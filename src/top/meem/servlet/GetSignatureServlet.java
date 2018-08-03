package top.meem.servlet;

import com.crypto.RSAUtils;
import com.icbc.crypto.utils.Base64;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetSignatureServlet extends HttpServlet {
	
	/**
	 * ��ȡǩ����Ϣ
	 * ҳ�棺pages/access/getToken.jsp
	 * @author kfzx-xiebb
	 */
	private static final long serialVersionUID = -4738237070489769675L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String key = req.getParameter("i_key");
		byte[] data = {};
		if(key.equals("gettimestamp")){
			Long sysTime = System.currentTimeMillis();
			String res_timestamp = String.valueOf(sysTime);
			data = res_timestamp.getBytes();
		}else{
			String appid = req.getParameter("i_appid");
			String timestamp = req.getParameter("i_timestamp");
			String signature = appid + timestamp;
			byte[] cipherText;
			try {
				cipherText = RSAUtils.encryptByPrivateKey(signature.getBytes(), key);
				signature = Base64.icbcbase64encode(cipherText);
				signature = signature.replaceAll("\r","").replaceAll("\n", "");
				System.out.println(signature);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			data = signature.getBytes();
		}
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		resp.getOutputStream().write(data);
		resp.flushBuffer();
	}
			
}
