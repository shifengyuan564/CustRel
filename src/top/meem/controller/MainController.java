package top.meem.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.meem.menu.MenuManager;
import top.meem.utils.RelApi;
import top.meem.utils.UtilProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Map;
import java.util.UUID;


@Controller
@RequestMapping("/")
public class MainController {

    private Logger log = Logger.getLogger(MainController.class);

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String mainPage(HttpServletRequest request, Model model, String code) {
        log.info("/* -----------------进入主页面------------------获取的code值： */" + code);

        Map<String, String> accessTokenAndOpenId = RelApi.getAccessTokenAndOpenId(code);
        String access_token = accessTokenAndOpenId.get("access_token");
        String openid = accessTokenAndOpenId.get("openid");

        // ******** 获取普通access_token *************
        String accessToken = RelApi.getAccessToken();
        log.info("获取普通accessToken:" + accessToken);

        // ********* 获取 jsApi Ticket 和 signature*****
        String currentUrl = request.getRequestURL().toString();
        String jsapi_ticket = RelApi.getJsApiTicket();
        String timestamp = create_timestamp();
        String nonceStr = create_nonce_str();

        String temp = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + currentUrl;
        log.info("获取的jsapi："+temp);

        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(temp.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("signature="+signature);
        model.addAttribute("appid", UtilProperties.getAppid());
        model.addAttribute("timestamp", timestamp);
        model.addAttribute("nonceStr", nonceStr);
        model.addAttribute("signature", signature);

        // *********通过特殊access_token和openid获取用户的信息***********
        Map<String, String> userInfoMap = RelApi.getUserInfo(access_token, openid);

        //创建用户对象
        log.info("access_token:" + access_token + ", openid=" + openid);
        String cisno = userInfoMap.get("cisno");

        //成功认证之后创建session
        HttpSession session = request.getSession();
        if (openid != null && !"".equals(openid)) {
            session.setAttribute("openid", openid);
        }
        session.setAttribute("cisno", cisno);
        model.addAttribute("map", userInfoMap);

        return "jsp/expect";
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
