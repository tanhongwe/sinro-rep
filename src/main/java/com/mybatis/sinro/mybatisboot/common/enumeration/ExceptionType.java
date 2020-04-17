package com.mybatis.sinro.mybatisboot.common.enumeration;

import java.util.Objects;

/**
 * @author fengshuonan
 * @Description 所有业务异常的枚举
 * @date 2016年11月12日 下午5:04:51
 */
public enum ExceptionType {

    /**
     * 操作成功
     */
    HANDLE_SUCCESS(200, "成功"),

    BAD_REQUEST(400, "请求有错误"),

    LOGIN_FAILED(401, "登录失败"),

    TOKEN_ERROR(403, "Token认证失败"),

    SERVER_ERROR(500, "服务器异常"),

    SYSTEM_MAINTENANCE(500, "服务器维护中，请稍后再进行尝试"),

    SYSTEM_BUSY(500, "系统繁忙,请稍后再试"),

    FILE_SERVER_ERROR(500, "文件服务器异常"),

    /**
     * Excel
     */
    EXCEL_UPLOAD_FAIL_TITLE_ERROR(20001,"上传失败，模板不正确"),
    EXCEL_CONTENT_EMPTY(20002,"Excel中内容为空"),
    EXCEL_CREATE_EXCEPTION(20003,"Excel创建异常"),

    /**
     * 加解密
     */
    ENCRYPT_METHOD_IS_NULL(30000,"加密方式为空"),



    /**
     * 登录
     */
    LOGIN_USERNAME_EMPTY(10601, "用户名不能为空"),
    LOGIN_PASSWORD_EMPTY(10602, "密码不能为空"),
    LOGIN_USERNAME_NOT_EXISTS(10603, "用户名不存在"),
    LOGIN_PASSWORD_ERROR(10604, "密码错误"),
    LOGIN_USER_DISABLED(10605, "该用户已被禁用"),
    LOGIN_USERNAME_PASSWORD_ERROR(10606, "用户名或密码错误"),

    /**
     * 角色
     */
    ROLE_ID_EMPTY(10701, "角色ID不能为空"),
    ROLE_NAME_EMPTY(10702, "角色名称不能为空"),
    ROLE_NAME_IS_EXISTS(10703, "角色名称已存在"),
    ROLE_HAS_USERS(10704, "该角色已有用户绑定,无法删除"),
    ROLE_ID_NOT_EXISTS(10705, "角色ID不存在"),
    ROLE_NAME_ERROR(10706, "角色名称不合法,请输入1-30个字符的汉字、英文、数字、下划线"),
    ROLE_ADMIN_NOT_UPDATE(10707, "不可操作超级管理员角色"),
    ROLE_REMARK_LENGTH_LIMIT(10708, "备注信息超过长度超长,最大长度100个字符"),

    /**
     * 菜单
     */
    MENU_ID_HAS_SAME(10801, "有相同菜单ID"),
    MENU_ID_NOT_EXISTS(10802, "菜单ID不存在"),
    MENU_NAME_ERROR(10803, "菜单名称不合法,请输入1-30个字符的汉字、英文、数字、下划线"),
    MENU_ICON_LENGTH_LIMIT(10804, "菜单图标长度超长,最大长度100个字符"),
    MENU_URL_LENGTH_LIMIT(10805, "菜单跳转路径长度超长,最大长度100个字符"),
    MENU_TYPE_ERROR(10806, "菜单类型不合法,（1：菜单  2：按钮  3：接口）"),
    MENU_REMARK_LENGTH_LIMIT(10807, "菜单备注长度超长,最大长度100个字符"),
    MENU_PID_NOT_EXISTS(10808, "父级菜单不存在"),
    MENU_ID_EMPTY(10809, "菜单ID不存在"),
    MENU_PID_IS_SELF(10810, "当前菜单不可作为自己父级菜单"),

    /**
     * 用户
     */
    USER_ID_NOT_EXISTS(10901, "用户ID不存在"),
    USER_ID_EMPTY(10902, "用户ID不能为空"),
    USER_ADMIN_NOT_UPDATE(10903, "不可操作admin用户"),
    USER_USERNAME_ERROR(10904, "用户名不合法,请输入1-30个字符的英文、数字、下划线"),
    USER_USERNAME_EXISTS(10905, "该用户名已存在"),
    USER_PASSWORD_ERROR(10906, "密码不合法,请输入1-20个字符的英文、数字、下划线"),
    USER_NAME_ERROR(10907, "用户姓名不合法,请输入1-30个字符的汉字、英文、数字、下划线"),
    USER_PHONE_ERROR(10908, "用户手机号不合法,请输入1-20个数字"),
    USER_EMAIL_ERROR(10909, "用户邮箱输入不合法"),
    USER_OLD_PASSWORD_EMPTY(10910, "原密码不能为空"),
    USER_OLD_PASSWORD_ERROR(10911, "原密码错误"),
    USER_NEW_PASSWORD_EMPTY(10912, "新密码不能为空"),
    USER_NEW_PASSWORD_NOT_SAME(10913, "两次密码输入不一致"),
    USER_PHONE_EXISTS(10914, "该手机号已被其他用户绑定"),
    USER_EMAIL_EXISTS(10915, "该邮箱已被其他用户绑定"),
    USER_ENABLED_TO_DELETE(10916, "该用户启用中,请停用后再删除"),
    USER_NOT_DISABLE_SELF(10917, "不可停用当前登录用户"),
    USER_NOT_DELETE_SELF(10918, "不可删除当前登录用户");

    ExceptionType(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public static ExceptionType code(Integer type) {
        if (Objects.nonNull(type)) {
            for (ExceptionType t : ExceptionType.values()) {
                if (Objects.nonNull(t.code) && t.code.intValue() == type) {
                    return t;
                }
            }
        }
        return null;
    }

    public static String message (Integer code) {
        ExceptionType exceptionType = code(code);
        return Objects.nonNull(exceptionType) ? exceptionType.getMessage() : "";
    }
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
