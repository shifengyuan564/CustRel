package top.meem.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.meem.menu.MenuManager;
import top.meem.utils.RelApi;
import top.meem.utils.RelApiForJsApi;
import top.meem.utils.UtilProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
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
        Map<String,String> map = RelApi.getAccessTokenSessionkey();
        log.info("获取普通accessToken:" + map.get("accessToken"));

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

        // =========================================
        String[] retArray = RelApiForJsApi.generateTokenAndSessionKey();
        String ticket = RelApiForJsApi.getJsApiTicket(retArray[0],retArray[1]);
        System.out.println(ticket);
        log.info(ticket);
        // ========================================


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
