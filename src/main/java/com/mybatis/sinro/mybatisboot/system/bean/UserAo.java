package com.mybatis.sinro.mybatisboot.system.bean;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mybatis.sinro.mybatisboot.common.bean.BaseAo;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * @ClassName: UserAo
 * @Package com.tce.system.bean.ao
 * @Description:
 * @Author wuxinjian
 * @Date 2019/1/3 10:56
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserAo extends BaseAo {

    private String userId;

    private String username;

    private String password;

    private String name;

    private String phone;

    private String email;

    private Integer status;

    private String roleId;

    public static QueryWrapper pageCondition(UserAo userAo){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like(StringUtils.isNotBlank(userAo.getName()),User.NAME,userAo.getName())
                .like(StringUtils.isNotBlank(userAo.getUsername()),User.USERNAME,userAo.getUsername())
                .like(StringUtils.isNotBlank(userAo.getPhone()),User.PHONE,userAo.getPhone())
                .eq(Objects.nonNull(userAo.getStatus()),User.STATUS,userAo.getStatus())
                .eq(StringUtils.isNotBlank(userAo.getRoleId()),User.ROLE_ID,userAo.getRoleId());
        return wrapper;
    }

    @Override
    public String toString() {
        return "UserAo{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
