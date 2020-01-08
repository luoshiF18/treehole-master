package com.treehole.api.member;

import com.treehole.framework.domain.member.Company;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.resquest.CompanyListRequest;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author shanhuijie
 * @Description: 角色api
 * @Date 2019.10.21 21:05
 */

@Api(value = "公司信息管理", description = "对公司信息进行增、删、改、查")
public interface CompanyControllerApi {
    @ApiOperation("查询所有公司信息")
    public QueryResponseResult findAllCompany(Integer page,
                                           Integer size,
                                              CompanyListRequest companyListRequest);
    @ApiOperation("根据id查询公司信息")
    public Company findById(String id);
    @ApiOperation("根据userid查询公司信息")
    public Company findByUserId( String user_id);
    @ApiOperation("插入一条公司信息")
    public ResponseResult insertCompany(Company company) ;

    @ApiOperation("根据id删除公司信息")
    public ResponseResult deleteCompanyById(String id);

    @ApiOperation("根据uesr_id删除公司信息")
    public ResponseResult deleteCompanyByUserId(String id);

    @ApiOperation("更改公司信息")
    public ResponseResult updateCompany(Company company);

}
