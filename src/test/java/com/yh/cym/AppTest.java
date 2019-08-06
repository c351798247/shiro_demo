package com.yh.cym;

import static org.junit.Assert.assertTrue;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import java.util.Arrays;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testPermission() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-permission.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();

        String username = "cym";
        String password = "111";

        AuthenticationToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        System.out.println(subject.hasRole("role1"));
        System.out.println(Arrays.toString(subject.hasRoles(Arrays.asList("role1", "role2"))));

        System.out.println(subject.isPermitted("user:create"));
        System.out.println(subject.isPermittedAll("user:create", "user:query"));
    }

    @Test
    public void testRealm() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager manager = factory.getInstance();
        SecurityUtils.setSecurityManager(manager);
        Subject subject = SecurityUtils.getSubject();
        String username = "cym";
        String password = "111";

        AuthenticationToken token = new UsernamePasswordToken(username, password);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        if (subject.isPermitted("user:create1")) {
            System.out.println(username + "含有user:create1权限");
        } else {
            System.out.println(username + "不含有user:create1权限");
        }
        if (subject.hasRole("role1")) {
            System.out.println(username+"含有role1角色的权限");
        }
    }


}
