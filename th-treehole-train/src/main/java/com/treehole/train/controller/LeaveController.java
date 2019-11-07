package com.treehole.train.controller;
import com.treehole.api.train.LeaveControllerApi;
import com.treehole.framework.domain.train.Leave;
import com.treehole.framework.model.response.QueryResponseResult;
import com.treehole.framework.model.response.ResponseResult;
import com.treehole.train.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/train/leave")
public class LeaveController implements LeaveControllerApi {

   @Autowired
    LeaveService leaveService;

    @Override
    @PostMapping("/add")
    public ResponseResult addLeave(@RequestBody Leave leave) {
        return leaveService.addLeave(leave);
    }

    @Override
    @PutMapping("/remove/{leaveId}")
    public ResponseResult removeLeave(@PathVariable("leaveId") String leaveId) {
        return leaveService.removeLeave(leaveId);
    }

    @Override
    @GetMapping("/find/{type}/{page}/{peopleId}")
    public QueryResponseResult<Leave> LeaveStatistics(@PathVariable("page") int page,@PathVariable("type") String type,@PathVariable("peopleId") String id) {
        return leaveService.LeaveStatistics(page,type,id);
    }


}
