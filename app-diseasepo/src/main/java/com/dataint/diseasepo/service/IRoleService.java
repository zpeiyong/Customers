package com.dataint.diseasepo.service;

import com.dataint.diseasepo.dao.entity.Role;

import java.util.List;

public interface IRoleService {

    List<Role> queryByRoleIds(List<Long> roleIdList);
}