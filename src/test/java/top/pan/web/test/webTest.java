package top.pan.web.test;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import top.pan.web.entity.Student;
import top.pan.web.mapper.Mapper;

import java.io.IOException;

public class webTest {
    private static SqlSessionFactory sqlSessionFactory;

    @Test
    public void test1(){
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try(SqlSession session = sqlSessionFactory.openSession(true)){
            Mapper mapper = session.getMapper(Mapper.class);
            Student student = mapper.selectStudentBySid(6);
            System.out.println(student);
        }

    }
}
