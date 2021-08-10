package com.jike.demo.tim.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jike.demo.tim.config.TIMConfig;
import com.jike.demo.tim.constant.TIMConstant;
import com.jike.demo.tim.pojo.TIMMessage;
import com.jike.demo.tim.pojo.TIMMsgElement;
import com.jike.demo.tim.pojo.TIMTagValue;
import com.jike.demo.tim.service.ITIMService;
import com.jike.demo.tim.util.TIMGenerateUserSig;
import com.kun.utils.exception.BusinessException;
import com.kun.utils.exception.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 腾讯IM接口实现
 * @author qu.kun
 * @date 2021/6/7
 */
@Slf4j
@Service
public class TIMServiceImpl extends TIMConfig implements ITIMService {
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 腾讯IM接口地址：导入账号
     */
    private static final String IMPORT_ID_URL = "https://console.tim.qq.com/v4/im_open_login_svc/multiaccount_import?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";
    /**
     * 腾讯IM接口地址：发送单聊消息
     */
    private static final String SEND_IM_MSG_URL = "https://console.tim.qq.com/v4/openim/sendmsg?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";
    /**
     * 腾讯IM接口地址：用户资料设置
     */
    private static final String SET_USER_INFO_URL = "https://console.tim.qq.com/v4/profile/portrait_set?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";
    /**
     * 腾讯IM接口地址：查找未读消息数
     */
    private static final String FIND_UNREAD_MSG_NUMS_URL = "https://console.tim.qq.com/v4/openim/get_c2c_unread_msg_num?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";
    /**
     * 腾讯IM接口地址：更新好友关系信息
     */
    private static final String FRIEND_INFO_UPDATE_URL = "https://console.tim.qq.com/v4/sns/friend_update?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";
    /**
     * 腾讯IM接口地址：设置单聊消息已读
     */
    private static final String SET_MSG_READ_URL = "https://console.tim.qq.com/v4/openim/admin_set_msg_read?sdkappid=%s&identifier=%s&usersig=%s&random=%s&contenttype=json";

    @Override
    public String getUserSig(String userSid) {
        if (Objects.isNull(userSid)) {
            return null;
        }
        return TIMGenerateUserSig.genUserSig(userSid);
    }

    @Override
    public void batchImportId(List<String> userSids) {
//        // 根据入参情况查库获取用户信息
//        List<Customer> customerList = getCustomers(userSids);
//        if (customerList == null) return;
//
//        // 随机数实例
//        Random random = new Random();
//
//        // 添加IM账号
//        importSids(customerList, random);
//
//        // 设置用户资料
//        batchSetUserInfoByCustoms(customerList);
    }

    @Override
    public String sendImSingleMsg(String fromAccount, String toAccount, TIMMsgElement msgElement) {
        // 参数校验
        if (StringUtils.isAllBlank(fromAccount, toAccount) || Objects.isNull(msgElement)) {
            throw BusinessException.of("参数必传", ResultCode.DATA_IS_WRONG);
        }

        // 请求随机数设置
        int randomNum = getRandomNum();

        // 封装消息体
        TIMMessage timMessage = TIMMessage.builder()
                .SyncOtherMachine(1)
                .From_Account(fromAccount)
                .To_Account(toAccount)
                // 目前通过后台发送的消息，无需发送回调信息再来处理，避免资源浪费，如果后期需要回调处理，做成可配即可
                .ForbidCallbackControl(Arrays.asList("ForbidBeforeSendMsgCallback", "ForbidAfterSendMsgCallback"))
                .MsgRandom(randomNum)
                .MsgBody(Collections.singletonList(msgElement))
                .build();

        // 拼接请求路径
        String sendMsgUrl = String.format(SEND_IM_MSG_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), randomNum);

        // 请求TIM接口，发送消息
        JSONObject body = executeUrlRequest(sendMsgUrl, timMessage);
        if (Objects.isNull(body)) {
            return null;
        }
        return body.getString(TIMConstant.MSG_KEY);
    }

    @Override
    public Map<String, Integer> getUnreadMsgNums(String toAccount, List<String> peerAccounts) {
        // 参数校验
        if (StringUtils.isBlank(toAccount) || CollectionUtils.isEmpty(peerAccounts)) {
            throw BusinessException.of("参数必传", ResultCode.DATA_IS_WRONG);
        }

        // 拼接请求路径
        String requestUrl = String.format(FIND_UNREAD_MSG_NUMS_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), getRandomNum());

        // 封装请求参数
        JSONObject requestBody = new JSONObject();
        JSONArray peerAccountArray = new JSONArray();
        peerAccountArray.addAll(peerAccounts);
        requestBody.put("To_Account", toAccount);
        requestBody.put("Peer_Account", peerAccountArray);

        // 返回Map
        Map<String, Integer> returnMap = new HashMap<>(peerAccounts.size());

        // 请求TIM接口，查询未读数
        JSONObject responseBody = executeUrlRequest(requestUrl, requestBody);
        if (Objects.isNull(responseBody)) {
            peerAccounts.forEach(userSid -> returnMap.put(userSid, 0));
            return returnMap;
        }

        JSONArray c2CUnreadMsgNumList = responseBody.getJSONArray("C2CUnreadMsgNumList");
        if (CollectionUtils.isEmpty(c2CUnreadMsgNumList)) {
            peerAccounts.forEach(userSid -> returnMap.put(userSid, 0));
            return returnMap;
        }

        // 封装返回数据
        for (Object o : c2CUnreadMsgNumList) {
            JSONObject jsonObject = (JSONObject) o;
            returnMap.put(jsonObject.getString("Peer_Account"), jsonObject.getInteger("C2CUnreadMsgNum"));
        }

        return returnMap;
    }

    @Override
    public Integer getUnreadMsgNums(String toAccount) {
        // 参数校验
        if (StringUtils.isBlank(toAccount)) {
            throw BusinessException.of("参数必传", ResultCode.DATA_IS_WRONG);
        }

        // 拼接请求路径
        String requestUrl = String.format(FIND_UNREAD_MSG_NUMS_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), getRandomNum());

        // 封装请求参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("To_Account", toAccount);

        // 请求TIM接口，查询未读数
        JSONObject body = executeUrlRequest(requestUrl, jsonObject);

        return Optional.ofNullable(body).map(obj -> obj.getInteger("AllC2CUnreadMsgNum")).orElse(0);
    }

    @Override
    public void updateFriendRemark(String fromAccount, String toAccount, String remark) {
        // 封装请求参数
        JSONObject requestBody = new JSONObject();
        JSONArray updateItems = new JSONArray();
        // 需要更新该 UserID 的关系链数据
        requestBody.put("From_Account", fromAccount);
        // 需要更新的好友对象数组
        requestBody.put("UpdateItem", updateItems);

        JSONObject updateItem = new JSONObject();
        JSONArray snsItems = new JSONArray();
        // 好友的 UserID
        updateItem.put("To_Account", toAccount);
        // 需要更新的关系链数据对象数组
        updateItem.put("SnsItem", snsItems);

        JSONObject remarkInfo = new JSONObject();
        // 需要更新的关系链字段的字段名，目前只支持好友备注、好友分组、关系链自定义字段的更新操作，关系链字段的详细信息可参见 https://cloud.tencent.com/document/product/269/1501#.E5.A5.BD.E5.8F.8B.E8.A1.A8
        remarkInfo.put("Tag", "Tag_SNS_IM_Remark");
        // 需要更新的关系链字段的值
        remarkInfo.put("Value", remark);

        updateItems.add(updateItem);
        snsItems.add(remarkInfo);

        // 拼接请求路径
        String requestUrl = String.format(FRIEND_INFO_UPDATE_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), getRandomNum());

        // 请求腾讯IM接口
        executeUrlRequest(requestUrl, requestBody);

    }

    @Override
    public void setMsgAllRead(String reportAccount, String peerAccount) {
        // 拼接请求路径
        String requestUrl = String.format(SET_MSG_READ_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), getRandomNum());

        // 封装请求消息
        JSONObject requestBody = new JSONObject();
        requestBody.put("Report_Account", reportAccount);
        requestBody.put("Peer_Account", peerAccount);

        // 请求腾讯IM接口
        executeUrlRequest(requestUrl, requestBody);
    }

    @Override
    public void setUserInfo(String fromAccount, List<TIMTagValue> tagValueList) {
        if (StringUtils.isBlank(fromAccount) || CollectionUtils.isEmpty(tagValueList)) {
            throw BusinessException.of("参数必传", ResultCode.DATA_IS_WRONG);
        }

        JSONObject reqObj = new JSONObject();
        reqObj.put("From_Account", fromAccount);

        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(tagValueList);
        reqObj.put("ProfileItem", jsonArray);

        // 拼接请求url
        String setUserInfoReqUrl = String.format(SET_USER_INFO_URL, SDK_APP_ID, ADMIN_ID, TIMGenerateUserSig.genAdminUserSig(), getRandomNum());
        // 发起请求
        executeUrlRequest(setUserInfoReqUrl, reqObj);
    }

    private int getRandomNum() {
        Random random = new Random();
        return random.nextInt(1294967290) + LocalDateTime.now().getSecond() + 1;
    }

//    private void batchSetUserInfoByCustoms(List<Customer> customerList) {
//        int runNums2 = 0;
//        long startTime2 = 0L;
//        for (Customer user : customerList) {
//            String userSid;
//            Integer userId = user.getId();
//            if (Objects.equals(user.getChannelId(), 101)) {
//                userSid = NumberUtils.compressTP(userId.longValue());
//            } else {
//                userSid = IntegerEncryptor.compress(userId);
//            }
//            String nickname = user.getNickname();
//            String imagePath = user.getAvatarImagePath();
//            GenderEnum gender = user.getGender();
//
//            // 昵称
//            TIMTagValue nickNameTag = TIMTagValue.builder()
//                    .tag(TIMTagEnum.Tag_Profile_IM_Nick)
//                    .value(nickname)
//                    .build();
//            // 头像
//            TIMTagValue imgTag = TIMTagValue.builder()
//                    .tag(TIMTagEnum.Tag_Profile_IM_Image)
//                    .value(attachmentUrlProvider.toHttpUrl(imagePath))
//                    .build();
//
//            // 性别
//            String genderValue;
//            switch (gender) {
//                case MALE:
//                    genderValue = "Gender_Type_Male";
//                    break;
//                case FEMALE:
//                    genderValue = "Gender_Type_Female";
//                    break;
//                default:
//                    genderValue = "Gender_Type_Unknown";
//                    break;
//            }
//            TIMTagValue genderTag = TIMTagValue.builder()
//                    .tag(TIMTagEnum.Tag_Profile_IM_Gender)
//                    .value(genderValue)
//                    .build();
//
//            // 多个属性封装为集合
//            List<TIMTagValue> timTagValues = Arrays.asList(nickNameTag,
//                    imgTag,
//                    genderTag);
//
//            // 发起请求
//            runNums2++;
//            setUserInfo(userSid, timTagValues);
//
//            if (startTime2 == 0L) {
//                startTime2 = SystemClock.now();
//            }
//            if (runNums2 == 200) {
//                // 限制每秒请求200次
//                limitRunTimes(startTime2);
//                startTime2 = 0L;
//                runNums2 = 0;
//            }
//        }
//    }
//
//    private void importSids(List<Customer> customerList, Random random) {
//        // 添加IM账号
//        int runNums = 0;
//        long startTime = 0L;
//
//        // 每次最多导入100个用户
//        List<List<Customer>> user100List = getBatchNumList(customerList, 100);
//
//        for (List<Customer> users : user100List) {
//            List<String> userSidList = users.stream().map(obj -> {
//                Integer userId = obj.getId();
//                if (obj.getChannelId().equals(101)) {
//                    return NumberUtils.compressTP(userId.longValue());
//                } else {
//                    return IntegerEncryptor.compress(userId);
//                }
//            }).collect(Collectors.toList());
//
//            int randomNum = random.nextInt(1294967290) + LocalDateTime.now().getSecond() + 1;
//            String adminSig = TIMGenerateUserSig.genAdminUserSig();
//            String requestUrl = String.format(IMPORT_ID_URL, SDK_APP_ID, ADMIN_ID, adminSig, randomNum);
//            JSONObject jsonObject = JSONObject.parseObject("{\"Accounts\":[]}");
//            JSONArray accounts = jsonObject.getJSONArray("Accounts");
//            accounts.addAll(userSidList);
//
//            if (startTime == 0L) {
//                startTime = SystemClock.now();
//            }
//
//            // 发起请求
//            runNums++;
//            executeUrlRequest(requestUrl, jsonObject);
//            if (startTime == 0L) {
//                startTime = SystemClock.now();
//            }
//
//            if (runNums == 100) {
//                // 限制每秒请求100次
//                limitRunTimes(startTime);
//                startTime = 0L;
//                runNums = 0;
//            }
//        }
//    }
//
//    private List<Customer> getCustomers(List<String> userSids) {
//        List<Customer> customerList;
//        List<TaiPingCard> userCardList = new ArrayList<>();
//        if (CollectionUtils.isNotEmpty(userSids)) {
//            List<Integer> userIdList = userSids.stream().map(IntegerEncryptor::uncompress).collect(Collectors.toList());
//            // 查询用户表中的信息
//            Map<Integer, Customer> customerMap = customerProvider.findCustomerByIds(userIdList);
//            if (MapUtils.isEmpty(customerMap)) {
//                return null;
//            }
//            customerList = new ArrayList<>(customerMap.values());
//            List<Long> userIdListInDB = customerList.stream().map(BaseModel::getId).map(Integer::longValue).collect(Collectors.toList());
//            // 查找太平的用户名片信息
//            userCardList = taipingUserProvider.findUserCards(userIdListInDB);
//        } else {
//            Customer queryCustomer = new Customer();
//            queryCustomer.setUserType(UserTypeEnum.EMPLOYEE);
//            queryCustomer.setChannelId(101);
//            // 查询用户表中的信息
//            Page<Customer> customerPage1 = customerProvider.findCustomerByChannelId(101, UserTypeEnum.EMPLOYEE.getCode(), 1, 100);
//            customerList = customerPage1.getResult();
//            if (CollectionUtils.isEmpty(customerList)) {
//                return null;
//            }
//
//            int pages = customerPage1.getPages();
//            if (pages > 1) {
//                for (int i = 2; i < pages + 2; i++) {
//                    Page<Customer> customerPage = customerProvider.findCustomerByChannelId(101, UserTypeEnum.EMPLOYEE.getCode(), i, 100);
//                    List<Customer> result = customerPage.getResult();
//                    if (CollectionUtils.isEmpty(result)) {
//                        break;
//                    }
//                    customerList.addAll(result);
//
//                    // 查找太平的用户名片信息
//                    List<Long> userIdList = result.stream().map(BaseModel::getId).map(Integer::longValue).collect(Collectors.toList());
//                    List<TaiPingCard> userCards = taipingUserProvider.findUserCards(userIdList);
//                    if (CollectionUtils.isNotEmpty(userCards)) {
//                        userCardList.addAll(userCards);
//                    }
//
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        log.error("睡眠GG", e);
//                    }
//                }
//            }
//
//        }
//
//        if (CollectionUtils.isEmpty(userCardList)) {
//            log.info("无用户名片信息");
//            return customerList;
//        }
//
//        // 有名片的优先取名片头像和名字,否则取个人中心的信息
//        Map<Long, TaiPingCard> userIdAndCardMap = userCardList.stream().collect(Collectors.toMap(TaiPingCard::getId, obj -> obj));
//        for (Customer customer : customerList) {
//            TaiPingCard taiPingCard = userIdAndCardMap.get(customer.getId().longValue());
//            if (Objects.isNull(taiPingCard)) {
//                continue;
//            }
//
//            // 名片名字
//            String title = taiPingCard.getTitle();
//            // 名片中的头像
//            String userImagePath = taiPingCard.getUserImagePath();
//            if (StringUtils.isNotBlank(title)) {
//                customer.setNickname(title);
//            }
//            if (StringUtils.isNotBlank(userImagePath)) {
//                customer.setAvatarImagePath(userImagePath);
//            }
//        }
//
//        return customerList;
//    }

    /**
     * 请求腾讯IM接口
     * @param requestUrl 请求路径
     * @param requestBody 请求体
     * @return 响应体
     */
    private JSONObject executeUrlRequest(String requestUrl, Object requestBody) {
        // 请求接口
        log.info("请求腾讯IM请求体：{}", requestBody);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(requestUrl, requestBody, String.class);
        log.info("请求腾讯IM接口结果：{}", responseEntity);
        JSONObject responseBody = JSONObject.parseObject(responseEntity.getBody());
        // 检查请求结果，如果结果有误则发起重试
        responseBody = checkResultThenRetry(requestUrl, requestBody, responseBody);
        return responseBody;
    }

    /**
     * 检查响应体是否请求失败
     * @param responseBody 响应体
     * @return 响应体是否请求失败
     */
    private boolean isActionStatusNotOk(JSONObject responseBody) {
        return Objects.isNull(responseBody) || !TIMConstant.ACTION_STATUS_OK.equals(responseBody.get("ActionStatus"));
    }

    /**
     * 检查响应体是否请求失败
     * @param responseBody 响应体
     * @return 响应体是否请求失败
     */
    private boolean isActionStatusOk(JSONObject responseBody) {
        return Objects.nonNull(responseBody) && TIMConstant.ACTION_STATUS_OK.equals(responseBody.get("ActionStatus"));
    }

    /**
     * 检查请求结果，如果结果有误则发起重试
     * @param requestUrl 请求接口
     * @param requestBody 请求体
     * @param responseBody 响应体
     * @return 响应体
     */
    private JSONObject checkResultThenRetry(String requestUrl, Object requestBody, JSONObject responseBody) {
        ResponseEntity<JSONObject> responseEntity;
        boolean actionStatusNotOk = isActionStatusNotOk(responseBody);
        if (actionStatusNotOk) {
            for (int i = 0; i < 2; i++) {
                // 如果请求接口报错，重试2次
                responseEntity = restTemplate.postForEntity(requestUrl, requestBody, JSONObject.class);
                log.info("后台重试查询单聊消息未读数次数：{}, 结果：{}", i + 1, responseEntity);
                responseBody = responseEntity.getBody();
                if (isActionStatusOk(responseBody)) {
                    break;
                }
                try {
                    Thread.sleep(1000 + i * 1000);
                } catch (InterruptedException e) {
                    log.error("睡眠GG", e);
                }
            }
        }
        return responseBody;
    }

    /**
     * 获取每个集合容量为batchNum个的等份集合
     * @param userSidList 用户加密ID集合
     * @param batchNum 批次处理数
     * @return 每个集合容量为100个的等份集合
     */
    private <T> List<List<T>> getBatchNumList(List<T> userSidList, int batchNum) {
        // 总注册数
        int countAll = userSidList.size();
        // 供需多少批
        int frequency = (countAll + batchNum - 1) / batchNum;
        return Stream.iterate(0, n -> n + 1) // 创建流
                .limit(frequency)    // 取 frequency 个
                .parallel()           // 开启并行处理
                .map(    // 处理单项
                        a -> userSidList.stream()
                                .skip((long) a * batchNum) // 跳过元素
                                .limit(batchNum)    // 取 batchNum 个
                                .parallel()        // 开启并行处理
                                .collect(Collectors.toList())
                )
                .collect(Collectors.toList());
    }

    /**
     * 限制每秒请求次数
     * @param startTime 运行开始时间
     */
    private void limitRunTimes(long startTime) {
        long endTime = System.currentTimeMillis();
        long betweenTime = endTime - startTime;
        if (betweenTime < 1000) {
            try {
                long sleepTime = 1000 - betweenTime;
                log.info("时间相差:{}ms, 睡眠时间:{}ms", betweenTime, sleepTime);
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                log.error("睡眠GG", e);
            }
        }
    }

}
