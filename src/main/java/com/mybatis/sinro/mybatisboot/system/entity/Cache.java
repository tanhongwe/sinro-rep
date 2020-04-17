package com.mybatis.sinro.mybatisboot.system.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 缓存表
 * </p>
 *
 * @author wxjason123
 * @since 2019-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_cache")
public class Cache extends Model<Cache> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 验证码key
     */
    private String codeKey;
    /**
     * 验证码
     */
    private String codeValue;
    /**
     * 用户信息key
     */
    private String tokenKey;
    /**
     * 用户信息
     */
    private String tokenValue;
    /**
     * 类别 1表示验证码  2 表示jwt
     */
    private Integer type;
    /**
     * 创建时间
     */
    private Integer createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
