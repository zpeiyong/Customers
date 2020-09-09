package com.dataint.monitor.service;

import com.dataint.monitor.dao.entity.User;
import com.dataint.monitor.model.CurrentUserVO;

import java.util.Map;

public interface IUserService {
    User getByUsername(String uniqueId);

    Map<String, Object> checkUserByUsername(String username, String password);

    CurrentUserVO getCurrentUser(String username);

    boolean logout(Long UserId);

    User getByUserId(Long userId);

}
