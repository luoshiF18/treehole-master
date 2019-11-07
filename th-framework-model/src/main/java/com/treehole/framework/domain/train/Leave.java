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
    private String leavePeopleType;
    @Column(name = "leave_people_id")
    private String leavePeopleId;
    @Column(name = "leave_people_name")
    private String leavePeopleName;
    @Column(name = "leave_time")
    private Date leaveTime;
    @Column(name = "leave_start_time")
    private Date leaveStartTime;
    @Column(name = "leave_end_time")
    private Date leaveEndTime;
    @Column(name = "leave_remove_time")
    private Date leaveRemoveTime;
    @Column(name = "leave_state")
    private String leaveState;
    @Column(name = "leave_operator_id")
    private String  leaveOperatorId;
    @Column(name = "leave_other")
    private String leaveOther;
}
