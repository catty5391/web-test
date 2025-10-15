package top.pan.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;

import java.io.*;

@MultipartConfig
@WebServlet("/file")
public class FileServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/svg");
        OutputStream outputStream = resp.getOutputStream();
        try (InputStream inputStream = Resources.getResourceAsStream("首页_home.svg")) {
            byte[] bytes = new byte[1024];
            int i;
            while((i = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0,  i);
            }
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try(FileOutputStream outputStream = new FileOutputStream("C:\\Users\\admin\\IdeaProjects\\web-test\\test.jpg")){
            Part part = req.getPart("test-file");
            InputStream inputStream = part.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            resp.setContentType("text/html;charset=UTF-8");
            resp.getWriter().write("上传成功");
        }
    }
}
