package Model;

import pojo.User;

public interface UserDao {
    ///// 注册
    public User create(String nickname, String username, String password, String name, String sex, String age, String phone, String idcard);

    ///// 登录
    public User search(String nickname,String username,String password);
}
