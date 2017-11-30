package domain;

import java.util.Date;

public class UserBehavior {
    private String user_id;
    private String act_obj;
    private String obj_type;
    private String bhv_type;
    private Double bhv_amt;
    private Double bhv_cnt;
    private Date bhv_datetime;
    private String content;
    private String trace_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAct_obj() {
        return act_obj;
    }

    public void setAct_obj(String act_obj) {
        this.act_obj = act_obj;
    }

    public String getObj_type() {
        return obj_type;
    }

    public void setObj_type(String obj_type) {
        this.obj_type = obj_type;
    }

    public String getBhv_type() {
        return bhv_type;
    }

    public void setBhv_type(String bhv_type) {
        this.bhv_type = bhv_type;
    }

    public Double getBhv_amt() {
        return bhv_amt;
    }

    public void setBhv_amt(Double bhv_amt) {
        this.bhv_amt = bhv_amt;
    }

    public Double getBhv_cnt() {
        return bhv_cnt;
    }

    public void setBhv_cnt(Double bhv_cnt) {
        this.bhv_cnt = bhv_cnt;
    }

    public Date getBhv_datetime() {
        return bhv_datetime;
    }

    public void setBhv_datetime(Date bhv_datetime) {
        this.bhv_datetime = bhv_datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }
}
