package top.pan.web.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import top.pan.web.entity.Student;
import top.pan.web.entity.User;

import java.util.List;

public interface Mapper {

    @Select("select * from study.user where username = #{1} and password = #{2}")
    User checkoutLogin(@Param("1") String username,@Param("2") String password);
}
