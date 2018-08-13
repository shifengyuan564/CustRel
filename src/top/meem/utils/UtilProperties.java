package top.meem.utils;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件
 */
public class UtilProperties {

	private static Logger log = Logger.getLogger(UtilProperties.class);

	private static String appid;
	private static String platformpubkey;
	private static String pubkey;
	private static String privkey;

	private static String ICBCHttpsCerPath;
	private static String ICBCHttpsPort;
	private static String ICBCHttpsPassword;
	private static String ICBCHttpsCerFlag;


	private static String platformURL;
	private static String hostURL;
    private static String jsApiTicketURL;

    private static String msgRecLog;
	private static boolean debug;	/* 是否调试模式 */

    static {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new ClassPathResource("util.properties").getInputStream();
			prop.load(in);

			appid = prop.getProperty("appid");

			// 公众平台公钥
			platformpubkey = prop.getProperty("platform_pub_key");
			platformpubkey = getAbsolutePath(platformpubkey);

			// 系统公钥
			pubkey = prop.getProperty("thirdparty_pub_key");
			pubkey = getAbsolutePath(pubkey);

			// 系统私钥
			privkey = prop.getProperty("thirdparty_priv_key");
			privkey = getAbsolutePath(privkey);

			ICBCHttpsCerPath = prop.getProperty("https_cer_path");
			ICBCHttpsPort = prop.getProperty("https_port");
			ICBCHttpsPassword = prop.getProperty("https_password");
			ICBCHttpsCerFlag = prop.getProperty("https_cer_flag");

			platformURL = prop.getProperty("platformURL");			// 平台接口地址
			hostURL = prop.getProperty("hostURL");                  // 授权地址
            jsApiTicketURL = prop.getProperty("jsApiTicketURL");    // jsApi ticket

            msgRecLog = prop.getProperty("message_rcv_log");
            debug = Boolean.parseBoolean(prop.getProperty("debug", "false"));

		} catch (IOException e) {
			log.error("读取配置文件出错", e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * 获取文件完整路径
	 * 
	 * @param res
	 *            文件名/相对路径/绝对路径
	 * @return
	 */
	private static String getAbsolutePath(String res) {
		try {
			Resource resource = null;

			// 先从classpath下面找
			resource = new ClassPathResource(res);
			if (resource.exists()) {
				String path = resource.getFile().getAbsolutePath();
				log.info("文件路径:" + res + " --> " + path);
				return path;
			}

			// 再从文件系统里找
			resource = new FileSystemResource(res);
			if (resource.exists()) {
				String path = resource.getFile().getAbsolutePath();
				log.info("文件路径:" + res + " --> " + path);
				return path;
			}

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		log.error("未找到文件:" + res);
		return res;
	}

	/**
	 * 融E联服务号appid
	 */
	public static String getAppid() {
		return appid;
	}

	/**
	 * 融易联平台公钥文件路径
	 */
	public static String getPlatformpubkey() {
		return platformpubkey;
	}

	/**
	 * 本系统公钥文件路径
	 */
	public static String getPubkey() {
		return pubkey;
	}

	/**
	 * 本系统私钥文件路径
	 */
	public static String getPrivkey() {
		return privkey;
	}

	public static String getICBCHttpsCerPath() {
		return ICBCHttpsCerPath;
	}

	public static String getICBCHttpsPort() {
		return ICBCHttpsPort;
	}

	public static String getICBCHttpsPassword() {
		return ICBCHttpsPassword;
	}

	public static String getICBCHttpsCerFlag() {
		return ICBCHttpsCerFlag;
	}

    public static String getMsgRecLog() {
        return msgRecLog;
    }

    public static boolean isDebug() {
		return debug;
	}

	public static String getPlatformURL() {
		return platformURL;
	}

	public static String getHostURL() {
		return hostURL;
	}

    public static String getJsApiTicketURL() {
        return jsApiTicketURL;
    }
}
