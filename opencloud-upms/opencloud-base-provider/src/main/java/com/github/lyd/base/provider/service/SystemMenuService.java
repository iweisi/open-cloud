package com.github.lyd.base.provider.service;

import com.github.lyd.base.client.dto.SystemMenuDto;
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
     * 获取菜单和操作列表
     * @param keyword
     * @return
     */
    PageList<SystemMenuDto> findWithActionList(String keyword);

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
    Long addMenu(SystemMenu menu);

    /**
     * 修改菜单资源
     *
     * @param menu
     * @return
     */
    void updateMenu(SystemMenu menu);

    /**
     * 更新启用禁用
     *
     * @param menuId
     * @param status
     * @return
     */
    void updateStatus(Long menuId, Integer status);

    /**
     * 移除菜单
     *
     * @param menuId
     * @return
     */
    void removeMenu(Long menuId);
}
