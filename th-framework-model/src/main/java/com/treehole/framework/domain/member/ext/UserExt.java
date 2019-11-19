package com.treehole.framework.domain.member.ext;

import com.treehole.framework.domain.member.ThMenu;
import com.treehole.framework.domain.member.User;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Qbl
 * Created by 8:56 on 2019/11/13.
 * Version: 1.0
 */
@Data
@ToString
public class UserExt extends User {

    private List<ThMenu> perimissions;

    private String companyId;

}
