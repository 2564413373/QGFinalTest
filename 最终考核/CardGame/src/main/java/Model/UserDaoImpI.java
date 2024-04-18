package Model;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.json.JSONObject;
import pojo.User;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class UserDaoImpI implements UserDao {
    static DataSource datasource;
    static {
        try {
            Properties prop = new Properties();
            InputStream ips= UserDaoImpI.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(ips);
            //////// 获取Druid连接池对象
            datasource = DruidDataSourceFactory.createDataSource(prop);
            //////注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("数据库连接成功！！！");
        return datasource.getConnection();
    }

    public User search(String nickname,String username,String password) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //// 定义SQL语句
            String sql = "select * from tb_person where nickname = ? and username = ? and password = ?";
            //// 创建执行对象
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1,nickname);
            pstmt.setString(2,username);
            pstmt.setString(3,password);
            /////  根据Resultset返回内容来判断操作是否成功
            rs = pstmt.executeQuery();
            if (rs.next()) {

                ////// 封装对象
                User user = new User();
                user.setNickname(nickname);
                user.setUsername(username);
                user.setPassword(password);

                System.out.println("登录成功");
                return user;
            } else {
                System.out.println("登录失败");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public User create(String nickname, String username, String password, String name, String sex, String age, String phone, String idcard) {
        //////  实现创建用户到数据库
        PreparedStatement pstmt = null;
        try {
            //// 定义SQL语句
            String sql = "insert into tb_person(nickname,username,password,name,sex,age,phone,idcard) values(?,?,?,?,?,?,?,?)";
            //// 创建执行对象
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1,nickname);
            pstmt.setString(2,username);
            pstmt.setString(3,password);
            pstmt.setString(4,name);
            pstmt.setString(5,sex);
            pstmt.setString(6,age);
            pstmt.setString(7,phone);
            pstmt.setString(8,idcard);
            /////  根据影响行数判断操作是否成功
            int count = pstmt.executeUpdate();
            if (count > 0) {

                ///// 封装对象
                User user = new User();
                user.setNickname(nickname);
                user.setUsername(username);
                user.setPassword(password);
                user.setName(name);
                user.setSex(sex);
                user.setAge(age);
                user.setPhone(phone);
                user.setIdcard(idcard);

                System.out.println("注册成功！！！");
                return user;
            } else {
                System.out.println("注册失败！！！");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
