package top.meem.utils;

import com.crypto.RSAUtils;
import com.crypto.Sha1Utils;
import com.crypto.TripleDesUtils;
import com.icbc.crypto.utils.Base64;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import top.meem.cache.Cache;
import top.meem.cache.CacheManager;
import top.meem.ticket.GetAccessTicket;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @创建人:周中俊
 * @创建时间:2017-4-5下午2:56:20
 * @类名称:RelApi
 * @类描述:获取融E联平台的参数的工具类
 */

public class RelApi {

    private static Logger log = Logger.getLogger(RelApi.class);

    // 获取accessToken时的get请求路径
    private final static String SIGNATURE = UtilProperties.getPlatformURL() + "/open/token";            // https://imapi.icbc.com.cn/open/token

    // 获取code后获取access_token和openid的路径
    private final static String CODE = UtilProperties.getPlatformURL() + "/open/oauth2/access_token";   // https://imapi.icbc.com.cn/open/oauth2/access_token

    // 开发者可以通过access_token和openid拉取用户信息的路径
    private final static String USERINFO = UtilProperties.getPlatformURL() + "/open/oauth2/userinfo";   // https://imapi.icbc.com.cn/open/oauth2/userinfo

    // APP_ID
    private final static String APP_ID = UtilProperties.getAppid();
    // 服务号私钥存放路径
    private final static String PRIVATE_KEY = UtilProperties.getPrivkey();
    // 平台公钥存放路径
    private final static String PLAT_FORMPUB_KEY = UtilProperties.getPlatformpubkey();
    // jsapi ticket
    private final static String JSAPI_TICKET = UtilProperties.getJsApiTicketURL();



    /*
     * 根据appid和时间戳获取签名
     *
     * @param appid 接入成功后平台为服务号分配的appid
     * @param timestamp 时间戳
     * @param key 服务号私钥存放路径
     *
     * @return String 签名密文
     */
    public static String getSignature(String appid, String timestamp, String key) {
        String signature = null;
        try {
            signature = appid + timestamp;  // 签名明文
            byte[] cipherText = RSAUtils.encryptByPrivateKey(signature.getBytes(), key);    // 使用服务号的私钥生成签名密文，
            signature = Base64.icbcbase64encode(cipherText);            // 将密文通过base64转化成字符串

            // base64转成字符串时，由于过长会导致结果出现\r，\n，因此替换掉
            signature = signature.replaceAll("\r", "").replaceAll("\n", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    // 获取Signature签名
    public static String getSignature(String timestamp) {
        String signature = null;

        try {
            String appid = UtilProperties.getAppid();  // 获取融E联服务号的appid
            String privkey = UtilProperties.getPrivkey(); // 获取本地私钥路径
            signature = appid + timestamp;// 签名明文：
            // 使用服务号的私钥生成签名密文，
            byte[] cipherText = RSAUtils.encryptByPrivateKey(signature.getBytes(), UtilProperties.getPrivkey()); // 将密文通过base64转化成字符串
            signature = Base64.icbcbase64encode(cipherText);
            // base64转成字符串时，由于过长会导致结果出现\r，\n，因此替换掉 
            signature = signature.replaceAll("\r", "").replaceAll("\n", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * @return
     * @方法描述: 获取普通的accessToken
     */
    private static String getAccessTokenBase() {
        Cache c = null;
        String accesstoken = null;
        String sessionkey = null;

        try {
            c = CacheManager.getCache("accessToken");
            log.info("accessToken--cache->" + c);

            if (c == null) {
                synchronized (RelApi.class) {
                    // 获取时间戳
                    String timestamp = System.currentTimeMillis() + "";
                    // 第一步：获取签名 signature
                    String signature = getSignature(APP_ID, timestamp, UtilProperties.getPrivkey());
                    StringBuilder url = new StringBuilder(SIGNATURE);
                    url.append("?signature=")
                            .append(URLEncoder.encode(signature, "UTF-8"))
                            .append("&appid=")
                            .append(URLEncoder.encode(APP_ID, "UTF-8"))
                            .append("&timestamp=")
                            .append(timestamp);

                    String result = threeGet(url.toString());
                    log.info(String.format("\n\n获取access_token\n【请求地址：%s】\n【返回结果：%s】", url.toString(), result));
                    if (result != null) {

                        JSONObject json = new JSONObject(result);
                        // 获取token对应的value，并转化为json对象
                        String newsignature = json.getString("signature");
                        String newtoken = json.getString("token");

                        JSONObject token = new JSONObject(json.get("token").toString());
                        accesstoken = (String) token.get("accesstoken"); // 获取accesstoken
                        String expiredtime = (String) token.get("expiredtime"); // 获取expiredtime
                        String key_c = token.getString("sessionkey");       // 获取sessionkey密文


                        // 第二步： 使用Sha1Utils.tokenValidate方法进行sha1校验token
                        boolean ret = Sha1Utils.tokenValidate(newtoken.getBytes(), newsignature, PLAT_FORMPUB_KEY);
                        System.out.println("判断正误:" + ret);
                        if (ret) {
                            //  第三步：使用服务号私钥解密，获取sessionkey明文 (保存100分钟)
                            sessionkey = new String(RSAUtils.decryptByPrivateKey(Base64.icbcbase64decode(key_c), UtilProperties.getPrivkey()));
                            CacheManager.setCache("accesstoken", accesstoken, 100 * 60 * 1000L);
                            CacheManager.setCache("sessionkey", sessionkey, 100 * 60 * 1000L);
                            CacheManager.setCache("expiredtime", expiredtime, 100 * 60 * 1000L);
                            c = CacheManager.getCache("accesstoken");
                        } else {
                            log.fatal("响应来源及数据不完整");
                        }

                    }
                    // 第四步：用之前获得的access_token， 采用http GET方式，请求获得jsapi_ticket

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.fatal("获取accesstoken出现异常");
        }
        return c == null ? null : c.getVal();
    }


    /**
     * 获取accesstoken & sessionkey
     *
     * @return
     */
    public static Map<String, String> getAccessTokenSessionkey() {
        Cache accessTokenCache = null, sessionkeyCache = null;

        try {
            accessTokenCache = CacheManager.getCache("accesstoken");
            sessionkeyCache = CacheManager.getCache("sessionkey");

            log.info("\n\naccessTokenCache ->" + accessTokenCache
                    + "\nsessionkeyCache ->" + sessionkeyCache);

            if (accessTokenCache == null) {
                getAccessTokenBase();                // 重新获取
                accessTokenCache = CacheManager.getCache("accesstoken");
                sessionkeyCache = CacheManager.getCache("sessionkey");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.fatal("获取accesstoken出现异常");
        }

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("accessToken", accessTokenCache.getVal());
        resultMap.put("sessionkey", sessionkeyCache.getVal());
        return resultMap;
    }

    public static String getAccessToken() {
        return getAccessTokenSessionkey().get("accessToken");
    }

    public static String getSessionkey() {
        return getAccessTokenSessionkey().get("sessionkey");
    }

    // 获取JsAPI Ticket
    public static String getJsApiKey() {
        String ticket = null;
        Cache jsapiCache = CacheManager.getCache("jsapiTicket");
        if (jsapiCache != null) {
            ticket = jsapiCache.getVal();
        } else {
            Map<String, String> map = getAccessTokenSessionkey();
            GetAccessTicket at = new GetAccessTicket();
            ticket = at.getAccessTicket(map.get("accessToken"), map.get("sessionkey"), "jsapi", JSAPI_TICKET);
            CacheManager.setCache("jsapiTicket", ticket, 100 * 60 * 1000L); // 100分钟
        }
        log.info(" JSAPI Ticket======> "+ ticket);

        JSONObject json = new JSONObject(ticket);
        String onlyTicket = (String) json.get("ticket");

        return onlyTicket;
    }

    // 获取JsAPI
    public static String generateJsApiSign(String jsApiTicket, String nonceStr, String ts, String targetUrl){

        String temp = "jsapi_ticket=" + jsApiTicket + "&noncestr=" + nonceStr + "&timestamp=" + ts + "&url=" + targetUrl;
        log.info("Before Decode ===> : signature = " + temp);

        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(temp.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        log.info("After Decode ===> : signature=" + signature);
        return signature;
    }

    /**
     * @param code
     * @return
     * @方法描述: 网页授权获取用户基本信息时获取的特殊access_token
     * <p>
     * 这里通过code换取的是一个特殊的网页授权access_token,
     * 与基础支持中的access_token（该access_token用于调用其他接口）不同
     */
    public static Map<String, String> getAccessTokenAndOpenId(String code) {
        Map<String, String> map = new HashMap<String, String>();
        String access_token = "";
        String openid = "";
        try {
            StringBuilder url = new StringBuilder(CODE);
            url.append("?appid=")
                    .append(URLEncoder.encode(UtilProperties.getAppid(), "UTF-8"))
                    .append("&code=")
                    .append(URLEncoder.encode(code, "UTF-8"));
            log.info("\n\n\n\n" + url.toString());

            String result = threeGet(url.toString());
            if (result != null) {
                JSONObject json = new JSONObject(result);
                log.info("通过code请求, 返回的【access_token】和【openid】:" + json.toString());
                access_token = json.getString("access_token");
                openid = json.getString("openid");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.fatal("获取access_token出现异常");
        }

        map.put("access_token", access_token);
        map.put("openid", openid);
        return map;
    }

    /**
     * @return
     * @方法描述:通过openid和access_token获取用户信息
     */
    public static Map<String, String> getUserInfo(String access_token,
                                                  String openid) {
        Map<String, String> map = new HashMap<String, String>();
        // 用户是否订阅该服务号标识，值为0时，代表此用户没有关注该服务号，拉取不到其余信息
        String subscribe = "";
        // 用户的昵称
        String nickname = "";
        // 用户的性别，值为1时是男性，值为0时是女性
        String sex = "";
        // 用户所在城市
        String city = "";
        // 用户所在省份
        String province = "";
        // 客户信息号，行内服务号返回，不存在则返回空
        String cisno = "";
        // 统一认证号，行内服务号返回，不存在则返回空
        String unino = "";
        // 统一通行证号，行内服务号返回，不存在则返回空
        String ICBCUserid = "";
        try {
            StringBuilder url = new StringBuilder(USERINFO);
            url.append("?access_token=")
                    .append(URLEncoder.encode(access_token, "UTF-8"))
                    .append("&openid=")
                    .append(URLEncoder.encode(openid, "UTF-8"));
            String result = threeGet(url.toString());
            log.info("获取用户信息=====>" + result);
            if (result != null) {
                JSONObject json = new JSONObject(result);
                subscribe = json.getString("subscribe");
                nickname = json.getString("nickname");
                sex = json.getString("sex");
                city = json.getString("city");
                province = json.getString("province");
                cisno = json.getString("cisno");
                unino = json.getString("unino");
                ICBCUserid = json.getString("ICBCUserid");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("subscribe", subscribe);
        map.put("openid", openid);
        map.put("nickname", nickname);
        map.put("sex", sex);
        map.put("city", city);
        map.put("province", province);
        map.put("cisno", cisno);
        map.put("unino", unino);
        map.put("ICBCUserid", ICBCUserid);
        return map;
    }

    /**
     * 失败重复连接三次
     *
     * @param url
     * @return
     */
    private static String threeGet(String url) {
        String result = null;
        int i = 0;
        while (i < 3) {
            result = HttpUtil.get(url);
            if (result != null) {
                break;
            }
            i++;
        }
        return result;
    }

    /**
     * 失败重复连接三次
     *
     * @param url
     * @param data
     * @return
     */
    @SuppressWarnings("unused")
    private static String threePost(String url, String data) {
        String result = null;
        int i = 0;
        while (i < 3) {
            result = HttpUtil.post(url, data);
            if (result != null) {
                break;
            }
            i++;
        }
        return result;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis());
    }
}
