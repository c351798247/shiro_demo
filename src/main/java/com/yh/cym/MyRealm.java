package com.yh.cym;

import com.yh.cym.bean.TbUser;
import com.yh.cym.bean.TbUserExample;
import com.yh.cym.mapper.TbUserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/7/24.
 */
@Component
public class MyRealm extends AuthorizingRealm{
    @Autowired
    TbUserMapper mapper;
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String)principalCollection.getPrimaryPrincipal();
        System.out.println(username);
        List<String> permissins = new ArrayList<>();
        permissins.add("user:create");
        permissins.add("user:update");

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions(permissins);
        authorizationInfo.addRole("role1");

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String  username = (String) authenticationToken.getPrincipal();
//		根据用户名查询数据库获取密码
//		username ="zhangsan";
//		调用业务方法获取数据库中的凭证
        String pwdByUsername ="123";
//        TbUserExample example = new TbUserExample();
//        example.createCriteria().andUnameEqualTo(username);
//        List<TbUser> tbUsers = mapper.selectByExample(example);
//        if (tbUsers.size() > 0) {
//            TbUser user = tbUsers.get(0);
//            pwdByUsername = user.getUpass();
//        }

//		  String pwd= "123";
//		将送来用户账号及根据账号查出的密码（凭证）封装成一个AuthenticationInfo对象，返回
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, pwdByUsername, getName());
        return authenticationInfo;

    }
}
