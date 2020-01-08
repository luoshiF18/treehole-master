package com.treehole.member.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.member.Company;
import com.treehole.framework.domain.member.Vo.UserVo;
import com.treehole.framework.domain.member.resquest.CompanyListRequest;
import com.treehole.framework.domain.member.result.MemberCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.member.mapper.CompanyMapper;
import com.treehole.member.myUtil.MyNumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author shanhuijie
 * @Description:
 * @Date
 */
@Service
public class CompanyService {
    @Autowired
    private CompanyMapper companyMapper;
    @Autowired
    private UserService userService;

    /*
     * 根据rank,id,name查询所有付费会员等级信息
     * */
    //@Cacheable(value="MemberCompany")
    public QueryResponseResult findAll(Integer page,
                                        Integer size,
                                       CompanyListRequest companyListRequest) {

        //判断请求条件的合法性
        if (companyListRequest == null){
            companyListRequest = new CompanyListRequest();
        }

        Example example = new Example(Company.class);
        Example.Criteria criteria = example.createCriteria();
        //判断不为空字符串
        if(StringUtils.isNotEmpty(companyListRequest.getCompany_id())){
            criteria.andLike("id", "%" + companyListRequest.getCompany_id() + "%");
        }
        if(StringUtils.isNotEmpty(companyListRequest.getName())){
            criteria.andLike("name", "%" + companyListRequest.getName() + "%");
        }
        if(StringUtils.isNotEmpty(companyListRequest.getLinkname())){
            criteria.andLike("linkname", "%" + companyListRequest.getLinkname() + "%");
        }
        if(StringUtils.isNotEmpty(companyListRequest.getUser_id())){
            criteria.andLike("user_id", "%" + companyListRequest.getUser_id() + "%");
        }
        if(companyListRequest.getCheckstatus() != null){
            criteria.andEqualTo("checkstatus",companyListRequest.getCheckstatus());
        }
        example.orderBy("apply_time").desc();  //降序
        //List<Company> companies = companyMapper.selectByExample(example);
        Page pag = PageHelper.startPage(page,size);
        List<Company> companies = companyMapper.selectByExample(example);
        //  解析分页结果
        PageInfo<Company> pageInfo = new PageInfo<>(pag.getResult());
        QueryResult queryResult = new QueryResult();
        queryResult.setList(companies);
        queryResult.setTotal(pageInfo.getTotal());
        return new QueryResponseResult(CommonCode.SUCCESS,queryResult);
    }

    /*查*/
    public Company findCompanyByCompany(Company company){
        return companyMapper.selectOne(company);
    }

    /*查*/
    public Company findCompanyById(String  id){
        Company company = new Company();
        company.setId(id);
        return companyMapper.selectOne(company);
    }
    /*查*/
    public Company findCompanyByUserId(String  id){
        Company company = new Company();
        company.setUser_id(id);
        return companyMapper.selectOne(company);
    }
    /*增*/
    @Transactional
    //@CacheEvict(value="MemberCompany",allEntries=true)
    public void insertCompany(Company company) {
        if(company == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        company.setId(MyNumberUtils.getUUID());
        company.setStatus(0); //企业状态 0正常
        company.setCheckstatus(0); //状态未审核
        company.setApply_time(new Date());//申请时间

        int ins = companyMapper.insert(company);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }

        Company companyByUserId = this.findCompanyByUserId(company.getUser_id());
        UserVo userVo = new UserVo();
        userVo.setUser_id(company.getUser_id());
        userVo.setCompany_id(companyByUserId.getId());
        userService.updateUser(userVo);
    }

    /*更新*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void updateCompany(Company company) {
        if(company == null){
            //抛出异常，非法参数异常。指定异常信息的内容
            ExceptionCast.cast(MemberCode.DATA_IS_NULL);
        }
        Example example = new Example(Company.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",company.getId());
        int ins = companyMapper.updateByExampleSelective(company,example);
        if(ins != 1){
            ExceptionCast.cast(MemberCode.INSERT_FAIL);
        }
    }

    /*删除
    * 根据companyid*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void deleteCompanyById(String id) {
        //id不为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //Company存在
        Company company = new Company();
        company.setId(id);
        if(this.findCompanyByCompany(company) != null){
            int del = companyMapper.delete(company);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }

    /*删除
     * 根据userid*/
    @Transactional
    //@CacheEvict(value="MemberRole",allEntries=true)
    public void deleteCompanyByUserId(String id) {
        //id不为空
        if(StringUtils.isBlank(id)){
            ExceptionCast.cast(MemberCode.DATA_ERROR);
        }
        //Company存在
        Company company = new Company();
        company.setUser_id(id);
        if(this.findCompanyByCompany(company) != null){
            int del = companyMapper.delete(company);
            if(del != 1){
                ExceptionCast.cast(MemberCode.DELETE_FAIL);
            }
        }else{
            ExceptionCast.cast(MemberCode.GRADE_NOT_EXIST);
        }
    }


}
