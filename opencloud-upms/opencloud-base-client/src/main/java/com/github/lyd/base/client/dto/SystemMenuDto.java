package com.github.lyd.base.client.dto;

import com.github.lyd.base.client.entity.SystemAction;
import com.github.lyd.base.client.entity.SystemMenu;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuyadu
 */
public class SystemMenuDto extends SystemMenu implements Serializable {

    private static final long serialVersionUID = 3474271304324863160L;

    private List<SystemAction> actionList;

    public List<SystemAction> getActionList() {
        return actionList;
    }

    public void setActionList(List<SystemAction> actionList) {
        this.actionList = actionList;
    }
}
