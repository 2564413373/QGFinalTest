package Controller;

import org.json.JSONObject;
import Model.*;
import pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/LoginRegister")
public class LoginRegisterServlet extends BaseServlet {

    private UserDaoImpI udi = new UserDaoImpI();

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //////  获取请求参数
        String nickname = request.getParameter("Nickname");
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");
        response.setContentType("application/json");
        ////// 调用查找函数
        User success = udi.search(nickname,username,password);
        if (success != null) {
            /*
            if ("1".equals(remember)) {
                //////// 如果勾选了复选框，就创建Cookie
                Cookie nickname1 = new Cookie("nickname", nickname);
                Cookie username1 = new Cookie("username", username);
                Cookie password1 = new Cookie("password", password);

                nickname1.setMaxAge(60*60*24*2);
                username1.setMaxAge(60*60*24*2);
                password1.setMaxAge(60*60*24*2);
                //////  发送到浏览器中
                response.addCookie(nickname1);
                response.addCookie(username1);
                response.addCookie(password1);
            }*/
            JSONObject json = new JSONObject();
            json.put("success",true);
            json.put("message", "登陆成功！！！");
            response.getWriter().write(json.toString());
        } else {
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("message", "无此账号，登陆失败！！！");
            response.getWriter().write(json.toString());
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //////  获取请求参数
        String nickname = request.getParameter("nickname");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String sex = request.getParameter("sex");
        String age = request.getParameter("age");
        String phone = request.getParameter("phone");
        String idcard = request.getParameter("idcard");
        response.setContentType("application/json");
        ////// 调用查找函数
        User success = udi.create(nickname,username,password,name,sex,age,phone,idcard);
        if (success != null) {
            JSONObject json = new JSONObject();
            json.put("success",true);
            json.put("message", "注册成功！！！");
            response.getWriter().write(json.toString());
        } else {
            JSONObject json = new JSONObject();
            json.put("success",false);
            json.put("message", "注册失败！！！");
            response.getWriter().write(json.toString());
        }
    }
}
