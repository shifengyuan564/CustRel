package top.meem.servlet;


import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

// 调试 ：http://localhost:8080/tps/pages/ticket/clientTicketForReal.html
public class signServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(signServlet.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		 String targetUrl=request.getParameter("url");
		
		 String jsticket=request.getParameter("jsticket");
		 
		 String appid=request.getParameter("appid");
		 System.out.println("decode before targetUrl="+targetUrl+   ",jsticket="+jsticket +",appid=" +appid);	
		 
		 
		 if (targetUrl==null ||targetUrl.length()==0) {
			 System.out.println("jsapiTicket��ȡʧ��,��ǰurlΪ�գ���");
		}
		int index = targetUrl.indexOf("#");
		if (index > 0) {
			targetUrl = targetUrl.substring(0, index);
			 System.out.println("targetUrl��" + targetUrl);
		}
		String jsapi_ticket =jsticket; 
		String timestamp =  create_timestamp();
		String nonceStr =create_nonce_str();

		
		String temp="jsapi_ticket="+ jsapi_ticket+"&noncestr=" +nonceStr +"&timestamp=" +timestamp +"&url=" +targetUrl;
		log.info("tps temp="+temp);
		System.out.println("temp="+temp);

		String signature="";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(temp.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
        	 System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
        	 System.out.println(e.getMessage());
            e.printStackTrace();
        }

	   
        System.out.println("signature="+signature);
		
		//String JS_API	= "onMenuShareTimeline,onMenuShareAppMessage,onMenuShareQQ,onMenuShareWeibo,startRecord,stopRecord,onVoiceRecordEnd,playVoice,pauseVoice,stopVoice,onVoicePlayEnd,uploadVoice,downloadVoice,chooseImage,previewImage,uploadImage,downloadImage,translateVoice,getNetworkType,openLocation,getLocation,hideOptionMenu,showOptionMenu,hideMenuItems,showMenuItems,hideAllNonBaseMenuItem,showAllNonBaseMenuItem,closeWindow,scanQRCode,chooseWXPay,openProductSpecificView,addCard,chooseCard,openCard";
		//String[] jsApiList =JS_API.split(",");
	    
		JSONObject all=new JSONObject();
		try {
			all.put("signature", signature);
			all.put("timestamp", timestamp);
			all.put("nonceStr", nonceStr);
			all.put("appid", appid);
		} catch (JSONException e) {
			 System.out.println(e.getMessage());
		}
		
			response.getWriter().print(all.toString());
		} catch (IOException e) {
			 System.out.println(e.getMessage());
			e.printStackTrace();
		}  


	}
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis());
    }

}
