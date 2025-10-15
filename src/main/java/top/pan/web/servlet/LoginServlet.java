package top.pan.web.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import top.pan.web.entity.User;
import top.pan.web.mapper.Mapper;

import java.io.IOException;
import java.util.Map;

@Log
@WebServlet(value = "/login", loadOnStartup = 1)
public class LoginServlet extends HttpServlet {
    private static SqlSessionFactory sqlSessionFactory;
    @Override
    public void init() throws ServletException {
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Mapper mapper;



        resp.setContentType("text/html");
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (parameterMap.containsKey("username") && parameterMap.containsKey("password")){
            try(SqlSession session = sqlSessionFactory.openSession(true)){
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                mapper = session.getMapper(Mapper.class);
                User user = mapper.checkoutLogin(username, password);
                if(user != null){
                    resp.getWriter().write("登录成功");
                }else{
                    resp.getWriter().write("密码或账号错误，请重试");
                }
            }
        }else{
            resp.getWriter().write("没有返回密码或账号");
        }

    }
}


