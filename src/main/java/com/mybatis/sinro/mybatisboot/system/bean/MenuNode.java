package com.mybatis.sinro.mybatisboot.system.bean;


import com.mybatis.sinro.mybatisboot.common.bean.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: MenuNode
 * @Package com.tce.system.common
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/3 17:35
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuNode extends TreeNode<MenuNode> {

    private String menuName;

    private String menuIcon;

    private String menuUrl;

    private Integer menuType;

    private String remark;

    @Override
    public String toString() {
        return "MenuNode{" +
                "menuName='" + menuName + '\'' +
                ", menuIcon='" + menuIcon + '\'' +
                ", menuUrl='" + menuUrl + '\'' +
                ", menuType=" + menuType +
                ", remark='" + remark + '\'' +
                '}';
    }
}
