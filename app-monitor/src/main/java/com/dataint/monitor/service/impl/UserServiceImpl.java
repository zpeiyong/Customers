package com.dataint.monitor.service.impl;

import com.dataint.cloud.common.model.Constants;
import com.dataint.cloud.common.utils.JWTUtil;
import com.dataint.monitor.dao.IUserDao;
import com.dataint.monitor.dao.entity.Role;
import com.dataint.monitor.dao.entity.User;
import com.dataint.monitor.exception.sys.UserNotExistException;
import com.dataint.monitor.model.CurrentUserVO;
import com.dataint.monitor.service.IRoleService;
import com.dataint.monitor.service.IUserRoleService;
import com.dataint.monitor.service.IUserService;
import com.dataint.monitor.utils.verify.UserVerifyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {


    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private IUserDao userDao;

    @Override
    public User getByUsername(String uniqueId) {
        User user = userDao.findByUsername(uniqueId);
        if (Objects.isNull(user)) {
            throw new UserNotExistException();
        }
        user.setRoleIds(userRoleService.queryByUserId(user.getId()));

        return user;
    }

    @Override
    public Map<String, Object> checkUserByUsername(String username, String password) {
        // check if user exist
        User user = getByUsername(username);
        UserVerifyUtil.verifyLogin(user, password);
        // 登录授权
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // jwt
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", new ArrayList<>());
        if (!CollectionUtils.isEmpty(user.getRoleIds())) {
            List<Role> roleList = roleService.queryByRoleIds(new ArrayList<>(user.getRoleIds()));
            Set<String> roleSet = roleList.stream().map(Role::getCode).collect(Collectors.toSet());
            claims.put("authorities", roleSet);
        }
        claims.put("userId", user.getId());

        // rebuild return map
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(Constants.AUTHORIZE_ACCESS_TOKEN, JWTUtil.generateToken(claims, username));
        resultMap.put("currentAuthority", claims.get("authorities"));

        return resultMap;
    }

    //    @Cacheable(cacheNames = "user#1200", key = "#username")
    @Override
    public CurrentUserVO getCurrentUser(String username) {
        // get current user
        User currentUser = getByUsername(username);
        // get roles of current user
        List<Role> roleList = roleService.queryByRoleIds(new ArrayList<>(currentUser.getRoleIds()));
        Set<String> roleSet = roleList.stream().map(Role::getCode).collect(Collectors.toSet());
        // rebuild CurrentUserVO
        CurrentUserVO userVO = new CurrentUserVO();
        userVO.setId(currentUser.getId());
        userVO.setName(currentUser.getName());
        userVO.setUsername(currentUser.getUsername());
        userVO.setCurrentAuthority(roleSet);

        return userVO;
    }

    @Override
    public boolean logout(Long userId) {
        User user = userDao.getOne(userId);

        return true;
    }

    @Override
    public User getByUserId(Long userId) {
        Optional<User> userOpt = userDao.findById(userId);
        if (!userOpt.isPresent())
            throw new UserNotExistException();
        return userOpt.get();
    }

}

