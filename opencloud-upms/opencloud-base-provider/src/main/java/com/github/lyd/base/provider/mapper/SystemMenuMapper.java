package com.github.lyd.base.provider.mapper;

import com.github.lyd.base.client.dto.SystemMenuDto;
import com.github.lyd.base.client.entity.SystemMenu;
import com.github.lyd.common.mapper.CrudMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liuyadu
 */
@Repository
public interface SystemMenuMapper extends CrudMapper<SystemMenu> {
    List<SystemMenuDto> selectWithActionList();
}
