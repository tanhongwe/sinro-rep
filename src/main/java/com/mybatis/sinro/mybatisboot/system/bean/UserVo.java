package com.mybatis.sinro.mybatisboot.system.bean;

import com.mybatis.sinro.mybatisboot.common.bean.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @ClassName: UserVo
 * @Package com.tce.system.bean.vo
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/3 10:59
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends BaseVo {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    private String createTime;

    @Override
    public String toString() {
        return "UserVo{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
