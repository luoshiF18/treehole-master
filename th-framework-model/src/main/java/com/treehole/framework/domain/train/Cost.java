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
@Table(name="cost")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Cost implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(name = "cost_id")
    private String costId;
    @Column(name = "cost_student_id")
    private String costStudentId;
    @Column(name = "cost_student_name")
    private String costStudentName;
    @Column(name = "cost_amount_payable")
    private double costAmountPayable;
    @Column(name = "cost_preferential_amount")
    private double costPreferentialAmount;
    @Column(name = "cost_amount_received")
    private double costAmountReceived;
    @Column(name = "cost_arrears")
    private double costArrears;
    @Column(name = "cost_time")
    private Date costTime;
    @Column(name = "cost_operator_id")
    private String costOperatorId;
    @Column(name = "cost_other")
    private  String costOther;
}
