package com.treehole.member.controller;

import com.treehole.api.member.CompanyControllerApi;
import com.treehole.api.member.RoleControllerApi;
import com.treehole.framework.domain.member.Company;
import com.treehole.framework.domain.member.Role;
import com.treehole.framework.domain.member.User;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.CompanyListRequest;
import com.treehole.framework.domain.member.resquest.RoleListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.member.service.CompanyService;
import com.treehole.member.service.RoleService;
import com.treehole.member.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Shan HuiJie
 * @Description:
 * @Date 2019.12.23 19:35
 */
@RestController
@RequestMapping("member/company")
public class CompanyController implements CompanyControllerApi {

    @Autowired
    private CompanyService companyService;




    @Override
    @GetMapping("/find/all/{page}/{size}")
    @PreAuthorize("hasAuthority('member_company_find_all')")
    public QueryResponseResult findAllCompany(@PathVariable("page")Integer page,
                                              @PathVariable("size")Integer size,
                                              CompanyListRequest companyListRequest) {
        return companyService.findAll(page,size,companyListRequest);
    }

    @Override
    @GetMapping("/findById/{id}")
    //@PreAuthorize("hasAuthority('member_company_find_id')")
    public Company findById(@PathVariable("id") String id) {
        Company companyById = companyService.findCompanyById(id);
        return companyById;
    }
    @Override
    @GetMapping("/findByUserId/{user_id}")
    public Company findByUserId(@PathVariable("user_id") String user_id) {
        Company companyById = companyService.findCompanyByUserId(user_id);
       // System.out.println("+++++++" + companyById);
        return companyById;
    }


    @Override
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('member_company_insert')")
    public ResponseResult insertCompany(@RequestBody @Valid Company company) {
        //System.out.println("}}}}}");
        companyService.insertCompany(company);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/deleteById/{id}")
    @PreAuthorize("hasAuthority('member_company_delete_id')")
    public ResponseResult deleteCompanyById(@PathVariable("id") String id) {
        companyService.deleteCompanyById(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @DeleteMapping("/deleteByUserId/{user_id}")
    @PreAuthorize("hasAuthority('member_company_delete_userid')")
    public ResponseResult deleteCompanyByUserId(@PathVariable("user_id") String id) {

        companyService.deleteCompanyByUserId(id);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('member_company_update')")
    public ResponseResult updateCompany(@RequestBody  Company company) {
        companyService.updateCompany(company);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
