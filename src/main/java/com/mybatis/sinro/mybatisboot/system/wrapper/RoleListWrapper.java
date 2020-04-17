package com.mybatis.sinro.mybatisboot.system.wrapper;


import com.mybatis.sinro.mybatisboot.common.utils.DateUtils;
import com.mybatis.sinro.mybatisboot.common.wrapper.BaseWrapper;
import com.mybatis.sinro.mybatisboot.system.bean.RoleListVo;
import com.mybatis.sinro.mybatisboot.system.entity.Role;
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
public class RoleListWrapper extends BaseWrapper<Role, RoleListVo> {

    @Override
    protected RoleListVo warp(Role model) throws IOException {
        RoleListVo vo = new RoleListVo();
        vo.setRoleId(model.getId());
        vo.setRoleName(model.getName());
        vo.setRemark(model.getRemark());
        vo.setCreateTime(DateUtils.getDateTimeAsString(model.getCreateTime()));
        return vo;
    }
}
