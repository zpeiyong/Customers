package com.dataint.monitor.service;

import com.dataint.monitor.dao.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> queryByRoleIds(List<Long> roleIdList);
}