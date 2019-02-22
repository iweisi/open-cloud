package com.github.lyd.base.provider.service.impl;

import com.github.lyd.base.client.constants.BaseConstants;
import com.github.lyd.base.provider.service.SystemGrantAccessService;
import com.github.lyd.common.test.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: liuyadu
 * @date: 2018/12/28 13:31
 * @description:
 */
public class SystemGrantAccessServiceImplTest extends BaseTest {
    @Autowired
    private SystemGrantAccessService systemGrantAccessService;

    @Test
    public void getUserGrantAccess() throws Exception {
    }

    @Test
    public void getUserPrivateGrantAccess() throws Exception {
    }

    @Test
    public void getGrantAccessList() throws Exception {
    }

    @Test
    public void addGrantAccess() throws Exception {
    }

    @Test
    public void updateGrantAccess() throws Exception {
        String[] ids = new String[]{
                "1",
                "2",
                "3",
                "4",
                "7",
                "6",
                "8",
                "9",
                "10"
        };
        for (String id : ids) {
            systemGrantAccessService.updateGrantAccess(BaseConstants.RESOURCE_TYPE_MENU, Long.parseLong(id));
        }
    }

    @Test
    public void isExist() throws Exception {
    }

    @Test
    public void buildGrantAccess() throws Exception {
    }

}