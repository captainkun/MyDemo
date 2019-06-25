package com.jike.demo.service.impl;

import com.google.gson.Gson;
import com.jike.demo.service.PayService;
import com.jike.demo.util.HttpsUtils;
import com.jike.demo.util.Result;
import com.jike.demo.util.SHA256Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author qukun
 * @date 2019/2/18
 */
@Service
public class PayServiceImpl implements PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
    private static final String KEY = "1wcyz5wmr6rq23c353xe";//平台分配
    private static final String MerchantID = "104650053110020";//商户 ID 平台分配，如: I000001
    private static final String AccessID = "10000001";//访问者 ID，平台为每一个访问者分配
    private static final String SUCCESS = "00";//返回码说明：[成功]
    private static final String UNKNOW = "X0";//返回码说明：[状态未知，请调用查询交易，查询订单状态]
    private static final String BUSY = "X4";//返回码说明：[设备忙，稍后再尝试]
    private static final String FAIL = "XX";//返回码说明：[失败]
    private static final String S021 = "XX";//返回码说明：[收银终端未绑定云端前置 注：如果返回 S021，收银台可展示 二维码，二维码内容格式为:MerchantID|CashierID 。收银终端扫描二维码实现绑定云端前置。]




    @Override
    public Result consume(String orderId, String cashierId, String money) {
        //1 包装参数
        String keyStr = "Key=" + KEY;
        String txnTypeStr = "TxnTypeStr=" + 2;//交易类型 2:消费
        String accessIDStr = "AccessID="+AccessID;//访问者 ID，平台为每一个访问者分配
        String channelIDStr = "ChannelID=27";//支付渠道,27 银行
        String merchantTxnNoStr = "MerchantTxnNo=1000000000001";//商户交易流水号 ?
        String merchantOrderNoStr = "MerchantOrderNoStr="+orderId;//商户订单号
        String merchantIDStr = "MerchantID="+MerchantID;//商户ID 平台分配
        String cashierIDStr = "CashierID="+cashierId;//收银员号
        String txnAmtStr = "TxnAmt="+money;//交易金额(元)
        String currencyCodeStr = "CurrencyCode=156";//货币代码, CNY:156
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatTime = simpleDateFormat.format(new Date());
        String txnReqTimeStr = "TxnReqTime=" + formatTime;//请求时间, 格式:YYYY/MM/DD HH24:MM:SS
        String sign = getSign(keyStr, txnTypeStr, accessIDStr, channelIDStr, merchantTxnNoStr, merchantOrderNoStr, merchantIDStr, cashierIDStr, txnAmtStr,currencyCodeStr,txnReqTimeStr);

        //组装发送请求的参数
        Map param = new HashMap();
        param.put("TxnType","2");//交易类型
        param.put("AccessID",AccessID);//访问者 ID，平台为每一个访问者分配
        param.put("Sign", sign);
        param.put("ChannelID","27");//支付渠道,27 银行
        param.put("MerchantTxnNo","1000000000001");//商户交易流水号 ?
        param.put("MerchantID",MerchantID);//商户 ID 平台分配，如: I000001
        param.put("CashierID", cashierId);//收银员号
        param.put("TxnAmt", money);//交易金额(元)
        param.put("CurrencyCode", "156");//货币代码, CNY:156
        param.put("TxnReqTime",formatTime);//请求时间, 格式:YYYY/MM/DD HH24:MM:SS

        //2 发送请求,接收响应
        String response = HttpsUtils.doPost("https://ss-platform-test.shijicloud.com/cloudpos-simu/api", new Gson().toJson(param));
        Map map = new Gson().fromJson(response, Map.class);
        if (SUCCESS.equals(map.get("RespCode"))) {
            return Result.success(map);
        } else if (UNKNOW.equals(map.get("RespCode"))) {
            return Result.error(2,"状态未知，请调用查询交易，查询订单状态");
        } else if (BUSY.equals(map.get("RespCode"))) {
            return Result.error(3, "设备忙，请稍后再试");
        } else if (FAIL.equals(map.get("RespCode"))) {
            return Result.fail(4, "支付失败");
        } else {
            return Result.error(5,"收银终端未绑定云端前置",map);
        }

    }

    @Override
    public Result queryConsume(String orderId, String cashierId) {
        //1 包装参数
        String keyStr = "Key=" + KEY;
        String TxnTypeStr = "TxnTypeStr=" + 5;//交易类型 5:消费查询
        String AccessIDStr = "AccessID="+AccessID;//访问者 ID，平台为每一个访问者分配
        String OrgTxnNo = "OrgTxnNo="+orderId;//原商户交易流水号，前端传递 ?
        String MerchantIDStr = "MerchantID="+MerchantID;//商户ID 平台分配
        String CashierIDStr = "CashierID="+cashierId;//收银员号
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatTime = simpleDateFormat.format(new Date());
        String TxnReqTimeStr = "TxnReqTime=" + formatTime;//请求时间, 格式:YYYY/MM/DD HH24:MM:SS
        String sign = getSign(keyStr, TxnTypeStr, AccessIDStr, OrgTxnNo, MerchantIDStr, CashierIDStr, TxnReqTimeStr);
        //组装发送请求的参数
        Map params = new HashMap();
        params.put("TxnType","5");//交易类型
        params.put("AccessID",AccessID);//访问者 ID，平台为每一个访问者分配
        params.put("Sign", sign);//签名值
        params.put("OrgTxnNo",orderId);//原商户交易流水号，前端传递
        params.put("MerchantID",MerchantID);//商户 ID 平台分配，如: I000001
        params.put("CashierID", cashierId);//收银员号
        params.put("TxnReqTime",formatTime);//请求时间, 格式:YYYY/MM/DD HH24:MM:SS

        //2 发送请求
        String response = HttpsUtils.doPost("https://ss-platform-test.shijicloud.com/cloudpos-server/api", new Gson().toJson(params));

        //3 判断请求状态 返回数据
        Map responseMap = new Gson().fromJson(response, Map.class);
        if (SUCCESS.equals(responseMap.get("RespCode"))) {
            return Result.success(responseMap);
        } else if (UNKNOW.equals(responseMap.get("RespCode"))) {
            return Result.error(2,"状态未知，请调用查询交易，查询订单状态");
        } else if (BUSY.equals(responseMap.get("RespCode"))) {
            return Result.error(3, "设备忙，请稍后再试");
        } else if (FAIL.equals(responseMap.get("RespCode"))) {
            return Result.fail(4, "支付失败");
        } else {
            return Result.error(5,"收银终端未绑定云端前置",responseMap);
        }

    }

    @Override
    public Result callback(String params) {
        Map paramsMap = new Gson().fromJson(params, Map.class);
        String respCode = paramsMap.get("RespCode").toString();
        if (SUCCESS.equals(respCode)) {
            return Result.success(paramsMap);
        } else if (UNKNOW.equals(respCode)) {
            return Result.error(2,"状态未知，请调用查询交易，查询订单状态");
        } else if (BUSY.equals(respCode)) {
            return Result.error(3, "设备忙，请稍后再试");
        } else if (FAIL.equals(respCode)) {
            return Result.fail(4, "支付失败");
        } else {
            return Result.error(5,"收银终端未绑定云端前置",paramsMap);
        }

    }

    private String getSign(String...params) {
        //对参数进行排序组合为sign,格式：Key=90134529356452348953&MerchantID=104650053110020&MerchantTxnNo=1000000000001&NotifyUrl=http://www.xxx.com/xxx.do&PartnerID=1017&TxnA
        List<String> list = new ArrayList<>();
        for (String param : params) {
            list.add(param);
        }
        Collections.sort(list);
        StringBuilder stringBuilder = new StringBuilder();
        for (String param : list) {
            stringBuilder.append(param + "&");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        String sign = stringBuilder.toString();
        return SHA256Utils.getSHA256StrJava(sign);//签名值
    }

}
