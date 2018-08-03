package top.meem.servlet;


import top.meem.ticket.GetAccessToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ��ȡtoken 
 * ҳ�棺pages/access/getToken.jsp
 * @author kfzx-xiebb
 **/

public class GetTokenServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		long startTime=System.currentTimeMillis();
		String appid = req.getParameter("i_appid");
		String timestamp = req.getParameter("i_timestamp");
		String signature = req.getParameter("i_signature").replaceAll(" ", "+");
		String url = req.getParameter("i_url");

		GetAccessToken t = new GetAccessToken();
		String [] retArr = t.getAccessToken(appid, timestamp, signature, url);
		String ret = retArr[0]+"|"+retArr[1];
		
		System.out.println(ret);
		byte[] data = ret.getBytes();
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		resp.getOutputStream().write(data);
		resp.flushBuffer();
		long endTime=System.currentTimeMillis();
		System.out.println("��ȡtoken��ʱ=" +(endTime - startTime) +" ����");
	}

}
