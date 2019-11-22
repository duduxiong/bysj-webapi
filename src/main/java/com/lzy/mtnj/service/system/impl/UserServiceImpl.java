package com.lzy.mtnj.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lzy.mtnj.dto.UserAddDto;
import com.lzy.mtnj.infrastructure.BaseService;
import com.lzy.mtnj.infrastructure.exception.BusinessException;
import com.lzy.mtnj.infrastructure.shiro.JWTUtil;
import com.lzy.mtnj.infrastructure.shiro.UserInfo;
import com.lzy.mtnj.infrastructure.util.DateUtil;
import com.lzy.mtnj.infrastructure.util.MD5Util;
import com.lzy.mtnj.mapper.system.UserMapper;
import com.lzy.mtnj.mapper.system.UserRoleMapper;
import com.lzy.mtnj.model.UserRole;
import com.lzy.mtnj.model.user.User;
import com.lzy.mtnj.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseService implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    public String getSecret(String userName) {
        Object secret = getLoginCache().get(userName + "_secret");
        return secret == null ? "" : secret.toString();
    }

    @Override
    public User getCurrentUser(String userAcount) {
        Object user = getLoginCache().get(userAcount);
        return user == null ? null : (User) user;
    }

    /***********************************************读操作-start*********************************/
    @Override
    public UserInfo login(String acount, String password) {
        Example example = new Example(User.class);
        example.and().andEqualTo("userAccount", acount);
        example.and().andEqualTo("password", MD5Util.getMD5Code(password));
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            throw new BusinessException(001,"用户名密码错误");
        }
        User tmpUser = new User();
        tmpUser.setId(user.getId());
        tmpUser.setLastLoginDate(LocalDateTime.now());
        tmpUser.setLastLoginArea("北京-昌平");
        userMapper.updateByPrimaryKeySelective(tmpUser);

        String secret = java.util.UUID.randomUUID().toString();
        getLoginCache().put(user.getUserAccount() + "_secret", secret);
        getLoginCache().put(user.getUserAccount()+"_lastRequest",LocalDateTime.now());
        getLoginCache().put(user.getUserAccount(), user);
        String sign = JWTUtil.sign(user.getUserAccount(), secret);
        UserInfo token = new UserInfo();
        token.setToken(sign);
        token.setUserName(user.getUserName());
        token.setAvatar(user.getAvatar());
        token.setCellPhone(user.getCellPhone());
        token.setEmail(user.getEmail());
        token.setSex(user.getSex());
        token.setStatus(user.getStatus());
        token.setTel(user.getTel());
        token.setSuperAdmin(user.getSuperAdmin());

        if(user.getLastLoginDate()!=null) {
            token.setLastLoginDate(DateUtil.dateFormatToString(user.getLastLoginDate()));
        }
        token.setLastLoginArea(user.getLastLoginArea());
        return token;
    }

    @Override
    public User findById(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public Boolean checkUserAccountExist(String userAccount) {
        Example example = new Example(User.class);
        example.and().andEqualTo("userAccount",userAccount);
        User user = userMapper.selectOneByExample(example);
        if(user==null){
            return true;
        }else{
            return  false;
        }
    }

    @Override
    public Page<User> findByPage(User user) {
        Page<User> items = null;
        if (user.getPage() != null && user.getLimit() != null) {
            items = PageHelper.startPage(user.getPage(), user.getLimit());
        }
        //userMapper.selectAll();
        Example example = new Example(User.class);
        example.and().andEqualTo("superAdmin",0);
        if(getCurrentUser().getSuperAdmin().intValue()!=1){
            example.and().andEqualTo("parentId",getCurrentUser().getId());
        }else{
            if(user.getParentId()!=null && user.getParentId().trim()!=""){
                example.and().andEqualTo("parentId",user.getParentId());
            }
        }
        if(StringUtil.isNotEmpty(user.getUserAccount())){
            example.and().orLike("userAccount", "%"+user.getUserAccount().trim()+"%");
        }
        if(StringUtil.isNotEmpty(user.getUserName())){
            example.or().orLike("userName","%"+user.getUserName().trim()+"%");
        }
//        if(StringUtil.isNotEmpty(user.getRoleId())){
//            example.and().andEqualTo("roleId",user.getRoleId());
//        }
        userMapper.selectByExample(example);
        return items;
    }

    @Override
    public List<User> findAllProjectManager() {
        Example example = new Example(User.class);
        example.and().andEqualTo("roleId","1");
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> findChildrenUsersByParentId(String parentId) {
        Example example = new Example(User.class);
        if(getCurrentUser().getSuperAdmin().intValue()!=1) {
            if(StringUtil.isEmpty(parentId)){
                throw new BusinessException(0,"参数错误");
            }
            example.and().andEqualTo("parentId", parentId);
        }
        example.and().andEqualTo("superAdmin",0);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<User> findAllUsers() {
        Example example = new Example(User.class);
        example.and().andEqualTo("superAdmin",0);
        return  userMapper.selectByExample(example);
    }

    @Override
    public List<User> findUsersByRole(String roleId) {
        Example example = new Example(User.class);
        example.and().andEqualTo("roleId",roleId);
        return  userMapper.selectByExample(example);
    }

    @Override
    public boolean resetPassword(UserAddDto userinfo) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",userinfo.getId());
        User user=new User();
        user.setPassword(MD5Util.getMD5Code(userinfo.getPassword()));
        userMapper.updateByExampleSelective(user,example);
        return true;
    }

    @Override
    public LocalDateTime getLastRequestDate(String userAccount) {
        Object  lastRequestTime = getLoginCache().get(userAccount+"_lastRequest");
        if(lastRequestTime!=null){
            return  (LocalDateTime)lastRequestTime;
        }
        return  null;
    }

    @Override
    public void setLastRequestDate(String userAccount) {
        getLoginCache().put(userAccount+"_lastRequest",LocalDateTime.now());
    }
    /***********************************************读操作-start*********************************/


    /***********************************************写操作-start*********************************/
    @Override
    public int add(User user) {
        user.setSuperAdmin(0);
        user.setAddUser(getCurrentUser().getId());
        user.setAddDate(LocalDateTime.now());
        user.setPassword(MD5Util.getMD5Code(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int updateUserInfo(User user) {
        User entity = userMapper.selectByPrimaryKey(user.getId());
        if(entity==null){
            throw new BusinessException(0,"该用户不存在");
        }

        User upContent = new User();
        upContent.setId(entity.getId());
        upContent.setUserAccount(user.getUserAccount());
        upContent.setUserName(user.getUserName());
        upContent.setSex(user.getSex());
        upContent.setBirthday(user.getBirthday());
        upContent.setTel(user.getTel());
        upContent.setCellPhone(user.getCellPhone());
        upContent.setEmail(user.getEmail());
        upContent.setStatus(user.getStatus());
        return userMapper.updateByPrimaryKeySelective(upContent);
    }

    @Override
    public int delete(String id) {
        int result = userMapper.deleteByPrimaryKey(id);
        return result;
    }

    @Transactional
    @Override
    public int authorRoles(String userId, List<String> roleList) {
        if(roleList.size()<1){
            Example delCondtion = new Example(UserRole.class);
            delCondtion.createCriteria().andEqualTo("userId",userId);
            userRoleMapper.deleteByExample(delCondtion);
        }else{
            Example delCondtion = new Example(UserRole.class);
            delCondtion.createCriteria().
                    andEqualTo("userId",userId).
                    andNotIn("roleId",roleList);
            userRoleMapper.deleteByExample(delCondtion);



            for (String roleId:roleList){
                Example selectCondition = new Example(UserRole.class);
                selectCondition.createCriteria()
                        .andEqualTo("userId",userId)
                        .andEqualTo("roleId",roleId);
                if(userRoleMapper.selectOneByExample(selectCondition)==null){
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setAddDate(LocalDateTime.now());
                    userRole.setAddUser(getCurrentUser().getId());
                    userRole.setRoleId(roleId);
                    userRoleMapper.insert(userRole);
                }
            }
        }
        return 1;
    }

    @Override
    public List<UserRole> getUserRoles(String userId) {
        Example selectCondtion = new Example(UserRole.class);
        selectCondtion.createCriteria().andEqualTo("userId",userId);
        return userRoleMapper.selectByExample(selectCondtion);
    }
/***********************************************写操作-end*********************************/

}
