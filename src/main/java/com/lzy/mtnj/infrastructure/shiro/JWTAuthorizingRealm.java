package com.lzy.mtnj.infrastructure.shiro;

import com.lzy.mtnj.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.util.StringUtil;

import java.time.LocalDateTime;

public class JWTAuthorizingRealm extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    /**
     * 增加Shiro对JWTToken的支持
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }
    /**
     * 初始化授权信息
     * @param principal
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        String name = JWTUtil.getUsername(principal.toString());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //TODO 从数据库初始化角色和权限信息
        authorizationInfo.addRole("admin");//初始化角色
        authorizationInfo.addStringPermission("001");//初始化权限
        return authorizationInfo;
    }
    /**
     * token校验 并初始化请求中的用户上下文信息
     * @param authToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        String token = (String) authToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null) {

            throw new UnknownAccountException("token无效");
        }
        String secret =userService.getSecret(username);
        if(StringUtil.isEmpty(secret)){

            throw new ExpiredCredentialsException("登陆超时,请重新登陆");
        }
		if (!JWTUtil.verify(token, username, secret)) {

			throw new IncorrectCredentialsException("Token校验失败，请重新登陆"+token+"@"+username+"@"+secret);
		}
        LocalDateTime lastRequestTime = userService.getLastRequestDate(username);
		if(lastRequestTime==null || lastRequestTime.plusMinutes(30).isBefore(LocalDateTime.now())){
            throw new ExpiredCredentialsException("登陆超时,请重新登陆");
        }else{
		    userService.setLastRequestDate(username);
        }
        Object currentUser = userService.getCurrentUser(username);
        if(currentUser==null){
            throw new ExpiredCredentialsException("登陆超时，请重新登陆");
        }
        return new SimpleAuthenticationInfo(currentUser, token, "jwt_realm");
    }
}
