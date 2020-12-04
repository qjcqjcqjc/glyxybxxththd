package com.glyxybxhtxt.dataObject;


public class Ewm {
    private Integer id;

    private Integer qid;

    private String xxdd;
    private Bxqy qy;
    public Bxqy getQy() {
		return qy;
	}

	public void setQy(Bxqy qy) {
		this.qy = qy;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public String getXxdd() {
        return xxdd;
    }

    public void setXxdd(String xxdd) {
        this.xxdd = xxdd == null ? null : xxdd.trim();
    }
}