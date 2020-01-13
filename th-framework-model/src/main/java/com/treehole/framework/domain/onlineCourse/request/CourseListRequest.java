package com.treehole.framework.domain.onlineCourse.request;

import com.treehole.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CourseListRequest extends RequestData {
    //公司Id
    private String companyId;
}
