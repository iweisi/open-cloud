package com.github.lyd.gateway.producer.filter;

import com.github.lyd.common.constants.AuthorityConstants;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

public class GrantAccessVoter extends RoleVoter {

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        if (authentication == null) {
            return -1;
        } else {
            int result = 0;
            Collection<? extends GrantedAuthority> authorities = this.extractAuthorities(authentication);
            Iterator var6 = attributes.iterator();

            while(true) {
                ConfigAttribute attribute;
                do {
                    if (!var6.hasNext()) {
                       return result;
                    }

                    attribute = (ConfigAttribute)var6.next();
                } while(!this.supports(attribute));

                result = -1;
                Iterator var8 = authorities.iterator();

                while(var8.hasNext()) {
                    GrantedAuthority authority = (GrantedAuthority)var8.next();
                    // 全部权限放行
                    if(AuthorityConstants.AUTHORITY_ALL.contains(authority.getAuthority())){
                        return 1;
                    }
                    System.out.println();
                    if (attribute.getAttribute().equals(authority.getAuthority())) {
                        return 1;
                    }
                }
            }
        }
    }

    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
