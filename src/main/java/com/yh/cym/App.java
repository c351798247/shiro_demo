package com.yh.cym;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-first.ini");

        SecurityManager manager = factory.getInstance();

        SecurityUtils.setSecurityManager(manager);

        Subject subject = SecurityUtils.getSubject();

        String username = "cym";

        AuthenticationToken token = new UsernamePasswordToken(username, "121");

        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            System.out.println("username " + username + "is not exists");
        } catch (IncorrectCredentialsException e) {
            System.out.println("false token");
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        if (subject.isAuthenticated()) {
            System.out.println(username+" Access");
        } else {
            System.out.println(username+"zhangsan not access");
        }

    }
}
