/**
 * 
 */
package top.meem.menu;

/**
 * 

 * <p>
 * 
 * 菜单项的基类
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
public class Button {
    
    private String name;//所有一级菜单、二级菜单都共有一个相同的属性，那就是name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
