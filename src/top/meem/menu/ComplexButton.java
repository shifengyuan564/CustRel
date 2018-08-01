/**
 * 
 */
package top.meem.menu;

/**
 * 
 * <p>
 * 
 * 父菜单项 :包含有二级菜单项的一级菜单。这类菜单项包含有二个属性：name和sub_button，而sub_button以是一个子菜单项数组 </br>
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
public class ComplexButton extends Button {
    
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

}
