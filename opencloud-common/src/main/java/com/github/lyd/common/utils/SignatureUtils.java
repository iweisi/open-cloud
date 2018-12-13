package com.github.lyd.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.util.*;

/**
 * @author liuyadu
 */
@Slf4j
public class SignatureUtils {

    public static void main(String[] args) {
        //参数签名算法测试例子
        HashMap<String, String> signMap = new HashMap<String, String>();
        signMap.put("clientId", "gateway");
        signMap.put("signType", "SHA256");
        //signMap.put("timestamp", DateUtils.getTimestampStr());
        signMap.put("timestamp", "20181205224251");
        signMap.put("nonce", "d3c6fcd551104c53b4ccde31059d815a");
        String sign = SignatureUtils.getSign(signMap, "123456");
        System.out.println("得到签名sign1:" + sign);
        signMap.put("sign", sign);
        System.out.println(SignatureUtils.validateSign(signMap, "123456"));
    }

    /**
     * 验证参数
     *
     * @param paramsMap
     * @throws Exception
     */
    public static void validateParams(Map<String, String> paramsMap) throws Exception {
        Assert.notNull(paramsMap.get("clientId"), "clientId不能为空");
        Assert.notNull(paramsMap.get("nonce"), "nonce不能为空");
        Assert.notNull(paramsMap.get("timestamp"), "timestamp不能为空");
        Assert.notNull(paramsMap.get("signType"), "signType不能为空");
        if (!SignatureUtils.SignType.contains(paramsMap.get("signType"))) {
            throw new IllegalArgumentException(String.format("signType必须为:%s,%s", SignatureUtils.SignType.MD5, SignatureUtils.SignType.SHA256));
        }
        try {
            DateUtils.parseDate(paramsMap.get("timestamp"), "yyyyMMddHHmmss");
        } catch (ParseException e) {
            throw new IllegalArgumentException("timestamp 格式必须为:yyyyMMddHHmmss");
        }
    }

    /**
     * @param paramMap     必须包含
     * @param clientSecret
     * @return
     */
    public static boolean validateSign(Map<String, String> paramMap, String clientSecret) {
        // TODO Auto-generated method stub
        if (paramMap == null) {
            log.debug("validateSign fail paramMap is null");
            return false;
        }
        String sign = paramMap.get("sign");
        if (sign == null) {
            log.debug("validateSign fail sign is null");
            return false;
        }
        String clientId = paramMap.get("clientId");
        if (clientId == null) {
            log.debug("validateSign fail clientId is null");
            return false;
        }
        //第一步判断时间戳 timestamp=201808091113
        //和服务器时间差值在10分钟以上不予处理
        String timestamp = paramMap.get("timestamp");
        //如果没有带时间戳返回
        if (timestamp == null) {
            log.debug("validateSign timestamp clientSecret is null");
            return false;
        } else {
            try {
                Long clientTimestamp = Long.parseLong(timestamp);
                // 10分钟内有效
                long maxExpire = 10 * 60;
                if ((DateUtils.getTimestamp() - clientTimestamp) > maxExpire) {
                    log.debug("validateSign fail timestamp expire");
                    return false;
                }
                //第二步获取随机值
//                String nonce = paramMap.get("nonce");
//                if (nonce == null) {
//                    //非法请求
//                    log.debug("validateSign fail nonce is null");
//                    return false;
//                } else {
//                    if(redisTemplate!=null){
//                        String key = PREFIX + clientId + nonce;
//                        //判断请求是否重复攻击
//                        //从redis中获取当前nonce，如果不存在则允许通过，并在redis中存储当前随机数，并设置过期时间为10分钟，单租户区分
//                        String save_nonce = redisTemplate.opsForValue().get(key);
//                        if (save_nonce == null) {
//                            redisTemplate.opsForValue().set(key, nonce, 10, TimeUnit.MINUTES);
//                        } else {
//                            //如果存在，不允许继续请求。
//                            log.debug("validateSign fail nonce is exist");
//                            return false;
//                        }
//                    }
//                }
                //服务器重新生成签名
                String server_sign = getSign(paramMap, clientSecret);
                //判断当前签名是否正确
                if (server_sign.equals(sign)) {
                    return true;
                }
            } catch (Exception e) {
                log.error("validateSign error:{}", e.getMessage());
                return false;
            }
        }
        return false;
    }


    /**
     * 得到签名
     *
     * @param paramMap     参数集合不含clientSecret
     *                     必须包含clientId=客户端ID
     *                     signType = SHA256|MD5 签名方式
     *                     timestamp=时间戳
     *                     nonce=随机字符串
     * @param clientSecret 验证接口的clientSecret
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String clientSecret) {
        if (paramMap == null) {
            return "";
        }
        //排序
        Set<String> keySet = paramMap.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        String signType = paramMap.get("signType");
        SignType type = null;
        if (StringUtils.isNotBlank(signType)) {
            type = SignType.valueOf(signType);
        }
        if (type == null) {
            type = SignType.MD5;
        }
        for (String k : keyArray) {
            if (k.equals("sign") || k.equals("clientSecret")) {
                continue;
            }
            if (paramMap.get(k).trim().length() > 0) {
                // 参数值为空，则不参与签名
                sb.append(k).append("=").append(paramMap.get(k).trim()).append("&");
            }
        }
        //暂时不需要个人认证
        sb.append("clientSecret=").append(clientSecret);
        String signStr = "";
        //加密
        switch (type) {
            case MD5:
                signStr = EncryptUtils.md5Hex(sb.toString()).toLowerCase();
                break;
            case SHA256:
                signStr = EncryptUtils.sha256Hex(sb.toString()).toLowerCase();
                break;
            default:
                break;
        }
        log.debug("sign params to string={}", sb.toString());
        log.debug("sign result={}", signStr);
        return signStr;
    }


    public enum SignType {
        MD5,
        SHA256;

        public static boolean contains(String type) {
            for (SignType typeEnum : SignType.values()) {
                if (typeEnum.name().equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

}