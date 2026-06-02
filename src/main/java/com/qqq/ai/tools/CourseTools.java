package com.qqq.ai.tools;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.qqq.ai.entity.po.Course;
import com.qqq.ai.entity.po.CourseReservation;
import com.qqq.ai.entity.po.School;
import com.qqq.ai.entity.query.CourseQuery;
import com.qqq.ai.service.ICourseReservationService;
import com.qqq.ai.service.ICourseService;
import com.qqq.ai.service.ISchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author qqq
 * @description
 * @createDate 2026/6/2 16:37
 */
@RequiredArgsConstructor
@Component
public class CourseTools {

    private final ICourseService courseService;
    private final ISchoolService schoolService;
    private final ICourseReservationService courseReservationService;

    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件") CourseQuery courseQuery) {
        if (courseQuery == null) {
            return List.of();
        }
        QueryChainWrapper<Course> query = courseService.query()
                .eq(courseQuery.getType() != null, "type", courseQuery.getType())
                .le(courseQuery.getEdu() != null, "edu", courseQuery.getEdu());
        if(courseQuery.getSorts() != null) {
            for (CourseQuery.Sort sort : courseQuery.getSorts()) {
                query.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return query.list();
    }

    @Tool(description = "查询所有校区")
    public List<School> queryAllSchools() {
        return schoolService.list();
    }

    @Tool(description = "生成课程预约单,并返回生成的预约单号")
    public Integer generateCourseReservation(
            @ToolParam(description = "预约课程") String courseName,
            @ToolParam(description = "学生姓名") String studentName,
            @ToolParam(description = "联系方式") String contactInfo,
            @ToolParam(description = "预约校区") String school,
            @ToolParam(description = "备注") String remark) {
        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(courseName)
                .setStudentName(studentName)
                .setContactInfo(contactInfo)
                .setSchool(school)
                .setRemark(remark);
        courseReservationService.save(courseReservation);
        return courseReservation.getId();
    }

}
