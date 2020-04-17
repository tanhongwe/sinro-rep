package com.mybatis.sinro.mybatisboot.system.bean;


import com.mybatis.sinro.mybatisboot.common.bean.BaseAo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @ClassName: RoleMenusAo
 * @Package com.tce.system.bean.ao
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/3 9:43
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RoleMenusAo extends BaseAo {

    private String roleId;

    private List<Integer> menuIds;

    @Override
    public String toString() {
        return "RoleMenusAo{" +
                "roleId='" + roleId + '\'' +
                ", menuIds=" + menuIds +
                '}';
    }
}
