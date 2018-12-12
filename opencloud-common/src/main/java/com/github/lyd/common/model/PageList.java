package com.github.lyd.common.model;

import com.github.pagehelper.PageSerializable;

import java.util.List;

/**
 * @author admin
 */
public class PageList<T> extends PageSerializable {
    private static final long serialVersionUID = -4318162326895987772L;

    public PageList() {
        super();
    }

    public PageList(List<T> rows) {
        super(rows);
    }
}
