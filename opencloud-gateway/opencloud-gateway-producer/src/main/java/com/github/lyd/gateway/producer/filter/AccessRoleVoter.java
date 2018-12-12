package com.github.lyd.gateway.producer.filter;

import com.github.lyd.rbac.client.constans.RbacConstans;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 重写角色投票器
 *
 * @author liuyadu
 */
@Slf4j
public class AccessRoleVoter extends RoleVoter {

    public Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return -1;
        } else {
            // 角色投票鉴权
            int result = 0;

            Collection<? extends GrantedAuthority> authorities = this.extractAuthorities(authentication);
            Iterator var6 = attributes.iterator();
            Iterator var8 = authorities.iterator();
            while (var8.hasNext()) {
                GrantedAuthority authority = (GrantedAuthority) var8.next();
                if (authority.getAuthority().equals(RbacConstans.SUPER_AUTHORITY)) {
                    //超级管理员拥有全部权限,直接通过
                    return 1;
                }
            }
            while (true) {
                ConfigAttribute attribute;
                do {
                    if (!var6.hasNext()) {
                        return result;
                    }

                    attribute = (ConfigAttribute) var6.next();
                } while (!this.supports(attribute));

                result = -1;
                while (var8.hasNext()) {
                    GrantedAuthority authority = (GrantedAuthority) var8.next();
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return 1;
                    }
                }
            }
        }
    }
}
