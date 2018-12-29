package com.github.lyd.common.security;

import com.github.lyd.common.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuyadu
 */
@Slf4j
public class OpenUserAuthenticationConverter extends DefaultUserAuthenticationConverter {


    public OpenUserAuthenticationConverter() {
    }

    private Object converter(Map<String, ?> map) {
        Map<String, Object> params = new HashMap<String, Object>();
        for (String key : map.keySet()) {
            if (USERNAME.equals(key)) {
                params.putAll((Map) map.get(key));
            } else {
                params.put(key, map.get(key));
            }
        }
        OpenUserAuth auth = BeanUtils.mapToBean(params, OpenUserAuth.class);
        auth.setAuthAppId(params.get(AccessTokenConverter.CLIENT_ID).toString());
        auth.setAuthorities(AuthorityUtils.authorityListToSet(getAuthorities(map)));
        return auth;
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        response.put(USERNAME, authentication.getPrincipal());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Object principal = converter(map);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            if (principal != null) {
                OpenUserAuth user = (OpenUserAuth) principal;
                authorities = user.getAuthorities();
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return AuthorityUtils.NO_AUTHORITIES;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

}
