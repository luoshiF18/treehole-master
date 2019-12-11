package com.treehole.framework.domain.train;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="leaves")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Leave implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "leave_id")
    private String leaveId;
    @Column(name = "leave_people_type")
    private Integer leavePeopleType;  //请假类型 1 学生 2 老师
    @Column(name = "leave_people_id")
    private String leavePeopleId;
    @Column(name = "leave_people_name")
    private String leavePeopleName;
    @Column(name = "leave_time")
    private Date leaveTime;  //请假时间
    @Column(name = "leave_start_time")
    private Date leaveStartTime; //开始时间
    @Column(name = "leave_end_time")
    private Date leaveEndTime;  //结束时间
    @Column(name = "leave_remove_time")
    private Date leaveRemoveTime; //消假时间
    @Column(name = "leave_state")
    private Integer leaveState;   // 1 已消假  2 请假中 3 审核中  4 审核失败
    @Column(name = "leave_operator_id")
    private String  leaveOperatorId;
    @Column(name = "leave_other")
    private String leaveOther;
}
