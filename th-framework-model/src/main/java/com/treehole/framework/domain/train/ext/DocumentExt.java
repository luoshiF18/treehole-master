package com.treehole.framework.domain.train.ext;


import com.treehole.framework.domain.train.Document;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class DocumentExt extends Document {

    private String describe;
    private String folderId;
    private String userId;
    private String teacherName;
    private String studentName;

}
