package top.meem.utils;

import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import top.meem.utils.ssl.ICBCSSLSocketFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;
import java.util.List;

public class Tools {

    public static URI getURI(DefaultHttpClient client, List<NameValuePair> formparams, String getURL, String actionname, String path) {
    	FileInputStream instream = null;
        try {
            String https_cer_path = UtilProperties.getICBCHttpsCerPath();
            String port = UtilProperties.getICBCHttpsPort();
            String protocl = getURL.split("://")[0];
            String url = getURL.split("://")[1]; 
            String password = UtilProperties.getICBCHttpsPassword();
            String certflag = UtilProperties.getICBCHttpsCerFlag();
           
            HttpParams httpparams = new BasicHttpParams();

            if (certflag.equals("1")) {
                // 验证证书
                if (protocl.equals("https")) {
                    protocl = "https";
                    // 获得密匙库
                    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    instream = new FileInputStream(new File(https_cer_path));
                    // 密匙库的密码
                    trustStore.load(instream, password.toCharArray());
                    // 注册密匙库
                    SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
                    // 不校验域名
                    socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    Scheme sch = new Scheme("https", socketFactory, Integer.parseInt(port));
                    client.getConnectionManager().getSchemeRegistry().register(sch);
                }
            }
            if (certflag.equals("0")) {
                // 不验证证书
                if (protocl.equals("https")) {
                    // 测试模式不验证证书
                    HttpProtocolParams.setVersion(httpparams, HttpVersion.HTTP_1_1);
                    HttpProtocolParams.setContentCharset(httpparams, HTTP.UTF_8);
                    KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
                    trustStore.load(null, null);
                    SSLSocketFactory sf = new ICBCSSLSocketFactory(trustStore);
                    sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                    SchemeRegistry registry = new SchemeRegistry();
                    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                    Scheme ssh = new Scheme("https", sf, Integer.parseInt(port));
                    ClientConnectionManager ccm = new ThreadSafeClientConnManager(httpparams, registry);
                    // client = new DefaultHttpClient(ccm, httpparams);
                    client.getConnectionManager().getSchemeRegistry().register(ssh);
                } else {
                    client = new DefaultHttpClient(httpparams);
                }
            }
            URI uri = URIUtils.createURI(protocl, url, -1, actionname, URLEncodedUtils.format(formparams, "UTF-8"), null);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally{
        	if(instream!=null){
        		try {
        			instream.close();  // 关闭Socket输出流
        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		
        	}
        }
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
                String s = encode;
                return s;      //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "ISO8859_1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO8859_1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";        //如果都不是，说明输入的内容不属于常见的编码格式。
    }
    
    
    
    
}
