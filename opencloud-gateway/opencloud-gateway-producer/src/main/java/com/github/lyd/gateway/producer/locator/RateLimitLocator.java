package com.github.lyd.gateway.producer.locator;

import com.github.lyd.common.utils.StringUtils;
import com.github.lyd.gateway.client.entity.GatewayRateLimit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.support.StringToMatchTypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态限流加载器
 *
 * @author: liuyadu
 * @date: 2018/10/23 10:31
 * @description:
 */
@Slf4j
public class RateLimitLocator {
    private JdbcTemplate jdbcTemplate;
    private RateLimitProperties properties;
    private StringToMatchTypeConverter converter;

    public RateLimitLocator(JdbcTemplate jdbcTemplate, RateLimitProperties properties) {
        this.jdbcTemplate = jdbcTemplate;
        this.properties = properties;
        this.converter = new StringToMatchTypeConverter();
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RateLimitProperties getProperties() {
        return properties;
    }

    public void setProperties(RateLimitProperties properties) {
        this.properties = properties;
    }

    public StringToMatchTypeConverter getConverter() {
        return converter;
    }

    public void setConverter(StringToMatchTypeConverter converter) {
        this.converter = converter;
    }

    /**
     * 刷新限流配置
     */
    public void doRefresh() {
        loadRateLimit();
    }

    /**
     * 加载数据库限流配置
     * 1. 认证用户（Authenticated User）
     * 使用已认证的用户名（username）或'anonymous'
     * 2. 原始请求（Request Origin）
     * 使用系统用户的原始请求
     * 3. URL
     * 使用上游请求的地址
     * 4. 针对每个服务的全局配置
     * 该方式不会验证Request Origin，Authenticated User或URL
     * 使用该方式只需不设置‘type’参数即可
     *
     * @return
     */
    public Map<String, List<RateLimitProperties.Policy>> loadRateLimit() {
        log.debug("=============加载动态限流==============");
        LinkedHashMap<String, List<RateLimitProperties.Policy>> policysMap = Maps.newLinkedHashMap();
        //从application.properties中加载限流信息
        policysMap.putAll(properties.getPolicyList());
        //从db中加载限流信息
        policysMap.putAll(loadRateLimitWithDb());
        properties.setPolicyList(policysMap);
        return policysMap;
    }


    protected Map<String, List<RateLimitProperties.Policy>> loadRateLimitWithDb() {
        Map<String, List<RateLimitProperties.Policy>> policyMap = Maps.newLinkedHashMap();
        try{
            List<GatewayRateLimit> results = jdbcTemplate.query("select * from gateway_rate_limit  where status = 1", new
                    BeanPropertyRowMapper<>(GatewayRateLimit.class));
            if (results != null && results.size() > 0) {
                for (GatewayRateLimit result : results) {
                    List<RateLimitProperties.Policy> policyList = policyMap.get(result.getServiceId());
                    if (policyList == null) {
                        policyList = Lists.newArrayList();
                    }
                    RateLimitProperties.Policy policy = new RateLimitProperties.Policy();
                    String key = result.getServiceId() + ":" + result.getLimit() + ":" + result.getInterval();
                    Boolean flag = false;
                    for (RateLimitProperties.Policy p : policyList) {
                        String pkey = result.getServiceId() + ":" + p.getLimit() + ":" + p.getRefreshInterval();
                        if (pkey.equals(key)) {
                            policy = p;
                            flag = true;
                            break;
                        }
                    }
                    policy.setLimit(result.getLimit());
                    policy.setRefreshInterval(result.getInterval());
                    if (StringUtils.isNotBlank(result.getType())) {
                        String type = result.getType().concat("=").concat(result.getRules());
                        RateLimitProperties.Policy.MatchType matchType = converter.convert(type);
                        policy.getType().add(matchType);
                    }
                    if (!flag) {
                        policyList.add(policy);
                    }
                    policyMap.put(result.getServiceId(), policyList);
                }
            }
        }catch (Exception e){
            log.error("加载动态限流错误:{}",e.getMessage());
        }
        return policyMap;
    }
}
