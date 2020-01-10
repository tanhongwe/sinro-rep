package com.mybatis.sinro.mybatisboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.mybatis.sinro.mybatisboot.common.annotation.ExcelExportColumn;
import lombok.Data;

/**
 * @author: tan.hongwei
 * @Date: 2019/12/23 15:36
 * @Description:
 */
@Data
@TableName("t_user")
@ExcelExportColumn
public class User extends Model {

    @TableId(value = "id", type = IdType.ID_WORKER)
    @ExcelExportColumn(value = false)
    private Long id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("number")
    private String number;
}
