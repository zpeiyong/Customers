package com.dataint.monitor.service.impl;

import com.dataint.monitor.dao.IUserRoleDao;
import com.dataint.monitor.dao.entity.UserRole;
import com.dataint.monitor.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements IUserRoleService {

    @Autowired
    private IUserRoleDao userRoleDao;

    @Override
    @Transactional
    public boolean saveBatch(Long userId, Set<Long> roleIds, String updateBy) {
        if (CollectionUtils.isEmpty(roleIds))
            return false;
        removeByUserId(userId);

        List<UserRole> userRoles = roleIds.stream().map(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).collect(Collectors.toList());

        userRoleDao.saveAll(userRoles);

        return true;
    }

    @Override
    @Transactional
    public boolean removeByUserId(Long userId) {
        userRoleDao.deleteByUserId(userId);

        return true;
    }

    @Override
    public Set<Long> queryByUserId(Long userId) {
        List<UserRole> userRoleList = userRoleDao.findAllByUserId(userId);

        return userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }


}
