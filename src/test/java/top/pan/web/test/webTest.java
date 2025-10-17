package top.pan.web.test;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import top.pan.web.entity.Student;
import top.pan.web.mapper.Mapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class webTest {
    private static SqlSessionFactory sqlSessionFactory;

    @Test
    public void test1() throws UnirestException, IOException {
        try {
            // 发送GET请求并获取二进制响应
            HttpResponse<InputStream> response = Unirest.get("https://demo.komga.org/api/v1/books/05RKH8CC8B4RW/pages/35")
                    .header("Cookie", "KOMGA-SESSION=ZDFmNDg3ODQtYTEyZi00MGI1LWE0N2UtNWJhNWUzYTNmZGE0")
                    .asBinary();

            // 检查HTTP状态码
            int status = response.getStatus();
            if (status == 200) {
                // 获取响应体作为输入流
                InputStream inputStream = response.getBody();

                // 将输入流写入文件
                Files.copy(inputStream, Paths.get("test.png"), StandardCopyOption.REPLACE_EXISTING);

                System.out.println("图片下载成功: test.png");
            } else {
                System.out.println("请求失败，状态码: " + status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 在应用程序结束时关闭Unirest客户端
            try {
                Unirest.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

