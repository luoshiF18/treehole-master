package com.treehole.framework.domain.appointment;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName CltManage
 * @Description: 咨询师预约管理
 * @Author XDD
 * @Date 2019/11/27
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "clt_manage")
@GenericGenerator(name ="jpa-uuid",strategy = "uuid")
public class CltManage {
    // ID
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    // 咨询师ID
    private String cltId;

    // 预约日期
    private Date cltDate;

    // 预约开始时间
    private String cltStartTime;

    // 预约结束时间
    private String cltEndTime;
}
