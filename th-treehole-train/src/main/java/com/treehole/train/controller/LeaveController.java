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
    @PostMapping("/find/{page}/{size}")
    public QueryResponseResult<Leave> LeaveStatistics(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody Leave leave) {
        return leaveService.LeaveStatistics(page,size,leave);
    }

    @Override
    @PostMapping("/findAll/{page}/{size}")
    public QueryResponseResult<Leave> LeaveAllStatistics(@PathVariable("page") int page,@PathVariable("size") int size,@RequestBody Leave leave) {
        return leaveService.LeaveAllStatistics(page,size,leave);
    }

    @Override
    @GetMapping("/findInfo/{id}")
    public Leave findInfo(@PathVariable("id") String id) {
        return leaveService.findInfo(id);
    }


}
