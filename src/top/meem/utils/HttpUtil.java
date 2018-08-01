package top.meem.utils;

import com.crypto.RSAUtils;
import com.crypto.TripleDesUtils;
import com.icbc.crypto.utils.Base64;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http请求工具
 * 
 * @author zgf
 * 
 */
public class HttpUtil {
	
    private static Logger log=Logger.getLogger(HttpUtil.class);
    
    /**
     *  Description: HttpClient GET方法
	 	1. 创建 HttpClient 的实例
		2. 创建某种连接方法的实例，在这里是 GetMethod。在 GetMethod 的构造函数中传入待连接的地址
		3. 调用第一步中创建好的实例的 execute 方法来执行第二步中创建好的 method 实例
		4. 读 response
		5. 释放连接。无论执行方法是否成功，都必须释放连接
		6. 对得到后的内容进行处理
     * @param get_url
     * @return
     */
	public static String get(String get_url){
		ByteArrayOutputStream outputStream  = null;
		InputStream in = null;
		HttpClient client = new HttpClient();
		// 设置代理服务器地址和端口(第一个参数是代理服务器地址，第二个参数是端口号。)      
	    // client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
		
		//创建GET方法的实例，在GET方法的构造函数中传入待连接的地址即可。
		//用GetMethod将会自动处理转发过程，如果想要把自动处理转发过程去掉的话，可以调用方法setFollowRedirects(false)。
		// 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
        HttpMethod getMethod = new GetMethod(get_url);
        
		//调用实例httpClient的executeMethod方法来执行getMethod。
		//设置成了默认的恢复策略，在发生异常时候将自动重试3次，在这里你也可以设置成自定义的恢复策略
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		//执行getMethod
		try {
			int statusCode = client.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				  log.fatal("Method failed: " + getMethod.getStatusLine());
			}
			/**
			 * 在返回的状态码正确后，即可取得内容。取得目标地址的内容有三种方法：
			 * 第一种，getResponseBody，该方法返回的是目标的二进制的byte流；
			 * 第二种，getResponseBodyAsString，这个方法返回的是String类型，值得注意的是该方法返回的String的编码是根据系统默认的编码方式，所以返回的String值可能编码类型有误，在本文的"字符编码"部分中将对此做详细介绍；
			 * 第三种，getResponseBodyAsStream，这个方法对于目标地址中有大量数据需要传输是最佳的。在这里我们使用了最简单的getResponseBody方法。
			 */
			//读取内容 
			 in = getMethod.getResponseBodyAsStream();
			 outputStream = new ByteArrayOutputStream();
			 byte[] data = new byte[1024];
			 int len = 0;
			 while ((len = in.read(data)) != -1) {
				outputStream.write(data, 0, len);
			 }
			 return outputStream.toString("utf-8");
		} catch (HttpException e) {
			log.fatal("Please check your provided http address!");
		} catch (IOException e) {
			log.fatal(e);
		} finally {
		   //释放连接
		   getMethod.releaseConnection();
		   if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
		}
		return null;
	}
	
	
	public static String post(String post_url,String post_data) {
		HttpClient client=new HttpClient();
		ByteArrayOutputStream outputStream  = null;
		InputStream in = null;
		/*
		 * 在创建了PostMethod的实例之后，需要给method实例填充表单的值
		 * 在BBS的登录表单中需要有两个域，第一个是用户名（域名叫id），第二个是密码（域名叫passwd）。
		 * 表单中的域用类NameValuePair来表示，该类的构造函数第一个参数是域名，第二参数是该域的值；
		 * 将表单所有的值设置到PostMethod中用方法setRequestBody。另外由于BBS登录成功后会转向另外一个页面，但是HttpClient对于要求接受后继服务的请求，
		 * 比如POST和PUT，不支持自动转发，因此需要自己对页面转向做处理。
		 */
		PostMethod postMethod = new PostMethod(post_url);
		try {
			RequestEntity requestEntity = new StringRequestEntity(post_data, "text/html", "UTF-8");
			postMethod.setRequestEntity(requestEntity);
			
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			 // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			 // 301或者302
			 if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			     // 从头中取出转向的地址
			     Header locationHeader = postMethod.getResponseHeader("location");
			     String location = null;
			     if (locationHeader != null) {
			    	 location = locationHeader.getValue();
			    	 log.fatal("The page was redirected to:" + location);
			     } else {
			    	 log.fatal("Location field value is null.");
			     }
			     return null;
			 }else{
				//读取内容 
				 in = postMethod.getResponseBodyAsStream();
				 outputStream = new ByteArrayOutputStream();
				 byte[] data = new byte[1024];
				 int len = 0;
				 while ((len = in.read(data)) != -1) {
					outputStream.write(data, 0, len);
				 }
				 return new String(outputStream.toByteArray());
			 }
		} catch (HttpException e) {
			log.fatal(e);
		} catch (IOException e) {
			log.fatal(e);
		}finally {
			   //释放连接
			postMethod.releaseConnection();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
		}
		return null;
	}
	
	/*
	 * 自动转向
        根据RFC2616中对自动转向的定义，
        主要有两种：301和302。301表示永久的移走（Moved Permanently），当返回的是301，则表示请求的资源已经被移到一个固定的新地方，任何向该地址发起请求都会被转到新的地址上。
       302表示暂时的转向，比如在服务器端的servlet程序调用了sendRedirect方法，则在客户端就会得到一个302的代码，这时服务器返回的头信息中location的值就是sendRedirect转向的目标地址。
       HttpClient支持自动转向处理，但是象POST和PUT方式这种要求接受后继服务的请求方式，暂时不支持自动转向，因此如果碰到POST方式提交后返回的是301或者302的话需要自己处理。
        就像刚才在POSTMethod中举的例子：如果想进入登录BBS后的页面，必须重新发起登录的请求，请求的地址可以在头字段location中得到。
        不过需要注意的是，有时候location返回的可能是相对路径，因此需要对location返回的值做一些处理才可以发起向新地址的请求。
        另外除了在头中包含的信息可能使页面发生重定向外，在页面中也有可能会发生页面的重定向。引起页面自动转发的标签是：<meta http-equiv="refresh" content="5; url=http://www.ibm.com/us">。
        如果你想在程序中也处理这种情况的话得自己分析页面来实现转向。需要注意的是，在上面那个标签中url的值也可以是一个相对地址，如果是这样的话，需要对它做一些处理后才可以转发。
	 */

	
	/**
	 * 
	 * @param post_url
	 * @return
	 * @throws JSONException
	 */
	public static String post(String post_url,Map<String, String> map) {
		HttpClient client=new HttpClient();
		ByteArrayOutputStream outputStream  = null;
		InputStream in = null;
		/*
		 * 在创建了PostMethod的实例之后，需要给method实例填充表单的值
		 * 在BBS的登录表单中需要有两个域，第一个是用户名（域名叫id），第二个是密码（域名叫passwd）。
		 * 表单中的域用类NameValuePair来表示，该类的构造函数第一个参数是域名，第二参数是该域的值；
		 * 将表单所有的值设置到PostMethod中用方法setRequestBody。另外由于BBS登录成功后会转向另外一个页面，但是HttpClient对于要求接受后继服务的请求，
		 * 比如POST和PUT，不支持自动转发，因此需要自己对页面转向做处理。
		 */
		PostMethod postMethod = new PostMethod(post_url);
		try {
			for (String key : map.keySet()) {
				postMethod.setParameter(key, key);
			}
			
			// 执行postMethod
			int statusCode = client.executeMethod(postMethod);
			 // HttpClient对于要求接受后继服务的请求，象POST和PUT等不能自动处理转发
			 // 301或者302
			 if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
			     // 从头中取出转向的地址
			     Header locationHeader = postMethod.getResponseHeader("location");
			     String location = null;
			     if (locationHeader != null) {
			    	 location = locationHeader.getValue();
			    	 log.fatal("The page was redirected to:" + location);
			     } else {
			    	 log.fatal("Location field value is null.");
			     }
			     return null;
			 }else{
				//读取内容 
				 in = postMethod.getResponseBodyAsStream();
				 outputStream = new ByteArrayOutputStream();
				 byte[] data = new byte[1024];
				 int len = 0;
				 while ((len = in.read(data)) != -1) {
					outputStream.write(data, 0, len);
				 }
				 return new String(outputStream.toByteArray());
			 }
		} catch (HttpException e) {
			log.fatal(e);
		} catch (IOException e) {
			log.fatal(e);
		}finally {
			   //释放连接
			postMethod.releaseConnection();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.fatal(e);
				}
			}
		}
		return null;
	}
	
public static JSONObject doPost(String sendUrl, String RequestMethod, String sendData) throws IllegalStateException, IOException, Exception {
	    
        //密钥在传输时已经过base64encode，使用前先decode
        byte [] sessionkey = Base64.icbcbase64decode(RelApi.getSessionkey());
        // 返回结果
        String ret = null;
        
	    try {
            //to-do formparams为post请求的参数
            List<NameValuePair> formparams = new ArrayList<NameValuePair> ();
            formparams.add(new BasicNameValuePair("body", sendData));//body为参数内容
    
            HttpEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");
            // token必须通过url提交给服务平台
            HttpPost httppost = new HttpPost(sendUrl);
            // 获取post请求实体的二进制数组
            byte[] p = org.apache.commons.io.IOUtils.toByteArray(entity.getContent());
            // 使用会话密钥加密后获取流的密文
            byte[] c = TripleDesUtils.encrypt(p, sessionkey);
            // 根据密文重新生成一个请求实体
            HttpEntity entity2 = new ByteArrayEntity(c);
            httppost.setEntity(entity2);
            DefaultHttpClient client = new DefaultHttpClient();
            // 发起post请求
            HttpResponse result = client.execute(httppost);
          
            // 使用同一会话密钥对响应报文进行解密，获取明文ret
            ret = new String(TripleDesUtils.decrypt(org.apache.commons.io.IOUtils.toByteArray(result.getEntity().getContent()), sessionkey), "utf-8");
            
	    } finally {
	        log.info(String.format("\n【请求地址：%s】\n【请求参数：%s】\n【返回结果：%s】", sendUrl, sendData, ret));
	    }
	    
	    return JSONObject.fromObject(ret);
	}
	
	public static JSONObject doPost1(String sendUrl, String RequestMethod, String sendData) throws IllegalStateException, IOException, Exception {
	    
	    // 请求内容明文 转 byte
        byte[] data = sendData.getBytes("utf-8");
        // 请求内容密文
        byte[] ct = RSAUtils.encryptByPrivateKey(data, UtilProperties.getPrivkey());
        // 加密参数
        byte[] cipherText = TripleDesUtils.encrypt(data, ct);
        sendData = new String(cipherText, "utf-8");
        //密钥在传输时已经过base64encode，使用前先decode
        byte [] sessionkey = Base64.icbcbase64decode(RelApi.getSessionkey());
        // 返回结果
        String ret = null;
        
	    try {
            //to-do formparams为post请求的参数
            List<NameValuePair> formparams = new ArrayList<NameValuePair> ();
            formparams.add(new BasicNameValuePair("body", sendData));//body为参数内容
    
            HttpEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");
            // token必须通过url提交给服务平台
            HttpPost httppost = new HttpPost(sendUrl);
            // 获取post请求实体的二进制数组
            byte[] p = org.apache.commons.io.IOUtils.toByteArray(entity.getContent());
            // 使用会话密钥加密后获取流的密文
            byte[] c = TripleDesUtils.encrypt(p, sessionkey);
            // 根据密文重新生成一个请求实体
            HttpEntity entity2 = new ByteArrayEntity(c);
            httppost.setEntity(entity2);
            DefaultHttpClient client = new DefaultHttpClient();
            // 发起post请求
            HttpResponse result = client.execute(httppost);
          
            // 使用同一会话密钥对响应报文进行解密，获取明文ret
            ret = new String(TripleDesUtils.decrypt(org.apache.commons.io.IOUtils.toByteArray(result.getEntity().getContent()), sessionkey), "utf-8");
            
	    } finally {
	        log.info(String.format("\n【请求地址：%s】\n【请求参数明文byte：%s】\n【请求参数密文byte：%s】\n【请求参数加密后byte：%s】\n【请求参数加密后String：%s】\n【返回结果：%s】", sendUrl, data, ct, cipherText, sendData, ret));
	    }
	    
	    return JSONObject.fromObject(ret);
	}
	
	/** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
	 * @throws UnsupportedEncodingException 
     */  
	public static JSONObject HttpRequest(String request, String RequestMethod, String output) throws UnsupportedEncodingException{
	    
	    System.out.println(String.format("\n【请求地址：%s】\n【请求参数加密前：%s】", request, output));
//	    // 加密参数
//	    byte[] cipherText = TripleDesUtils.encrypt(output.getBytes("UTF-8"), RelApi.getSessionkey().getBytes("UTF-8"));
//	    output = cipherText.toString();
//	    System.out.println(String.format("\n【请求地址：%s】\n【请求参数加密后：%s】", request, output));
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			//建立连接
			URL url = new URL(request);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setRequestMethod(RequestMethod);
			if(output!=null){
				OutputStream out = connection.getOutputStream();
				out.write(output.getBytes("UTF-8"));
				out.close();
			}	
			//流处理
			InputStream input = connection.getInputStream();
			InputStreamReader inputReader = new InputStreamReader(input,"UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			//关闭连接、释放资源
			reader.close();
			inputReader.close();
			input.close();
			input = null;
			connection.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	} 
	
	/**
	 * 数据提交与请求通用方法
	 * @param access_token 凭证
	 * @param RequestMt 请求方式
	 * @param RequestURL 请求地址
	 * @param outstr 提交json数据
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */
    public static JSONObject PostMessage(String access_token, String RequestMt, String RequestURL, String outstr) {
    	
    	RequestURL = RequestURL.replace("ACCESS_TOKEN", access_token);
    	JSONObject jsonobject = null;
        try {
            jsonobject = doPost(RequestURL, RequestMt, outstr);
            if (null != jsonobject) {  
                if (0 != jsonobject.getInt("errcode")) {  
                    String error = String.format("操作失败 errcode:{%d} errmsg:{%s}", jsonobject.getInt("errcode"), jsonobject.getString("errmsg"));  
                    System.out.println(error); 
                }  
            }
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	return jsonobject;
    }
    
}
