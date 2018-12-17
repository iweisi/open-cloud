package com.github.lyd.rbac.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.rbac.client.entity.ResourceMenu;

/**
 * 菜单资源
 */
public interface MenuService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<ResourceMenu> findListPage(PageParams pageParams, String keyword);

    /**
     * 查询列表
     * @param keyword
     * @return
     */
    PageList<ResourceMenu> findList(String keyword);

    /**
     * 根据主键获取菜单
     *
     * @param menuId
     * @return
     */
    ResourceMenu getMenu(Long menuId);

    /**
     * 检查菜单编码是否存在
     *
     * @param menuCode
     * @return
     */
    Boolean isExist(String menuCode);


    /**
     * 添加菜单资源
     *
     * @param menu
     * @return
     */
    Boolean addMenu(ResourceMenu menu);

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    Boolean updateMenu(ResourceMenu menu);

    /**
     * 更新启用禁用
     *
     * @param menuId
     * @param enable
     * @return
     */
    Boolean updateEnable(Long menuId, Boolean enable);

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    Boolean removeMenu(Long menuId);
}
