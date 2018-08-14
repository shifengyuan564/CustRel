package top.meem.controller;

import com.crypto.RSAUtils;
import com.google.gson.Gson;
import com.icbc.crypto.utils.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.meem.cache.CacheManager;
import top.meem.domain.MsgText;
import top.meem.ticket.GetAccessTicket;
import top.meem.ticket.GetAccessToken;
import top.meem.utils.MessageSend;
import top.meem.utils.RelApi;
import top.meem.utils.UtilProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Controller
@RequestMapping("/test")
public class TestController {

    private Logger log = Logger.getLogger(TestController.class);

    // 测试主页面
    @RequestMapping(value = "/check")
    public String checkJsImport(RedirectAttributes attr) {
        return "/jsp/test";
    }

    // 获取jsAPI ticket
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String testToken(HttpServletRequest request, Model model) {
        return "/jsp/getToken";
    }

    // 消息发送测试
    @RequestMapping(value = "/msgsend4openid", method = RequestMethod.GET)
    public String msgsend4openid() {
        return "/jsp/message/msgsend4openid";
    }

    @RequestMapping(value = "/postmessage", method = RequestMethod.POST)
    @ResponseBody
    public String postMsg(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("utf-8");

        // openid, access_token, session_key, https://imapi.icbc.com.cn:443
        String openid = req.getParameter("i_openid");
        String token = req.getParameter("i_token").replaceAll(" ", "+");
        String key = req.getParameter("i_key").replaceAll(" ", "+");
        String url = req.getParameter("i_url");
        String content = new String(req.getParameter("i_content").getBytes());

        System.out.println("【token】:"+ token);
        System.out.println("【content】:"+ content);

        MessageSend m = new MessageSend();
        String ret = m.sendMessage2(content, key, token, openid, url);
        return ret;

    }

    @RequestMapping(value = "/btnSendMsg", method = RequestMethod.POST)
    @ResponseBody
    public String btnSendMsg(HttpServletRequest request) throws IOException {

        /*
        i_token=tYL1PbY31wxIOj2hGXp4k3QYU1L96uv6loej8njLWMHPqoT30NdQNLKqTX77245h78wt3bggWdhG1985Y7JLEM7x0vl15gG0QM267msyMQAMS4T6K58MA1N4unXjpls%3D
        &i_openid=qiRv%2Bv%2BmpSTLVUUrVlQx%2Fhbr5xyeosMG
        &i_key=%2FsI%2BsIrQwsECUnw3T1LIxLUpmBmJa4Cw
        &i_url=https%3A%2F%2Fimapi.icbc.com.cn%3A443
        &i_content=%7B%22touser%22%3A%22qiRv%2Bv%2BmpSTLVUUrVlQx%2Fhbr5xyeosMG%22%2C%22msgtype%22%3A%22text%22%2C%22text%22%3A%7B%22title%22%3A%22%E6%81%AF%E6%A0%87%22%2C%22content%22%3A%22%E6%81%AF%E5%86%85%22%7D%7D

        【token】:tYL1PbY31wxIOj2hGXp4k3QYU1L96uv6loej8njLWMHPqoT30NdQNLKqTX77245h78wt3bggWdhG1985Y7JLEM7x0vl15gG0QM267msyMQAMS4T6K58MA1N4unXjpls=
        【content】:{"touser":"qiRv+v+mpSTLVUUrVlQx/hbr5xyeosMG","msgtype":"text","text":{"title":"息标","content":"息内"}}
        */

        // openid, access_token, session_key, https://imapi.icbc.com.cn:443
        String openid = (String)request.getSession().getAttribute("openid");//"qiRv+v+mpSTLVUUrVlQx/hbr5xyeosMG";
        String token = RelApi.getAccessToken();
        String key = RelApi.getSessionkey();
        String url = "https://imapi.icbc.com.cn:443";
        String content = msgContent(openid, "反馈来自网点客户"+openid, "差评~~~\n 待改进 \n谢谢");

        MessageSend m = new MessageSend();
        String ret = m.sendMessage2(content, key, token, openid, url);
        return ret;
    }

    @RequestMapping(value = "/invoke", method = RequestMethod.GET)
    public String testScan(HttpServletRequest request, Model model) {

        String currentUrl = request.getRequestURL().toString();
        String jsApiTicket = RelApi.getJsApiKey();
        String ts = RelApi.create_timestamp();
        String nonceStr = RelApi.create_nonce_str();

        System.out.println("【jsapi ticket】:" + jsApiTicket + ", 【currentUrl】:" + currentUrl);
        String signature = RelApi.generateJsApiSign(jsApiTicket, nonceStr, ts, currentUrl);

        model.addAttribute("jsticket", jsApiTicket);
        model.addAttribute("signature", signature);
        model.addAttribute("timestamp", ts);
        model.addAttribute("nonceStr", nonceStr);
        model.addAttribute("appid", UtilProperties.getAppid());

        return "jsp/invoke";
    }

    private String msgContent(String openid,String title,String leaveMessage){
        /*{
            "touser":"OPENID",
            "msgtype":"text",
            "text":
                {
                    "title":"标题123",
                    "content":"Hello World"
                }
        }*/

        MsgText mt = new MsgText();
        mt.setTouser(openid);
        mt.setMsgtype("text");
        mt.getText().put("title",title);
        mt.getText().put("content",leaveMessage);

        log.info(new Gson().toJson(mt));
        return new Gson().toJson(mt);
    }

}
