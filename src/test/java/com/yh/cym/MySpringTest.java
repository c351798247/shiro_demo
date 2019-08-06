package com.yh.cym;

import com.yh.cym.bean.TbUser;
import com.yh.cym.bean.TbUserExample;
import com.yh.cym.mapper.TbUserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/7/24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")

public class MySpringTest {
    @Autowired
    TbUserMapper mapper;

    @Test
    public void test1() {
        TbUser user =new TbUser();
        user.setUname("zhangsan");
        user.setUpass("123");
        user.setUserId((short) mapper.selectNextUserId());
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUnameEqualTo(user.getUname());
        int size = mapper.selectByExample(example).size();
        if (size > 0) {
            throw new RuntimeException("已存在该用户名");
        }
        mapper.insertSelective(user);

    }


    @Test
    public void testNextId() {
        System.out.println(mapper.selectNextUserId());
    }


    @Test
    public void testReaml() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        String username = "cym";
        String password = "123";

        AuthenticationToken token = new UsernamePasswordToken(username,password);
        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        if(subject.isAuthenticated()){
            System.out.println(username+"账户认证通过");
        }else{
            System.out.println(username+"账户认证未通过");
        }
    }

    @Test
    public void testSimpleHash() {

    }
}
