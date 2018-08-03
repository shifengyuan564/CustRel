package top.meem.ticket;


import com.crypto.TripleDesUtils;
import com.icbc.crypto.utils.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.client.RestTemplate;
import top.meem.utils.RelApi;
import top.meem.utils.Tools;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * ticket获取
 * @author kfzx-xiebb
 * 
 **/
public class GetAccessTicket {

    /*

     */
	public String getAccessTicket(String accessToken,String sessionKey,String type,String getUrl){

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();
		String retStr="";
		byte [] sessionkey = Base64.icbcbase64decode(sessionKey);

		formparams.add(new BasicNameValuePair("access_token",accessToken));
		formparams.add(new BasicNameValuePair("type",type));    // jsapi
		try{
			java.net.URI uri = Tools.getURI(client,formparams,getUrl,"/open/ticket/getticket");
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse result = client.execute(httpGet);
			byte [] returnByte=IOUtils.toByteArray(result.getEntity().getContent());
			String returnString=new String(returnByte,"utf-8");
			System.out.println("GetAccessTicket ----- returnString="+returnString);
			if(returnString.contains("errcode")){
				return returnString;
			}
			retStr = new String(TripleDesUtils.decrypt(returnByte, sessionkey),"utf-8");
			System.out.println("getAccessTicket:" +retStr);

		} catch(Exception e){
			
		} 
		return retStr;
	}
	
}
