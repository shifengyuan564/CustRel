/**
 * 
 */
package top.meem.menu;

/**
 * <p>
 * 
 * 子菜单项 :没有子菜单的菜单项，有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。 </br>
 * 
 * <p>
 * 
 * 区分　责任人　	日期　　　　	说明	<br/>
 * 创建　杨金禄	2017年4月13日　		<br/>
 * <p>
 * *******
 * <p>
 * @author yang-jinlu
 * @email  yangjinlu@teksun.cn
 * @version 1.0,2017年4月13日 <br/>
 * 
 */
public class CommonButton extends Button {
    
    private String type;
    private String key;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
