package com.treehole.train.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.treehole.framework.domain.train.Phase;
import com.treehole.framework.model.response.CommonCode;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.QueryResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.dao.PhaseMapper;
import com.treehole.train.dao.PhaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhaseService {

    @Autowired
    PhaseRepository phaseRepository;

    @Autowired
    PhaseMapper phaseMapper;

    @Autowired
    GenerateNumberService generateNumberService;

    //添加
    public ResponseResult addPhase(Phase phase){
        //生成期数Id
        String pId = generateNumberService.GeneratePhaseNumber();
        phase.setPhaseId(pId);
        //保存
        Phase save = phaseRepository.save(phase);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //修改
    public ResponseResult updatePhase( String phaseId , Phase phase){
        phase.setPhaseId(phaseId);
        Phase save = phaseRepository.save(phase);
        if (save != null) {
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }

    //查询
   public QueryResponseResult<Phase> findPhase(int page , int size , Phase phase){
       if(page<=0){
           page=1;
       }
       Page<Phase> PhasePage = PageHelper.startPage(page, size);
       List<Phase> list = phaseMapper.findPhase(phase);
       PageInfo<Phase> info = new PageInfo<>(PhasePage.getResult());
       long total = info.getTotal();
       QueryResult queryResult = new QueryResult();
       queryResult.setList(list);
       queryResult.setTotal(total);
       if(list!=null){
           return new QueryResponseResult<Phase>(CommonCode.SUCCESS,queryResult);
       }else {
           return new QueryResponseResult<Phase>(CommonCode.FAIL,null);
       }
   }

   //自动生成名称
    public String generation_phaseName(){
        //从数据库中查询书最大Id
        String phaseId = phaseMapper.findMaxPhaseId();
        if(phaseId == null){
            phaseId = generateNumberService.GeneratePhaseNumber();
            String front = phaseId.substring(0, 4);
            String behind = phaseId.substring(4);
            int behindNum = Integer.parseInt(behind);
                String phaseName = front+"年"+"第"+behindNum+"期";
                return phaseName;
        }else {
            String front = phaseId.substring(0, 4);
            String behind = phaseId.substring(4);
            int behindNum = Integer.parseInt(behind);
            if(behindNum != 999){
                behindNum++;
                String phaseName = front+"年"+"第"+behindNum+"期";
                return phaseName;
            }else {
                String phaseName = "今年期数已满";
                return phaseName;
            }
        }




    }
}
