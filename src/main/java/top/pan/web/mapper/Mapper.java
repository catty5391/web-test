package top.pan.web.mapper;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import top.pan.web.entity.Student;

import java.util.List;

public interface Mapper {

    @Results({
            @Result(id = true, column = "sid", property = "sid"),
            @Result(column = "name", property = "name"),
            @Result(column = "sex", property = "sex"),
            @Result(column = "grade", property = "grade")
    })
    @Select("select * from library2.student where sid = #{sid}")
    Student selectStudentBySid(int sid);
}
