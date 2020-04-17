package com.mybatis.sinro.mybatisboot.system.wrapper;


import com.mybatis.sinro.mybatisboot.common.utils.DateUtils;
import com.mybatis.sinro.mybatisboot.common.wrapper.BaseWrapper;
import com.mybatis.sinro.mybatisboot.system.bean.UserVo;
import com.mybatis.sinro.mybatisboot.system.entity.User;
import com.mybatis.sinro.mybatisboot.system.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @ClassName: ChannelListWrapper
 * @Package com.tce.operator.gzyd.admin.wrapper
 * @Description:
 * @Author wuxinjian
 * @Date 2018/12/25 9:33
 * @Version V1.0
 */
@Component
public class UserListWrapper extends BaseWrapper<User, UserVo> {

    @Autowired
    private IRoleService roleService;

    @Override
    protected UserVo warp(User model) throws IOException {
        UserVo vo = new UserVo();
        BeanUtils.copyProperties(model, vo);
        vo.setUserId(model.getId());
        vo.setCreateTime(DateUtils.getDateTimeAsString(model.getCreateTime()));
        vo.setRoleName(roleService.getRoleByRoleId(model.getRoleId()).getName());
        return vo;
    }

}
