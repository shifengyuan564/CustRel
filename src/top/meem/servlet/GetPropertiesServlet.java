package top.meem.servlet;

import org.json.JSONObject;
import top.meem.utils.PropertiesUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

/**
 * ��ȡconfig.properties����
 * @author kfzx-xiebb
 **/
public class GetPropertiesServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		String path = "E:\\IDEA_Projects\\CustRel\\out\\artifacts\\CustRel_war_exploded\\WEB-INF\\classes\\util.properties";
		Properties p = PropertiesUtils.getInstance(path);
		
		
		JSONObject j = new JSONObject(p);
		String ret = j.toString();
		System.out.println(ret);
		byte[] data = ret.getBytes("utf-8");
		
		resp.setCharacterEncoding("utf-8");
		resp.setContentLength(data.length);
		resp.getOutputStream().write(data);
		resp.flushBuffer();
	}
}
