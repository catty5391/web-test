package top.pan.web.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.java.Log;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import top.pan.web.entity.User;
import top.pan.web.mapper.Mapper;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //首先判断cookie是否为空，防止后面出现空指针异常
        if(req.getCookies() != null){
            String username = null;
            String password = null;
            for (Cookie cookie: req.getCookies()){
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if(cookie.getName().equals("password")) password = cookie.getValue();
            }
            try(SqlSession session = sqlSessionFactory.openSession(true)){
                Mapper mapper = session.getMapper(Mapper.class);
                User user = mapper.checkoutLogin(username, password);
                if (user != null){
                    resp.sendRedirect("time");
                    return;
                }else{
                    Cookie cookie1 = new Cookie("username", username);
                    cookie1.setMaxAge(0);
                    Cookie cookie2 = new Cookie("password", password);
                    cookie2.setMaxAge(0);
                    resp.addCookie(cookie1);
                    resp.addCookie(cookie2);
                }
            }
        }
        req.getRequestDispatcher("/").forward(req, resp);
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
                    //没有在cookie中发现jsessionID，则在sessionmanager中新建session对象，并让session1指向此对象
                    HttpSession session1 = req.getSession();
                    session1.setAttribute("user", user);

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


