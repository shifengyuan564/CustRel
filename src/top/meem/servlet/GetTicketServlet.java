package top.meem.servlet;


import top.meem.ticket.GetAccessTicket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetTicketServlet extends HttpServlet {
	/**
	 * ��ȡticket  jsapi
	 * ҳ�棺pages/access/getToken.jsp
	 * @author kfzx-xiebb
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String accessToken = req.getParameter("access_token").replaceAll(" ", "+");
		String type = req.getParameter("type");	
		String url = req.getParameter("i_url").replaceAll(" ", "+");
		String key = req.getParameter("i_key").replaceAll(" ", "+");

		GetAccessTicket t = new GetAccessTicket();
		String ret = t.getAccessTicket(accessToken, key,type, url);
		
		System.out.println(ret);
		byte[] data = ret.getBytes();
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		resp.getOutputStream().write(data);
		resp.flushBuffer();
	}
}
