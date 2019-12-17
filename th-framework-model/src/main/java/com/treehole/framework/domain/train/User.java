package com.treehole.framework.domain.train;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@Entity
@NoArgsConstructor
@Table(name="user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User implements Serializable {
    private static final long serialVersionUID = -916357110051689587L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String  userId;
    private String  userName;
    private String  userPassword;
}
