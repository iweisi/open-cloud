package com.github.lyd.common.model;

import com.github.lyd.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author liuyau
 * @date 2018/07/10
 */
@ApiModel(value = "分页参数")
public class PageParams implements Serializable {
    private static final long serialVersionUID = -1710273706052960025L;
    private static final int MIN_PAGE = 0;
    private static final int MAX_LIMIT = 999;
    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_LIMIT = 10;
    @ApiModelProperty(name = "page", value = "当前页码", example = "1", required = false)
    private int page = DEFAULT_PAGE;
    @ApiModelProperty(name = "limit", value = "显示条数:最大999", example = "10", required = false)
    private int limit = DEFAULT_LIMIT;
    @ApiModelProperty(name = "sort", value = "排序字段")
    private String sort;
    @ApiModelProperty(name = "order", value = "排序方向:asc-正序 desc-逆序",example = "asc")
    private String order;
    /**
     * 排序
     */
    private String orderBy;

    public PageParams() {
    }

    public PageParams(int page, int limit) {
        this(page, limit, "", "");
    }

    public PageParams(int page, int limit, String sort, String order) {
        this.page = page;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
    }

    public int getPage() {
        if (page <= MIN_PAGE) {
            page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        if (limit > MAX_LIMIT) {
            limit = MAX_LIMIT;
        }
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderBy() {
        if (StringUtils.isBlank(order)) {
            order = "asc";
        }
        if (StringUtils.isNotBlank(sort)) {
            this.setOrderBy(String.format("%s %s", StringUtils.camelToUnderline(sort), order));
        }
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
