package top.meem.utils;

import com.crypto.TripleDesUtils;
import com.icbc.crypto.utils.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MessageSend {

	public String sendMessage2(String content,String key,String token,String openid,String URL){

		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();
		formparams.add(new BasicNameValuePair("body",content));
		byte [] sessionkey = Base64.icbcbase64decode(key);
		String ret = "";
		HttpParams httpparams = new BasicHttpParams();

		try {
			HttpEntity entity = new UrlEncodedFormEntity(formparams,"UTF-8");
			java.net.URI uri = Tools.getURI(client,formparams,URL,"/open/message/send?access_token="+token);
			String url = uri.toString().replaceFirst("\\?body", "&body");

			HttpPost httppost = new HttpPost(url);
			byte [] p = IOUtils.toByteArray(entity.getContent());
			byte[] c = TripleDesUtils.encrypt(p, sessionkey);
			
			HttpEntity entity2 = new ByteArrayEntity(c);
			httppost.setEntity(entity2);
			HttpResponse result = client.execute(httppost);
			
			byte [] returnByte=IOUtils.toByteArray(result.getEntity().getContent());
			String returnString=new String(returnByte,"utf-8");
			System.out.println("returnString="+returnString);
			if(returnString.contains("errcode")){
				return returnString;
			}
			ret = new String(TripleDesUtils.decrypt(returnByte, sessionkey),"utf-8");
			System.out.println(ret);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public String sendMessageMut(String content,String key,String token,String URL,String path){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();
		formparams.add(new BasicNameValuePair("body",content));
		byte [] sessionkey = Base64.icbcbase64decode(key);
		String ret = "";
		try {
			HttpEntity entity = new UrlEncodedFormEntity(formparams,"UTF-8");
			java.net.URI uri = Tools.getURI(client,formparams,URL,"/open/message/sendmultiplebyopenid?access_token="+token);
			String url = uri.toString().replaceFirst("\\?body", "&body");
			HttpPost httppost = new HttpPost(url);
			byte [] p = IOUtils.toByteArray(entity.getContent());
			byte[] c = TripleDesUtils.encrypt(p, sessionkey);
			HttpEntity entity2 = new ByteArrayEntity(c);
			httppost.setEntity(entity2);
			HttpResponse result = client.execute(httppost);
			byte [] returnByte=IOUtils.toByteArray(result.getEntity().getContent());
			String returnString=new String(returnByte,"utf-8");
			System.out.println("returnString="+returnString);
			if(returnString.contains("errcode")){
				return returnString;
			}
			ret = new String(TripleDesUtils.decrypt(returnByte, sessionkey),"utf-8");
			System.out.println(ret);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public String sendMessageMessenger(String content,String key,String token,String URL,String path){
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		DefaultHttpClient client = new DefaultHttpClient();
		formparams.add(new BasicNameValuePair("body",content));
		byte [] sessionkey = Base64.icbcbase64decode(key);
		String ret = "";
		try {
			HttpEntity entity = new UrlEncodedFormEntity(formparams,"UTF-8");
			java.net.URI uri = Tools.getURI(client,formparams,URL,"/open/message/messenger?access_token="+token);
			String url = uri.toString().replaceFirst("\\?body", "&body");
			HttpPost httppost = new HttpPost(url);
			byte [] p = IOUtils.toByteArray(entity.getContent());
			byte[] c = TripleDesUtils.encrypt(p, sessionkey);
			HttpEntity entity2 = new ByteArrayEntity(c);
			httppost.setEntity(entity2);
			HttpResponse result = client.execute(httppost);
			byte [] returnByte=IOUtils.toByteArray(result.getEntity().getContent());
			String returnString=new String(returnByte,"utf-8");
			System.out.println("returnString="+returnString);
			if(returnString.contains("errcode")){
				return returnString;
			}
			ret = new String(TripleDesUtils.decrypt(returnByte, sessionkey),"utf-8");
			System.out.println(ret);
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}
