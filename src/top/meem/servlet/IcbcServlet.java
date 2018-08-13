package top.meem.servlet;


import com.icbc.crypto.utils.Base64;
import com.icbc.crypto.utils.RSA;
import org.apache.log4j.Logger;
import top.meem.utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Properties;

public class IcbcServlet extends HttpServlet {

    /**
     * doGet:服务平台接入servlet
     * doPost:接收消息
     *
     * @author kfzx-xiebb
     */
    private static final long serialVersionUID = 6147578426309128023L;

    private Logger log = Logger.getLogger(IcbcServlet.class);

    /**
     * 加密
     *
     * @param data 数据
     * @param path 路径
     * @return byte[] 返回值
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String path) throws Exception {
        String base64Text = Base64.icbcbase64encode(data);
        return RSA.icbcRsaPriEn(base64Text.getBytes(), path);
    }

    /**
     * 解密
     *
     * @param data 解密数据
     * @param path 解密路径
     * @return byte[] 返回数据
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String path) throws Exception {
        byte[] plainText = RSA.icbcRsaPubDe(data, path);
        return Base64.icbcbase64decode(new String(plainText));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("===== Icbc Servlet doGet =====");

        String platform_pub_key = UtilProperties.getPlatformpubkey();
        String thirdparty_priv_key = UtilProperties.getPrivkey();

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            request.setCharacterEncoding("UTF-8");
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            byte[] base64Data = Base64.icbcbase64decode(URLDecoder.decode(signature, "utf-8"));
            byte[] deData = decryptByPublicKey(base64Data, platform_pub_key);
            signature = new String(deData);
            if (signature.equals(timestamp)) {
                byte[] enData = encryptByPrivateKey(deData, thirdparty_priv_key);
                out = response.getWriter();
                out.write(Base64.icbcbase64encode(enData));
                out.flush();
            }
        } catch (Exception e) {
            log.error("验证接入URL异常：", e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        log.info("===== Icbc Servlet doPost =====");

        String path = UtilProperties.getMsgRecLog();

        try {
            FileOutputStream bos = new FileOutputStream(path, true);
            InputStream is = request.getInputStream();
            {
                byte[] b = new byte[1024];
                int len = 0;
                StringBuilder builder = new StringBuilder();
                while ((len = is.read(b)) != -1) {
                    byte[] t = new byte[len];
                    System.arraycopy(b, 0, t, 0, len);
                    builder.append(new String(t, "utf-8"));
                }
                log.info("接收到的消息（密文）:" + builder.toString());
                //注意：请开发者自己独立处理sessionkey部分
                System.out.println("会话密钥：" + RelApi.getSessionkey());


                byte[] cipherKey = Base64.icbcbase64decode(RelApi.getSessionkey());

                log.info("接收到的消息（明文）:" + new String(ThreeDESClass.decrypt(Base64.icbcbase64decode(builder.toString()), cipherKey)));
                /*商户收到支付成功的消息，必须应答*/
				/*JSONObject json =new JSONObject();
				json.put("errcode", "0");
				json.put("errmsg", "");
				PrintWriter pw = response.getWriter();
				String returnString="";
				returnString=json.toString();
				pw.print(returnString);
				pw.flush();
				pw.close();	*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
