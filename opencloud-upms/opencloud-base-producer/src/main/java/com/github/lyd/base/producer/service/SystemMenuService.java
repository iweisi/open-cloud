package com.github.lyd.base.producer.service;

import com.github.lyd.common.model.PageList;
import com.github.lyd.common.model.PageParams;
import com.github.lyd.base.client.entity.SystemMenu;

/**
 * 菜单资源
 * @author liuyadu
 */
public interface SystemMenuService {
    /**
     * 分页查询
     *
     * @param pageParams
     * @param keyword
     * @return
     */
    PageList<SystemMenu> findListPage(PageParams pageParams, String keyword);

    /**
     * 查询列表
     * @param keyword
     * @return
     */
    PageList<SystemMenu> findList(String keyword);

    /**
     * 根据主键获取菜单
     *
     * @param menuId
     * @return
     */
    SystemMenu getMenu(Long menuId);

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
    Boolean addMenu(SystemMenu menu);

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    Boolean updateMenu(SystemMenu menu);

    /**
     * 更新启用禁用
     *
     * @param menuId
     * @param status
     * @return
     */
    Boolean updateStatus(Long menuId, Integer status);

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    Boolean removeMenu(Long menuId);
}
