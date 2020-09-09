package com.dataint.monitor.auth;

import com.dataint.cloud.common.dim.BaseExceptionEnum;
import com.dataint.cloud.common.exception.DataintBaseException;

import com.dataint.monitor.dao.entity.Role;
import com.dataint.monitor.dao.entity.User;
import com.dataint.monitor.service.IRoleService;
import com.dataint.monitor.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    /**
     * 授权
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userService.getByUsername(username);
        log.info("Load User By Username: {}", user.toString());

        if (!user.getEnabled()) {
            throw new DataintBaseException(BaseExceptionEnum.DATA_VER_USER_NOT_ENABLED);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                this.obtainGrantedAuthorities(user));
    }


    private Set<GrantedAuthority> obtainGrantedAuthorities(User user) {
        List<Role> roles = roleService.queryByRoleIds(new ArrayList<>(user.getRoleIds()));
        log.info("User: {}, Roles: {}", user.getUsername(), roles);
        Set<GrantedAuthority> sets = roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());

        return sets;
    }
}