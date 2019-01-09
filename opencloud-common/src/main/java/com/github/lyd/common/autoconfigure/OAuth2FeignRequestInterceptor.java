/**
 * Copyright (c) 2015 the original author or authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.lyd.common.autoconfigure;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * 微服务之间feign调用请求头丢失的问题
 * 加入微服务之间传递的唯一标识,便于追踪
 */
@Slf4j
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {

    /**
     * 微服务之间传递的唯一标识
     */
    private static final String X_REQUEST_SID = "X-Request-serialId";

    /**
     * The authorization header name.
     */
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * The {@code Bearer} token type.
     */
    private static final String BEARER_TOKEN_TYPE = "Bearer";

    /**
     * Current OAuth2 authentication context.
     */
    private final OAuth2ClientContext oauth2ClientContext;

    /**
     * Creates new instance of {@link OAuth2FeignRequestInterceptor} with client context.
     *
     * @param oauth2ClientContext the OAuth2 client context
     */
    public OAuth2FeignRequestInterceptor(OAuth2ClientContext oauth2ClientContext) {
        Assert.notNull(oauth2ClientContext, "Context can not be null");
        this.oauth2ClientContext = oauth2ClientContext;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void apply(RequestTemplate template) {
        if (template.headers().containsKey(AUTHORIZATION_HEADER)) {
            log.debug("The Authorization token has been already set");
        } else if (oauth2ClientContext.getAccessTokenRequest().getExistingToken() == null) {
            log.debug("Can not obtain existing token for request, if it is a non secured request, ignore.");
        } else {
            log.debug("Constructing Header {} for Token {}", AUTHORIZATION_HEADER, BEARER_TOKEN_TYPE);
            template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER_TOKEN_TYPE,
                    oauth2ClientContext.getAccessTokenRequest().getExistingToken().toString()));
        }
        //增加自定义请求头
        if (!template.headers().containsKey(X_REQUEST_SID)) {
            String sid = String.valueOf(UUID.randomUUID());
            template.header(X_REQUEST_SID, sid);
        }
        log.debug("FeignRequest:{}", template.toString());
    }
}