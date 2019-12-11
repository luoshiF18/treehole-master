package com.treehole.framework.domain.train.response;

import com.treehole.framework.model.response.ResultCode;
import lombok.ToString;

@ToString
public enum TrainCode implements ResultCode {

    STUDENT_NUMBER_NOTNULL(false,11001,"学生人数不为0，不能删除！"),
    COURSE_NUMBER_NOTNULL(false,11002,"课程数量不为0，不能删除！"),
    TEACHER_HAVING_COURSE(false,11003,"该老师还有课，不能删除！"),
    TEACHER_HAVING_CLASS(false,11004,"该老师还是班级的班主任，不能删除！"),
    TODAY_NUMBER_FULL(false,11005,"今天人数已经报满，请明天再来！"),
    TUITION_IS_NOT_PAID(false,11006,"该学生还有学费没有交完（欠费），不能删除！"),
    NO_TUITION_FEES(false,11007,"该学生还没有进行交费，不能删除！"),
    CLASS_IS_FULL(false,11008,"该班级人数已经满！"),
    COURSE_SELECTED(false,11009,"该课程已被选中不能删除！"),
    CLASS_IS_NULL(false,11010,"该班级人数为空，目前没有安排课程！"),;

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private TrainCode(String message){
        this.message = message;
    }

    private TrainCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }



    @Override
    public boolean success() {
        return success;
    }
    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

}
