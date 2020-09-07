package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

    List<Role> findAllByIdIn(List<Long> roleIdList);
}

