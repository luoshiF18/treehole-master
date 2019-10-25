package com.treehole.framework.domain.member;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shanhuijie
 * @Description: 签到信息
 * @Date
 */
@Data
@Table(name = "member_checkin")
public class Checkin implements Serializable {
    private String checkin_id; //签到id
    private String user_id; //用户id
    private Date checkin_time; //签到时间

    public Checkin() {
    }

    public String getCheckin_id() {
        return checkin_id;
    }

    public void setCheckin_id(String checkin_id) {
        this.checkin_id = checkin_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(Date checkin_time) {
        this.checkin_time = checkin_time;
    }
}
