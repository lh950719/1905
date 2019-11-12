/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: MyRealm
 * Author:   zyl
 * Date:     2018/12/26 13:56
 * Description: test
 * History:
 */
package com.jk.shiro;


import com.jk.model.Users;
import com.jk.service.inter.UserServiceInter;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈test〉
 *
 * @author zyl
 * @create 2018/12/26
 * @since 1.0.0
 */
public class MyRealm extends AuthorizingRealm {

/*    @Resource
    private UserService userServcice;*/
    @Autowired
    private UserServiceInter userServiceInter;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取主身份信息
       // User userCode = (User)principals.getPrimaryPrincipal();

        //根据身份信息获取权限信息
        //模拟从数据库获取到数据
        List<String> permissions = new ArrayList<String>();
        permissions.add("user:create"); //用户的创建权限
        permissions.add("user:update"); //商品的添加权限
        permissions.add("user:deletes"); //商品的添加权限
        //permissions.add("user:toAdd");
        //将查询到授权信息填充到对象中
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(permissions);

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.从token取出用户身份信息
        String username = (String)token.getPrincipal();
        //查询数据库 判断当前user是否为空
      /*  User user = userServcice.queryUserByName(username);*/
        Users user = userServiceInter.queryUserName(username);
        //如果查询不到则返回null  user==nullc
            if(!username.equals(user.getUsername())){//这里模拟查询不到
            throw new UnknownAccountException();
        }
        //2.根据用户userCode查询数据库

        //模拟从数据库查询到的密码
        String password = user.getUserpwd();

        System.out.println(user.getUsername()+"---"+user.getUserpwd());
        //查询不到返回null

        //查询到返回认证信息
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password,this.getName());
        SecurityUtils.getSubject().getSession().setAttribute("login", username);
        return authenticationInfo;
    }

}
