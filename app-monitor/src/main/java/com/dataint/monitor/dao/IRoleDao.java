package com.dataint.monitor.dao;

import com.dataint.monitor.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

    List<Role> findAllByIdIn(List<Long> roleIdList);
}

