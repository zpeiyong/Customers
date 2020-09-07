package com.dataint.diseasepo.service;

import com.dataint.diseasepo.dao.entity.User;
import com.dataint.diseasepo.model.CurrentUserVO;

import java.util.Map;

public interface IUserService {
    User getByUsername(String uniqueId);

    Map<String, Object> checkUserByUsername(String username, String password);

    CurrentUserVO getCurrentUser(String username);

    boolean logout(Long UserId);

    User getByUserId(Long userId);

}
