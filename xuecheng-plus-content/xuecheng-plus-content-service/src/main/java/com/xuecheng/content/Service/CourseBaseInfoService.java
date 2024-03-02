package com.xuecheng.content.Service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CoursePublish;
import org.springframework.web.bind.annotation.RequestBody;

//课程信息管理接口
public interface CourseBaseInfoService {
    //课程分页查询
    public PageResult<CourseBase> queryCourseBaseList(Long companyId, PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto);

    public CourseBaseInfoDto createCourseBase(Long companyID, AddCourseDto addCourseDto);

    public CourseBaseInfoDto getCourseBaseInfo(long courseId);

    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto);
}
