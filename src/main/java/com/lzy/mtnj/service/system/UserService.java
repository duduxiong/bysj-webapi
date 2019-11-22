package com.lzy.mtnj.service.system;

import com.github.pagehelper.Page;
import com.lzy.mtnj.dto.UserAddDto;
import com.lzy.mtnj.infrastructure.shiro.UserInfo;
import com.lzy.mtnj.model.UserRole;
import com.lzy.mtnj.model.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    String getSecret(String userName);
    User getCurrentUser(String userAcount);

    /**
     * 登陆
     * @param acount
     * @param password
     * @return
     * @throws Exception
     */
    UserInfo login(String acount, String password);

    User findById(String id);
    Boolean checkUserAccountExist(String userAccount);
    Page<User> findByPage(User user);
    int add(User user);
    int update(User user);
    int updateUserInfo(User user);
    int delete(String id);
    List<User> findAllProjectManager();
    List<User> findChildrenUsersByParentId(String parentId);
    List<User> findAllUsers();
    List<User> findUsersByRole(String roleId);
    boolean resetPassword(UserAddDto userinfo);
    LocalDateTime getLastRequestDate(String userAccount);
    void setLastRequestDate(String userAccount);
    int authorRoles(String id,List<String>roleList);
    List<UserRole> getUserRoles(String userId);
/***********************************/
}
