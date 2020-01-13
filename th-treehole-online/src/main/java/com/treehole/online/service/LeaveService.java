package com.treehole.online.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.domain.onlinetalk.Leave;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.online.mapper.LeaveMapper;
import com.treehole.online.myUtil.MyNumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author hewenze
 * @Description:
 * @Date
 */
@Service
@Cacheable(value = "LeaveService")
public class LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;



    /**
     * 查询所有留言
     *
     * @param
     * @return List<User>
     */

    public QueryResult findAllLeave(int page, int size , String name) {
        //分页
        Page pag =PageHelper.startPage(page,size);
        List<Leave> leaves = new ArrayList<>();
        if (!name .equals("") ){
            Leave leave2 = new Leave();
            leave2.setName(name);
            leaves= leaveMapper.select(leave2);
        }else {
            leaves = leaveMapper.selectAll();
        }

        if (CollectionUtils.isEmpty(leaves)) {
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        //        解析分页结果
        PageInfo<Leave> pageInfo = new PageInfo<>(pag.getResult());

        return new QueryResult(leaves, pageInfo.getTotal());

    }


    /**
     * 创建一条留言信息
     *
     * @param leave
     * @return int
     */
    @CacheEvict(value="LeaveService",allEntries=true)
    public void insertLeave(Leave leave)  {
        leave.setLeave_id(MyNumberUtils.getUUID());
        leave.setCreatetime(new Date());
        int ins = leaveMapper.insert(leave);
        if( ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

    }


}
