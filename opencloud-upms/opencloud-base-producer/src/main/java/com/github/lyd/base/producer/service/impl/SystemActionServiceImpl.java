package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemAction;
import com.github.lyd.base.producer.mapper.SystemActionMapper;
import com.github.lyd.base.producer.service.SystemGrantAccessService;
import com.github.lyd.base.producer.service.SystemActionService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
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
public class SystemActionServiceImpl implements SystemActionService {
    @Autowired
    private SystemActionMapper systemActionMapper;
    @Autowired
    private SystemGrantAccessService systemAccessService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemAction> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        ExampleBuilder builder = new ExampleBuilder(SystemAction.class);
        Example example = builder.criteria()
                .orLike("actionCode", keyword)
                .orLike("actionName", keyword).end().build();
        List<SystemAction> list = systemActionMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemAction> findList(String keyword, Long menuId) {
        ExampleBuilder builder = new ExampleBuilder(SystemAction.class);
        Example example = builder
                .criteria()
                .orLike("actionCode", keyword)
                .orLike("actionName", keyword).end()
                .and()
                .andEqualTo("menuId", menuId).end()
                .build();
        example.orderBy("actionId").asc().orderBy("priority").asc();
        List<SystemAction> list = systemActionMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取Action
     *
     * @param actionId
     * @return
     */
    @Override
    public SystemAction getAction(Long actionId) {
        return systemActionMapper.selectByPrimaryKey(actionId);
    }


    /**
     * 检查Action编码是否存在
     *
     * @param actionCode
     * @return
     */
    @Override
    public Boolean isExist(String actionCode) {
        ExampleBuilder builder = new ExampleBuilder(SystemAction.class);
        Example example = builder.criteria()
                .andEqualTo("actionCode", actionCode)
                .end().build();
        int count = systemActionMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加Action操作
     *
     * @param action
     * @return
     */
    @Override
    public Long addAction(SystemAction action) {
        if (isExist(action.getActionCode())) {
            throw new OpenMessageException(String.format("%sAction编码已存在,不允许重复添加", action.getActionCode()));
        }
        if (action.getMenuId() == null) {
            action.setMenuId(0L);
        }
        if (action.getPriority() == null) {
            action.setPriority(0);
        }
        if (action.getStatus() == null) {
            action.setStatus(BaseConstants.ENABLED);
        }
        if (action.getIsPersist() == null) {
            action.setIsPersist(BaseConstants.DISABLED);
        }
        action.setCreateTime(new Date());
        action.setUpdateTime(action.getCreateTime());
        systemActionMapper.insertSelective(action);
        return action.getActionId();
    }

    /**
     * 修改Action操作
     *
     * @param action
     * @return
     */
    @Override
    public void updateAction(SystemAction action) {
        SystemAction savedAction = getAction(action.getActionId());
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
        systemActionMapper.updateByPrimaryKeySelective(action);
        // 同步授权表里的信息
        systemAccessService.updateGrantAccess(BaseConstants.RESOURCE_TYPE_ACTION, action.getActionId());
    }

    /**
     * 更新启用禁用
     *
     * @param actionId
     * @param status
     * @return
     */
    @Override
    public void updateStatus(Long actionId, Integer status) {
        SystemAction action = new SystemAction();
        action.setActionId(actionId);
        action.setStatus(status);
        action.setUpdateTime(new Date());
        systemActionMapper.updateByPrimaryKeySelective(action);
        // 同步授权表里的信息
        systemAccessService.updateGrantAccess(BaseConstants.RESOURCE_TYPE_ACTION, action.getActionId());
    }

    /**
     * 移除Action
     *
     * @param actionId
     * @return
     */
    @Override
    public void removeAction(Long actionId) {
        SystemAction action = getAction(actionId);
        if (action != null && action.getIsPersist().equals(BaseConstants.ENABLED)) {
            throw new OpenMessageException(String.format("保留数据,不允许删除"));
        }
        if (systemAccessService.isExist(actionId, BaseConstants.RESOURCE_TYPE_ACTION)) {
            throw new OpenMessageException(String.format("资源已被授权,不允许删除,请取消授权后,再次尝试!"));
        }
        systemActionMapper.deleteByPrimaryKey(actionId);
    }


}
