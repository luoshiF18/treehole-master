package com.treehole.framework.domain.archives.response;

import com.treehole.framework.model.response.ResponseResult;
import com.treehole.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 任志强
 * @version 1.0
 * @date 2019/11/4 14:41
 * @description:
 */
@Data
@NoArgsConstructor
public class ArchivesCountResult extends ResponseResult {
    private Boolean aCount;

    public ArchivesCountResult(ResultCode resultCode, Boolean aCount) {
        super(resultCode);
        this.aCount = aCount;
    }
}
