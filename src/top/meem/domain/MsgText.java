package top.meem.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shifengyuan.
 * Date: 2018/8/14
 * Time: 16:02
 */
public class MsgText {

    private String touser;
    private String msgtype;
    private Map<String,String> text = new HashMap<>();

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Map<String, String> getText() {
        return text;
    }

    public void setText(Map<String, String> text) {
        this.text = text;
    }
}
