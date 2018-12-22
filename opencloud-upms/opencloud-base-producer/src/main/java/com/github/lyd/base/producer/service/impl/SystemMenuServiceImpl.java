package com.github.lyd.base.producer.service.impl;

import com.github.lyd.base.producer.mapper.SystemMenuMapper;
import com.github.lyd.base.producer.service.SystemMenuService;
import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.client.entity.SystemMenu;
import com.github.lyd.base.producer.service.SystemAccessService;
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
public class SystemMenuServiceImpl implements SystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Autowired
    private SystemAccessService systemAccessService;

    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemMenu> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        return findList(keyword);
    }

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    @Override
    public PageList<SystemMenu> findList(String keyword) {
        ExampleBuilder builder = new ExampleBuilder(SystemMenu.class);
        Example example = builder.criteria()
                .orLike("menuCode", keyword)
                .orLike("menuName", keyword).end().build();
        List<SystemMenu> list = systemMenuMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public SystemMenu getMenu(Long menuId) {
        return systemMenuMapper.selectByPrimaryKey(menuId);
    }

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    @Override
    public Boolean isExist(String menuCode) {
        ExampleBuilder builder = new ExampleBuilder(SystemMenu.class);
        Example example = builder.criteria()
                .andEqualTo("menuCode", menuCode)
                .end().build();
        int count = systemMenuMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean addMenu(SystemMenu menu) {
        if (isExist(menu.getMenuCode())) {
            throw new OpenMessageException(String.format("%s菜单编码已存在,不允许重复添加", menu.getMenuCode()));
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        if (menu.getStatus() == null) {
            menu.setStatus(BaseConstants.ENABLED);
        }
        menu.setCreateTime(new Date());
        menu.setUpdateTime(menu.getCreateTime());
        int count = systemMenuMapper.insertSelective(menu);
        return count > 0;
    }

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public Boolean updateMenu(SystemMenu menu) {
        if(menu.getMenuId()==null){
            throw new OpenMessageException("ID不能为空");
        }
        SystemMenu savedMenu = getMenu(menu.getMenuId());
        if (savedMenu == null) {
            throw new OpenMessageException(String.format("%s菜单不存在", menu.getMenuId()));
        }
        if (!savedMenu.getMenuCode().equals(menu.getMenuCode())) {
            // 和原来不一致重新检查唯一性
            if (isExist(menu.getMenuCode())) {
                throw new OpenMessageException(String.format("%s菜单编码已存在,不允许重复添加", menu.getMenuCode()));
            }
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0l);
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        menu.setUpdateTime(new Date());
        int count = systemMenuMapper.updateByPrimaryKeySelective(menu);
        // 同步授权表里的信息
        systemAccessService.updateAccess(BaseConstants.RESOURCE_TYPE_ACTION, menu.getMenuId());
        return count > 0;
    }

    /**
     * 更新启用禁用
     *
     * @param menuId
     * @param status
     * @return
     */
    @Override
    public Boolean updateStatus(Long menuId, Integer status) {
        SystemMenu menu = new SystemMenu();
        menu.setMenuId(menuId);
        menu.setStatus(status);
        menu.setUpdateTime(new Date());
        int count = systemMenuMapper.updateByPrimaryKeySelective(menu);
        // 同步授权表里的信息
        systemAccessService.updateAccess(BaseConstants.RESOURCE_TYPE_ACTION, menu.getMenuId());
        return count > 0;
    }

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public Boolean removeMenu(Long menuId) {
        if (systemAccessService.isExist(menuId, BaseConstants.RESOURCE_TYPE_MENU)) {
            throw new OpenMessageException(String.format("%s已被授权使用,不允许删除!", menuId));
        }
        int count = systemMenuMapper.deleteByPrimaryKey(menuId);
        return count > 0;
    }


}
