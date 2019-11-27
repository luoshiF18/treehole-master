package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="phase")
public class Phase {

    @Id
    private  String phaseId;
    private  String phaseName; //期数名称
    private  double phaseTuition;//学费
    private  double  phasePreferentialAmount; //优惠金额

}
