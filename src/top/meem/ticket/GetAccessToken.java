package top.meem.ticket;

import com.crypto.RSAUtils;
import com.crypto.Sha1Utils;
import com.icbc.crypto.utils.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import top.meem.utils.RelApi;
import top.meem.utils.Tools;
import top.meem.utils.UtilProperties;


import java.util.ArrayList;
import java.util.List;

public class GetAccessToken {
		
	public String[] getAccessToken(String appid,String timestamp,String signature,String getURL) {

		String platform_pub_key =UtilProperties.getPlatformpubkey();
		String thirdparty_priv_key =UtilProperties.getPrivkey();
		
		String [] retArr = new String[2];
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();

		formparams.add(new BasicNameValuePair("appid",appid));
		formparams.add(new BasicNameValuePair("timestamp",timestamp));
		formparams.add(new BasicNameValuePair("signature",signature));
		try {
			java.net.URI uri = Tools.getURI(client,formparams,getURL,"/open/token");
			System.out.println(uri);
			HttpGet httpGet = new HttpGet(uri);
			
			HttpResponse result = client.execute(httpGet);
			String str = EntityUtils.toString(result.getEntity(),"utf-8");
			System.out.println(str);
			if(str.contains("errcode")){
				return new  String[]{str,""};
			}
			JSONObject json = new JSONObject(str);
			JSONObject json2 = new JSONObject(json.get("token").toString());
			//System.out.println("token:"+json2.get("accesstoken"));
			String accesstoken = (String) json2.get("accesstoken");
			String sessionkey =(String) json2.get("sessionkey");
			//System.out.println("sessionkey=" +sessionkey);
			String expiredtime=(String) json2.get("expiredtime");
			//System.out.println("expiredtime=" + expiredtime);
			JSONObject jso3= new JSONObject();
			jso3.put("expiredtime", expiredtime);
			jso3.put("accesstoken", accesstoken);
			jso3.put("sessionkey", sessionkey);
			
			String zhaiyao_c = json.getString("signature");
			String zhaiyao  = new String(RSAUtils.decryptByPublicKey(Base64.icbcbase64decode(zhaiyao_c),platform_pub_key));
			boolean ret = Sha1Utils.tokenValidate(jso3.toString().getBytes(), zhaiyao_c, platform_pub_key);
			//ret = ThreeDESClass.validata(json2.toString().getBytes(), zhaiyao);
			System.out.println(json2.toString());
			System.out.println(zhaiyao);
			System.out.println(ret);
			if(ret)
			{
				//��ȡsessionkey
				String key_c = json2.getString("sessionkey");
				//key_c="PEWWf+eRxQ3MamkDa0ToCdJvM+Jutb/wr0/y0HituySPnOLFTh9XeT8AmvNSOh/qkgm0sEKjiyEQZ7Y2ASyY6NeBqTN6RaX8HRdmLREFcBpoV/1VBkBYbjXW0nHtmrMPJ6euZKrHHFLBut5V6hzKW92WQMOqcWReAaIQtmc2jVA=";
				String key = new String(RSAUtils.decryptByPrivateKey(Base64.icbcbase64decode(key_c),thirdparty_priv_key));
				
				//Constant.sessionkey = key;

                System.out.println("key:"+key);
				retArr[0] = accesstoken;
				retArr[1] = key;
			}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		return retArr;
	}

	public static void main(String[] args) {
		System.out.println(RelApi.getAccessToken());
	}
	
}
