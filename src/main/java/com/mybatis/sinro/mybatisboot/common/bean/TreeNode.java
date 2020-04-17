package com.mybatis.sinro.mybatisboot.common.bean;

import com.mybatis.sinro.mybatisboot.common.utils.CollectionUtils;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuNode
 * @Author: tan.hongwei
 * @Date: 2018/11/21 08:54
 * @Description:
 **/
@Data
public class TreeNode<T extends TreeNode> {
    protected Integer id;
    protected String name;
    protected List<T> children;

    public void append(T child){
        if(CollectionUtils.isEmpty(this.children)){
            this.children = new ArrayList<>();
        }
        this.children.add(child);
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", children=" + children +
                '}';
    }
}
