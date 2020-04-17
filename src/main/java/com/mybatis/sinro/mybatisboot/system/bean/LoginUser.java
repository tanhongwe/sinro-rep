package com.mybatis.sinro.mybatisboot.system.bean;

import com.mybatis.sinro.mybatisboot.common.bean.BaseAo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName: LoginUser
 * @Package com.tce.system.ao
 * @Description:
 * @Author wuxinjian
 * @Date 2018/12/13 14:19
 * @Version V1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUser extends BaseAo {

    private String username;

    private String password;

}
