package com.itheima.service.impl;

import com.itheima.mapper.UserMapper;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserServiceImpl implements UserService {

    SqlSessionFactory factory= SqlSessionFactoryUtils.getSqlSessionFactory();
    @Override
    public User login(User user) {
        SqlSession sqlSession=factory.openSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        String username=user.getUsername();
        String password=user.getPassword();
        User loginUser=mapper.Select(username,password);
        sqlSession.close();
        return loginUser;
    }

    @Override
    public boolean register(User user) {
        SqlSession sqlSession= factory.openSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        String username=user.getUsername();
        User u=mapper.selectByUsername(username);
        if(u==null) {
            //用户名不存在
            mapper.add(user);
            sqlSession.commit();

        }

        sqlSession.close();
        return u==null;
    }
}
