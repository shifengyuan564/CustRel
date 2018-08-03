package top.meem.controller;

import com.crypto.RSAUtils;
import com.icbc.crypto.utils.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.meem.cache.CacheManager;
import top.meem.ticket.GetAccessTicket;
import top.meem.ticket.GetAccessToken;
import top.meem.utils.RelApi;
import top.meem.utils.UtilProperties;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/test")
public class TestController {

    private Logger log = Logger.getLogger(TestController.class);

    // 测试页面
    @RequestMapping(value = "/check")
    public String checkJsImport(RedirectAttributes attr){
        return "/jsp/test";
    }


    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    public String testScan(HttpServletRequest request, Model model){

        String currentUrl = request.getRequestURL().toString();
        String jsApiTicket = RelApi.getJsApiKey();
        String ts = RelApi.create_timestamp();
        String nonceStr = RelApi.create_nonce_str();

        System.out.println("【jsapi ticket】:"+ jsApiTicket + ", 【currentUrl】:"+ currentUrl);
        String signature = RelApi.generateJsApiSign(jsApiTicket,nonceStr,ts,currentUrl);

        model.addAttribute("jsticket", jsApiTicket);
        model.addAttribute("signature", signature);
        model.addAttribute("timestamp", ts);
        model.addAttribute("nonceStr", nonceStr);
        model.addAttribute("appid", UtilProperties.getAppid());

        return "jsp/invoke";
    }
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String testToken(HttpServletRequest request, Model model){
        return "/jsp/getToken";
    }

    @RequestMapping(value = "/jsapi", method = RequestMethod.GET)
    public String getJsApiTicket () throws InterruptedException {

        CacheManager.clearCache();

        // step 1 - timestamp
        Long sysTime = System.currentTimeMillis();
        String timestamp = String.valueOf(sysTime);

        // step 2 - signature
        String appid = UtilProperties.getAppid();
        String signature = appid + timestamp;
        byte[] cipherText;
        try {
            cipherText = RSAUtils.encryptByPrivateKey(signature.getBytes(), UtilProperties.getPrivkey());
            signature = Base64.icbcbase64encode(cipherText);
            signature = signature.replaceAll("\r","").replaceAll("\n", "");
            System.out.println("signature:"+ signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // step 3 - access_token & sessionkey
        String url = UtilProperties.getJsApiTicketURL();
        GetAccessToken t = new GetAccessToken();
        String [] retArr = t.getAccessToken(appid, timestamp, signature, url);
        String ret = retArr[0]+"|"+retArr[1];
        System.out.println("access_token:"+ retArr[0] + ", session_key:"+retArr[1]);

        // step 4 - jsapi ticket
        GetAccessTicket at = new GetAccessTicket();
        String ticket = at.getAccessTicket(retArr[0], retArr[1],"jsapi", url);
        System.out.println("jsapi ticket:"+ ticket);

        // step 5 - signature
        // ...

        return "index";
    }

}
