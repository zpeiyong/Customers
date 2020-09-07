package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends JpaRepository<User,Long> {

    User findByUsername(String username);

}
