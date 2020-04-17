package com.mybatis.sinro.mybatisboot.system.bean;

import com.mybatis.sinro.mybatisboot.common.bean.BaseAo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: RoleAo
 * @Package com.tce.system.bean.ao
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/2 17:47
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleAo extends BaseAo {
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 角色名称
     */
    private String roleName;

    private String remark;

    @Override
    public String toString() {
        return "RoleAo{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
