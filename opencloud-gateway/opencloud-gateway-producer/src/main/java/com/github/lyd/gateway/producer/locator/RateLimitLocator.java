package com.github.lyd.gateway.producer.locator;

import com.github.lyd.common.utils.StringUtils;
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
        ;
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
     * 使用用户的原始请求
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
            List<PolicyVO> results = jdbcTemplate.query("select * from platform_gateway_rate_limit  where enabled = true ", new
                    BeanPropertyRowMapper<>(PolicyVO.class));
            if (results != null && results.size() > 0) {
                for (PolicyVO result : results) {
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


    public static class PolicyVO {

        /**
         * 限制数量
         */
        private Long limit;
        /**
         * 时间间隔(秒)
         */
        private Long interval;
        /**
         * 服务ID
         */
        private String serviceId;

        /**
         * 启用禁用
         */
        private Boolean enabled;

        /**
         * 限流方式
         * url    访问路径
         * origin 域名IP
         * user 用户
         */
        private String type;

        /**
         * 限流规则
         * url=/api/user
         * origin=
         */
        private String rules;

        /**
         * 描述
         */
        private String description;

        public Long getLimit() {
            return limit;
        }

        public void setLimit(Long limit) {
            this.limit = limit;
        }

        public Long getInterval() {
            return interval;
        }

        public void setInterval(Long interval) {
            this.interval = interval;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRules() {
            return rules;
        }

        public void setRules(String rules) {
            this.rules = rules;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}