package com.glyxybxhtxt.dataObject;

import java.util.Date;

public class Qdb {
    private Integer id;

    private Date qdsj;

    private String shyid;

    private String xq;
    private Integer state;
    
    public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getQdsj() {
        return qdsj;
    }

    public void setQdsj(Date qdsj) {
        this.qdsj = qdsj;
    }

    public String getShyid() {
        return shyid;
    }

    public void setShyid(String shyid) {
        this.shyid = shyid == null ? null : shyid.trim();
    }

    public String getXq() {
        return xq;
    }

    public void setXq(String xq) {
        this.xq = xq == null ? null : xq.trim();
    }
}