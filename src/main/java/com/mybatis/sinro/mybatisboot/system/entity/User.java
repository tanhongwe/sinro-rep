package com.mybatis.sinro.mybatisboot.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.mybatis.sinro.mybatisboot.common.enumeration.Delete;
import com.mybatis.sinro.mybatisboot.common.enumeration.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author wxjason123
 * @since 2018-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_user")
public class User extends Model<User> implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "id", type = IdType.UUID)
    private String id;
    /**
     * 用户名
     */
    @TableField("role_id")
    private String roleId;
    /**
     * 用户名
     */
    @TableField("username")
    private String username;
    /**
     * 登录密码
     */
    @TableField("password")
    private String password;
    /**
     * 用户姓名
     */
    @TableField("name")
    private String name;
    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;
    /**
     * 邮箱
     */
    @TableField("email")
    private String email;
    /**
     * 邮箱
     */
    @TableField("status")
    private Integer status;
    /**
     * 邮箱
     */
    @TableField("del")
    private Integer del;
    /**
     * 创建用户id
     */
    @TableField("create_user_id")
    private String createUserId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createTime;
    /**
     * 修改用户id
     */
    @TableField("update_user_id")
    private String updateUserId;
    /**
     * 最后修改时间
     */
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private Collection<? extends GrantedAuthority> authorities;

    @TableField(exist = false)
    private List<Integer> menuIds;


    public static final String ID = "id";

    public static final String ROLE_ID = "role_id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String NAME = "name";

    public static final String PHONE = "phone";

    public static final String EMAIL = "email";

    public static final String STATUS = "status";

    public static final String DEL = "del";

    public static final String CREATE_USER_ID = "create_user_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Status.ENABLE.getStatus().equals(this.status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Delete.NORMOL.getCode().equals(this.del);
    }
}
