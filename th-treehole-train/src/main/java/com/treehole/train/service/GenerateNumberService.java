package com.treehole.train.service;

import com.treehole.framework.domain.train.Course;
import com.treehole.framework.domain.train.response.CostCode;
import com.treehole.framework.domain.train.response.PhaseCode;
import com.treehole.framework.domain.train.response.TrainCode;
import com.treehole.framework.exception.ExceptionCast;
import com.treehole.train.dao.CourseTypeRepository;
import com.treehole.train.dao.GenerateNumberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GenerateNumberService {

    @Autowired
    GenerateNumberMapper generateNumberMapper;

    /**
     * 自动生成Id（学生 老师）
     * @param typeId 1代表学生  2代表老师  3代表课程类型 4代表课程 5代表班级
     * @return
     */
    public String GenerateNumber(String typeId){
        Date date = new Date();

        String year=String.format("%tY", date);
        String num = year.substring(1, 4);
        String mon=String .format("%tm", date);
        String day=String .format("%td", date);
        //id的前半部分
        String newFrontNum = typeId+num+mon+day;
        int newFrontNum2 = Integer.parseInt(newFrontNum);


        //数据库中查出来值
        String id = this.findMaxId(typeId);

        String oldFrontNum = null;
        int oldFrontNum2 = 0;
        if(id != null){
            oldFrontNum = id.substring(0, 8);
             oldFrontNum2 = Integer.parseInt(oldFrontNum);
        }

        if(id != null) {
            if (oldFrontNum2 < newFrontNum2) {
                String newId =  newFrontNum2 + "0000";
                return newId;
            } else if (oldFrontNum2 == newFrontNum2) {
                String behindFront3 = id.substring(8);
                if (behindFront3.equals("9999")) {
                    ExceptionCast.cast(TrainCode.TODAY_NUMBER_FULL);
                } else {
                    long newId = Long.parseLong(id);
                    newId++;
                    return newId+"";
                }
            }
        }else if(id == null){
            String newId =  newFrontNum2 + "0000";
            return newId;
        }
        return null;
    }

    /**
     * 查询最大的Id
     * @param typeId 1代表学生  2代表老师  3代表课程类型  4代表课程  5代表班级
     * @return
     */
    private String findMaxId(String typeId){
        if(typeId.equals("1")){
            String studentId = generateNumberMapper.findStudentId();
            return studentId;
        }else if(typeId.equals("2")){
            String teacherId = generateNumberMapper.findTeacherId();
            return teacherId;
        }else if(typeId.equals("3")){
            String courseTypeId = generateNumberMapper.findCourseTypeId();
            return courseTypeId;
        }else if(typeId.equals("4")){
            String courseId = generateNumberMapper.findCourseId();
            return courseId;
        }else if(typeId.equals("5")){
            String classId = generateNumberMapper.findClassId();
            return classId;
        }
        return null;
    }

    /**
     * 生成期数Id
     * @return
     */
    public String GeneratePhaseNumber(){
        Date date = new Date();
        String year=String.format("%tY", date);
        int yearNum = Integer.parseInt(year);
        //得到期数最大Id
        String phaseMaxId = this.findPhaseMaxId();
        String phaseFront = null;
        int phaseFrontNum = 0;
        String phasebehind = null;
        int phasebehindNum = 0;
        if( phaseMaxId != null ){
             phaseFront = phaseMaxId.substring(0, 4);
             phaseFrontNum = Integer.parseInt(phaseFront);
             phasebehind = phaseMaxId.substring(4);
        }

        String pId = null;
        if(phaseMaxId != null ) {
            if (yearNum > phaseFrontNum) {
                pId = year + "001";
            } else if (yearNum == phaseFrontNum) {
                if(phasebehind.equals("999")){
                    ExceptionCast.cast(PhaseCode.PHASEFULL);
                }else {
                    int phaseMaxIdNum = Integer.parseInt(phaseMaxId);
                    phaseMaxIdNum++;

                    pId = year + (phaseMaxIdNum+"").substring(4);
                }
            }
        }else if(phaseMaxId == null){
            pId = year + "001";
        }

        return pId;
    }

    /**
     * 找到期数最大Id
     * @return
     */
    private String findPhaseMaxId(){
        String phaseId = generateNumberMapper.findPhaseId();
        return phaseId;
    }

}
