package com.github.lyd.rbac.producer.service.impl;

import com.github.lyd.common.exception.OpenMessageException;
import com.github.lyd.common.mapper.ExampleBuilder;
import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.constans.RbacConstans;
import com.github.lyd.rbac.client.entity.ResourceMenu;
import com.github.lyd.rbac.producer.mapper.ResourceMenuMapper;
import com.github.lyd.rbac.producer.service.MenuService;
import com.github.lyd.rbac.producer.service.PermissionService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private ResourceMenuMapper resourceMenuMapper;
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
    public PageList<ResourceMenu> findListPage(PageParams pageParams, String keyword) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit(), pageParams.getOrderBy());
        return findList(keyword);
    }

    /**
     * 查询列表
     * @param keyword
     * @return
     */
    @Override
    public PageList<ResourceMenu> findList(String keyword) {
        ExampleBuilder builder = new ExampleBuilder(ResourceMenu.class);
        Example example = builder.criteria()
                .orLike("menuCode", keyword)
                .orLike("menuName", keyword).end().build();
        List<ResourceMenu> list = resourceMenuMapper.selectByExample(example);
        return new PageList(list);
    }

    /**
     * 根据主键获取菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public ResourceMenu getMenu(Long menuId) {
        return resourceMenuMapper.selectByPrimaryKey(menuId);
    }

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    @Override
    public boolean isExist(String menuCode) {
        ExampleBuilder builder = new ExampleBuilder(ResourceMenu.class);
        Example example = builder.criteria()
                .andEqualTo("menuCode", menuCode)
                .end().build();
        int count = resourceMenuMapper.selectCountByExample(example);
        return count > 0 ? true : false;
    }

    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public boolean addMenu(ResourceMenu menu) {
        if (isExist(menu.getMenuCode())) {
            throw new OpenMessageException(String.format("%s菜单编码已存在,不允许重复添加", menu.getMenuCode()));
        }
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        if (menu.getPriority() == null) {
            menu.setPriority(0);
        }
        menu.setCreateTime(new Date());
        menu.setUpdateTime(menu.getCreateTime());
        int count = resourceMenuMapper.insertSelective(menu);
        return count > 0;
    }

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    @Override
    public boolean updateMenu(ResourceMenu menu) {
        ResourceMenu savedMenu = getMenu(menu.getMenuId());
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
        int count = resourceMenuMapper.updateByPrimaryKeySelective(menu);
        // 同步授权表里的资源
        permissionService.updatePermission(RbacConstans.RESOURCE_TYPE_ACTION,menu.getMenuId());
        return count > 0;
    }

    /**
     * 更新启用禁用
     *
     * @param menuId
     * @param enable
     * @return
     */
    @Override
    public boolean updateEnable(Long menuId, Boolean enable) {
        ResourceMenu menu = new ResourceMenu();
        menu.setMenuId(menuId);
        menu.setEnabled(enable);
        menu.setUpdateTime(new Date());
        int count = resourceMenuMapper.updateByPrimaryKeySelective(menu);
        return count > 0;
    }

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    @Override
    public boolean removeMenu(Long menuId) {
        if (permissionService.isExist(menuId, RbacConstans.RESOURCE_TYPE_MENU)) {
            throw new OpenMessageException(String.format("%s已被授权使用,不允许删除!", menuId));
        }
        int count = resourceMenuMapper.deleteByPrimaryKey(menuId);
        return count > 0;
    }


}
