package com.dataint.diseasepo.service.impl;

import com.dataint.diseasepo.dao.IRoleDao;
import com.dataint.diseasepo.dao.entity.Role;
import com.dataint.diseasepo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    @Override
    public List<Role> queryByRoleIds(List<Long> roleIdList) {
        List<Role> roleList = roleDao.findAllByIdIn(new ArrayList<>(roleIdList))
                .stream().filter(Role::getEnabled)
                .collect(Collectors.toList());

        return roleList;
    }
}