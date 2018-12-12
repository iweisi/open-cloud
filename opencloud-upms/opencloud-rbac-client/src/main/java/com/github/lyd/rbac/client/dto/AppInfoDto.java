package com.github.lyd.rbac.client.dto;

import com.github.lyd.auth.client.dto.ClientDetailsDto;
import com.github.lyd.rbac.client.entity.AppInfo;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author liuyadu
 */
public class AppInfoDto extends AppInfo implements Serializable {
    private static final long serialVersionUID = 7902161098976147412L;
    @Transient
    private ClientDetailsDto clientInfo;

    public ClientDetailsDto getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientDetailsDto clientInfo) {
        this.clientInfo = clientInfo;
    }
}
