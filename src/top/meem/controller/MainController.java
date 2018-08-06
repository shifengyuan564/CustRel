package top.meem.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.meem.utils.RelApi;
import top.meem.utils.UtilProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class MainController {

    private Logger log = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, Model model, String code) throws UnsupportedEncodingException {
        log.info(" ----------------- 进入首页面/main ------------------获取的code值：" + code);

        // *********通过特殊access_token和openid获取用户的信息***********
        Map<String, String> accessTokenAndOpenId = RelApi.getAccessTokenAndOpenId(code);    // 用code获取token
        String access_token = accessTokenAndOpenId.get("access_token");
        String openid = accessTokenAndOpenId.get("openid");
        log.info("access_token:" + access_token + ", openid=" + openid);

        //创建用户对象
        Map<String, String> userInfoMap = RelApi.getUserInfo(access_token, openid);
        String cisno = userInfoMap.get("cisno");

        //成功认证之后创建session
        HttpSession session = request.getSession();
        if (openid != null && !"".equals(openid)) {
            session.setAttribute("openid", openid);
        }
        session.setAttribute("cisno", cisno);
        model.addAttribute("map", userInfoMap);

        // TODO : 入库以openid为主键

        // ******** 1. 获取普通access_token *************
        Map<String,String> map = RelApi.getAccessTokenSessionkey();
        log.info("获取普通accessToken:" + map.get("accessToken"));


        // ******* 2. 获取 jsapi 和 signature *************

        String currentUrl = request.getRequestURL().toString() + "?code="+URLEncoder.encode(code,"utf-8");
        String jsApiTicket = RelApi.getJsApiKey();
        String ts = RelApi.create_timestamp();
        String nonceStr = RelApi.create_nonce_str();

        System.out.println("【jsapi ticket】:"+ jsApiTicket + ", 【currentUrl】:"+ currentUrl);
        log.info("【jsapi ticket】:"+ jsApiTicket + ", 【currentUrl】:"+ currentUrl);
        String signature = RelApi.generateJsApiSign(jsApiTicket,nonceStr,ts,currentUrl);

        model.addAttribute("jsticket", jsApiTicket);
        model.addAttribute("signature", signature);
        model.addAttribute("timestamp", ts);
        model.addAttribute("nonceStr", nonceStr);
        model.addAttribute("appid", UtilProperties.getAppid());

        return "jsp/invoke";
    }

    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String invokeQr(HttpServletRequest request, Model model){
        log.info("/* ----------------- 进入次页面/invoke ------------------*/" );
        return "jsp/invoke";
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis());
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
}
