package com.humama.pojo;

import java.util.Date;

/**
 * @描述: .
 * @作者: ZHaoWeiNan .
 * @创建时间: 2016/9/10 .
 * @版本: 1.0 .
 */
public abstract class BasePojo {
    private Date created;
    private Date updated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
