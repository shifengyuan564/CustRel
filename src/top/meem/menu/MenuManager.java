/**
 *
 */
package top.meem.menu;

import com.crypto.TripleDesUtils;
import com.icbc.crypto.utils.Base64;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import top.meem.utils.RelApi;
import top.meem.utils.Tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * <p>
 * 菜单管理器类 </br>
 * <p>
 * <p>
 * <p>
 * 区分　责任人　 日期　　　　 说明 <br/>
 * 创建　杨金禄 2017年4月13日　 <br/>
 * <p>
 * *******
 * <p>
 *
 * @author yang-jinlu
 * @version 1.0, 2017年4月13日 <br/>
 * @email yangjinlu@teksun.cn
 */
public class MenuManager {

    private static Logger log = Logger.getLogger(MenuManager.class);

    /**
     * 菜单接口地址
     **/
    //生产
    private final static String MENU_URL = "https://imapi.icbc.com.cn";

    // 测试
    // private final static String MENU_URL = "https://imapi.dccnet.com.cn:447";

    public String testMenuDelte(String key, String getUrl, String token, String path) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        DefaultHttpClient client = new DefaultHttpClient();
        String retStr = "";
        HttpParams httpparams = new BasicHttpParams();
        byte[] sessionkey = Base64.icbcbase64decode(key);
        formparams.add(new BasicNameValuePair("access_token", token));
        try {
            java.net.URI uri = Tools.getURI(client, formparams, getUrl, "/open/menu/delete");
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse result = client.execute(httpGet);

            byte[] returnByte = IOUtils.toByteArray(result.getEntity().getContent());
            String returnString = new String(returnByte, "utf-8");
            System.out.println("returnString=" + returnString);
            if (returnString.contains("errcode")) {
                return returnString;
            }
            retStr = new String(TripleDesUtils.decrypt(returnByte, sessionkey), "utf-8");

            // retStr = new String(TripleDesUtils.decrypt(
            // IOUtils.toByteArray(result.getEntity().getContent()),
            // sessionkey),"utf-8");

            System.out.println(retStr);
        } catch (Exception e) {

        }
        return retStr;
    }

    public String testMenuGet(String key, String getUrl, String token, String path) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        DefaultHttpClient client = new DefaultHttpClient();
        String retStr = "";
        HttpParams httpparams = new BasicHttpParams();
        byte[] sessionkey = Base64.icbcbase64decode(key);
        formparams.add(new BasicNameValuePair("access_token", token));
        try {
            java.net.URI uri = Tools.getURI(client, formparams, getUrl, "/open/menu/get");
            HttpGet httpGet = new HttpGet(uri);

            HttpResponse result = client.execute(httpGet);
            byte[] returnByte = IOUtils.toByteArray(result.getEntity().getContent());
            String returnString = new String(returnByte, "utf-8");
            System.out.println("returnString=" + returnString);
            if (returnString.contains("errcode")) {
                return returnString;
            }
            retStr = new String(TripleDesUtils.decrypt(returnByte, sessionkey), "utf-8");

            // retStr = new
            // String(TripleDesUtils.decrypt(IOUtils.toByteArray(result.getEntity().getContent()),
            // sessionkey),"utf-8");

            System.out.println(retStr);
        } catch (Exception e) {

        }
        return retStr;
    }

    /**
     * @param key     sessionkey
     * @param getUrl  接口文档中菜单服务器地址 https://imapi.icbc.com.cn
     * @param token   access_token
     * @param content 菜单json格式
     * @param path    配置文件路径【无效】
     * @return
     */
    public static String menuCreate(String key, String getUrl, String token, String content, String path) {

        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        DefaultHttpClient client = new DefaultHttpClient();
        formparams.add(new BasicNameValuePair("body", content));
        byte[] sessionkey = Base64.icbcbase64decode(key);

        System.out.println("输入的【token】:" + token + ", 输入的【key】:" + key + ", 【转码后的key】:" + sessionkey.toString());

        String retStr = "";
        try {
            HttpEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            java.net.URI uri = Tools.getURI(client, formparams, getUrl, "/open/menu/create?access_token=" + token);
            String url = uri.toString().replaceFirst("\\?body", "&body");
            log.info(url);

            byte[] p = IOUtils.toByteArray(entity.getContent());
            byte[] c = TripleDesUtils.encrypt(p, sessionkey);

            RestTemplate restTemplate = new RestTemplate();
            byte[] retByte = restTemplate.postForObject(url, c, byte[].class);

            String returnString = new String(retByte, "utf-8");
            if (returnString.contains("errcode")) {
                return returnString;
            }

            retStr = new String(TripleDesUtils.decrypt(retByte, sessionkey), "utf-8");
            System.out.println("retStr:" + retStr);

            return retStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建菜单
     *
     * @param menu        菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> createMenu(Menu menu) throws UnsupportedEncodingException {
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        log.info("\n\njsonMenu" + jsonMenu);
        String res = menuCreate(RelApi.getSessionkey(), MENU_URL, RelApi.getAccessToken(), jsonMenu, "");
        log.info("\n\n创建菜单\n【返回结果：】" + res);
        return resultMsg(JSONObject.fromObject(res));
    }

    /**
     * 创建菜单【模拟使用】
     *
     * @param menu        菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> createMenu() throws UnsupportedEncodingException {
        // 将菜单对象转换成json字符串
        String jsonMenu = JSONObject.fromObject(getMenu()).toString();
        log.info("\n\njsonMenu" + jsonMenu);

        // 获取accessToken & sessionkey
        Map<String, String> dataMap = RelApi.getAccessTokenSessionkey();
        String asscessToken = dataMap.get("accessToken");
        String sessionkey = dataMap.get("sessionkey");

        String res = menuCreate(sessionkey, MENU_URL, asscessToken, jsonMenu, "");
        log.info("\n\n创建菜单\n【返回结果：】" + res);
        return resultMsg(JSONObject.fromObject(res));
    }

    /**
     * 处理返回结果
     *
     * @param result 返回结果json对象
     * @return
     */
    public static Map<String, String> resultMsg(JSONObject result) {
        // 定义返回结果
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put("code", String.valueOf(result.getInt("errcode")));
        resultMap.put("msg", result.getString("errmsg"));
        return resultMap;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //System.out.println(JSONObject.fromObject(getMenu()));
        createMenu();   // 创建菜单
    }

    /**
     * 组装菜单数据
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private static Menu getMenu() throws UnsupportedEncodingException {


//        CommonButton btn32 = new CommonButton();
//        btn32.setName("违章缴款");
//        btn32.setType("click");
//        btn32.setKey("num32");
//        btn32.setUrl("https://imapi.dccnet.com.cn:446/open/oauth2?appid=2C%2BUBKuZkiMLNWWKUqXa2Q%3D%3D&redirect=https%3A%2F%2Fimproxy.dccnet.com.cn%2Ftjfhrel%2FTJICBC%2FtrafficSearch%2FgoMain&scope=base");
//
//        CommonButton btn33 = new CommonButton();
//        btn33.setName("测试");
//        btn33.setType("click");
//        btn33.setKey("num33");
//        btn33.setUrl("https://imapi.dccnet.com.cn:443/open/oauth2?appid=2C%2BUBKuZkiMLNWWKUqXa2Q%3D%3D&redirect=https%3A%2F%2Fimproxy.dccnet.com.cn%2Ftjfhrel%2FTJICBC%2FtrafficSearch%2FgoMain&scope=base");

        CommonButton btn34 = new CommonButton();
        btn34.setName("进入主页");
        btn34.setType("viewurl");
//      btn32.setKey("num32");
        //生产使用
        //btn34.setUrl("https://imapi.icbc.com.cn/open/oauth2?appid=xtNgva%2FLUBMLNWWKUqXa2Q%3D%3D&redirect=https%3A%2F%2Fimproxy.icbc.com.cn%2Ftjfhrel%2FTJICBC%2FtrafficSearch%2FgoMain&scope=base");
//        btn34.setUrl("https://imapi.icbc.com.cn/open/oauth2?appid=3fevXREcthYLNWWKUqXa2Q=="+"&redirect="+URLEncoder.encode("https://imapi.icbc.com.cn/dlfhrel/traffic/trafficSearch/goMain.do", "utf-8")+"&scope=base");
        //生产-代理 appid和redirect都需要进行urlencode
        String url = "https://imapi.icbc.com.cn/open/oauth2?scope=userinfo&appid="+URLEncoder.encode("DLgiOs2NNEoLNWWKUqXa2Q==","utf-8")
                + "&redirect=" + URLEncoder.encode("http://meem.top/CustRel/main", "utf-8");
        btn34.setUrl(url);

        //测试使用
        //btn34.setUrl("https://imapi.dccnet.com.cn/open/oauth2?appid=fboKtcHyCu0LNWWKUqXa2Q%3D%3D&redirect=https%3A%2F%2Fimproxy.dccnet.com.cn%2Ftjfhrel%2FTJICBC%2FtrafficSearch%2FgoMain&scope=base");
//        btn34.setUrl("https://imapi.dccnet.com.cn:447/open/oauth2?appid=dus2ZELWYjILNWWKUqXa2Q%3d%3d"+"&redirect="+URLEncoder.encode("https://improxy.dccnet.com.cn/dlfhrel/dltraffic/trafficSearch/goMain.do", "utf-8")+"&scope=base");
        //测试兑奖页面
//        CommonButton btn41 = new CommonButton();
//        btn41.setName("我的奖品");
//       // btn41.setUrl("https://imapi.dccnet.com.cn:443/open/oauth2"+ "?appid="+URLEncoder.encode(UtilProperties.getAppid(), "utf-8")+"&redirect="+URLEncoder.encode("https://improxy.dccnet.com.cn/tjfhrel/TJICBC/lorretyController/getPrizeList.do", "utf-8")+"&scope=base");
//        btn41.setUrl("https://imapi.dccnet.com.cn/open/oauth2?appid=fboKtcHyCu0LNWWKUqXa2Q%3D%3D"+"&redirect="+URLEncoder.encode("https://improxy.dccnet.com.cn/tjfhrel/TJICBC/lorretyController/jump.do", "utf-8")+"&scope=base");
//        btn41.setType("viewurl");

        /**
         * 微信： mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
         */

//        ComplexButton mainBtn3 = new ComplexButton();
//        mainBtn3.setName("更多体验");
//        mainBtn3.setSub_button(new CommonButton[] {btn32, btn33 });

        /**
         * 封装整个菜单
         */
        Menu menu = new Menu();
        // menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });
        //menu.setButton(new Button[] { btn32, btn33 });
        menu.setButton(new Button[]{btn34});
        return menu;
    }

}
