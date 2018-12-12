package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.entity.ResourceAction;
import com.github.lyd.rbac.producer.mapper.ResourceActionMapper;
import com.github.lyd.rbac.producer.service.ActionService;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author liuyadu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ActionServiceImpl implements ActionService {
    @Autowired
    private ResourceActionMapper resourceActionMapper;
    @Autowired
    private PermissionService permissionService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<ResourceAction> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        ExampleBuilder builder = new ExampleBuilder(ResourceAction.class);
        Example example = builder.criteria()
                .orLike("actionCode", keyword)
                .orLike("actionName", keyword).end().build();
        List<ResourceAction> list = resourceActionMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取Action
     *
     * @param actionId
     * @return
     */
    @Override
    public ResourceAction getAction(Long actionId) {
        return resourceActionMapper.selectByPrimaryKey(actionId);
    }


    /**
     * 检查Action编码是否存在
     *
     * @param actionCode
     * @return
     */
    @Override
    public boolean isExist(String actionCode) {
        ExampleBuilder builder = new ExampleBuilder(ResourceAction.class);
        Example example = builder.criteria()
                .andEqualTo("actionCode", actionCode)
                .end().build();
        int count = resourceActionMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加Action动作
     *
     * @param action
     * @return
     */
    @Override
    public boolean addAction(ResourceAction action) {
        if (isExist(action.getActionCode())) {
            throw new OpenMessageException(String.format("%sAction编码已存在,不允许重复添加", action.getActionCode()));
        }
        if (action.getMenuId() == null) {
            action.setMenuId(0l);
        }
        if (action.getPriority() == null) {
            action.setPriority(0);
        }
        action.setCreateTime(new Date());
        action.setUpdateTime(action.getCreateTime());
        int count = resourceActionMapper.insertSelective(action);
        return count > 0;
    }

    /**
     * 修改Action动作
     *
     * @param action
     * @return
     */
    @Override
    public boolean updateAction(ResourceAction action) {
        ResourceAction savedAction = getAction(action.getActionId());
        if (savedAction == null) {
            throw new OpenMessageException(String.format("%sAction不存在", action.getActionId()));
        }
        if (!savedAction.getActionCode().equals(action.getActionCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(action.getActionCode())) {
                throw new OpenMessageException(String.format("%sAction编码已存在,不允许重复添加", action.getActionCode()));
            }
        }
        if (action.getMenuId() == null) {
            action.setMenuId(0L);
        }
        if (action.getPriority() == null) {
            action.setPriority(0);
        }
        action.setUpdateTime(new Date());
        int count = resourceActionMapper.updateByPrimaryKeySelective(action);
        // 同步授权表里的资源
        permissionService.updatePermission(RbacConstans.RESOURCE_TYPE_ACTION,action.getActionId());
        return count > 0;
    }

    /**
     * 更新启用禁用
     *
     * @param actionId
     * @param enable
     * @return
     */
    @Override
    public boolean updateEnable(Long actionId, Boolean enable) {
        ResourceAction action = new ResourceAction();
        action.setActionId(actionId);
        action.setEnabled(enable);
        action.setUpdateTime(new Date());
        int count = resourceActionMapper.updateByPrimaryKeySelective(action);
        return count > 0;
    }

    /**
     * 移除Action
     *
     * @param actionId
     * @return
     */
    @Override
    public boolean removeAction(Long actionId) {
        if (permissionService.isExist(actionId, RbacConstans.RESOURCE_TYPE_ACTION)) {
            throw new OpenMessageException(String.format("%s已被授权使用,不允许删除!", actionId));
        }
        int count = resourceActionMapper.deleteByPrimaryKey(actionId);
        return count > 0;
    }


}
