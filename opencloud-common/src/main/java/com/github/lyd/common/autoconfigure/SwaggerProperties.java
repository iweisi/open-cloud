package com.github.lyd.common.autoconfigure;

import com.google.common.collect.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 自定义swagger配置
 * @author LYD
 * @date 2018/7/29
 */
@ConfigurationProperties(prefix = "opencloud.swagger2")
public class SwaggerProperties {
    /**
     * 是否启用swagger
     */
    private boolean enabled;
    /**
     * 文档标题
     */
    private String title;
    /**
     * 文档描述
     */
    private String description;

    private List<String> ignores = Lists.newArrayList();
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getIgnores() {
        return ignores;
    }

    public void setIgnores(List<String> ignores) {
        this.ignores = ignores;
    }

    @Override
    public String toString() {
        return "SwaggerProperties{" +
                "enabled=" + enabled +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ignores=" + ignores +
                '}';
    }
}
