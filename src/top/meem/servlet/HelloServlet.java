package top.meem.servlet;

import com.icbc.crypto.utils.Base64;
import top.meem.menu.MenuManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Properties;

/**
 * 测试jsp 和 servlet的传值
 *
 * Created by shifengyuan.
 * Date: 2018/5/2
 * Time: 14:05
 */
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //设置session范围属性
        request.getSession().setAttribute("china", "张三");

        //设置request范围属性
        request.setAttribute("say", "你好world");

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
