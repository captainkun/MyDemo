package com.jike.demo.tim.service;


import com.jike.demo.tim.pojo.TIMMsgElement;
import com.jike.demo.tim.pojo.TIMTagValue;

import java.util.List;
import java.util.Map;

/**
 * 腾讯IM相关接口
 * @author qu.kun
 * @date 2021/6/7
 */
public interface ITIMService {

    /**
     * 获取用户ID签名
     * @param userSid 用户ID
     * @return 签名ID
     */
    String getUserSig(String userSid);

    /**
     * 批量导入ID到腾讯IM
     * @param userSids 用户加密ID，非必传。集合不为空则导入集合中的用户，集合为空则导入usr_customer表里面的数据
     */
    void batchImportId(List<String> userSids);

    /**
     * 发送IM单聊消息
     * @param fromAccount 发送对象
     * @param toAccount 接受对象
     * @param msgElement 消息元素，使用构造器构造 {@link com.jike.demo.tim.util.TIMMsgElementBuilder}
     * @return 发送消息后腾讯返回的MsgKey唯一ID
     */
    String sendImSingleMsg(String fromAccount, String toAccount, TIMMsgElement msgElement);

    /**
     * 批量查询多个单聊会话的未读消息数
     * @param toAccount 待查询的用户的UserSid
     * @param peerAccounts 待查询的用户与对端的UserSid
     * @return key: 对端userSid, value: 未读消息数
     */
    Map<String, Integer> getUnreadMsgNums(String toAccount, List<String> peerAccounts);

    /**
     * 查询特定账号的单聊总未读数
     * @param toAccount 待查询的用户的UserSid
     * @return 未读消息数
     */
    Integer getUnreadMsgNums(String toAccount);

    /**
     * 更新好友备注
     * @param fromAccount 需要更新该 UserID 的关系链数据
     * @param toAccount 好友的 UserID
     * @param remark 备注
     */
    void updateFriendRemark(String fromAccount, String toAccount, String remark);

    /**
     * 设置用户的某个单聊会话的消息全部已读
     * 示例：管理员指定 reportAccount 将会话 peerAccount 的单聊消息全部已读。
     *
     * @param reportAccount 进行消息已读的用户 UserSId
     * @param peerAccount 进行消息已读的单聊会话的另一方用户 UserSId
     */
    void setMsgAllRead(String reportAccount, String peerAccount);

    /**
     * 设置用户资料
     * @param fromAccount 待设置用户
     * @param tagValueList 属性信息
     */
    void setUserInfo(String fromAccount, List<TIMTagValue> tagValueList);
}
