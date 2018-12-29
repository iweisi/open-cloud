package com.github.lyd.base.client.dto;

import com.github.lyd.base.client.entity.SystemAccount;

import java.io.Serializable;

/**
 * @author: liuyadu
 * @date: 2018/11/12 11:35
 * @description:
 */
public class SystemAccountDto extends SystemAccount implements Serializable {
    private static final long serialVersionUID = 6717800085953996702L;

    /**
     * 系统用户资料
     */
    private SystemUserDto userProfile;

    public SystemUserDto getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(SystemUserDto userProfile) {
        this.userProfile = userProfile;
    }
}
