package com.dataint.diseasepo.dao;

import com.dataint.diseasepo.dao.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRoleDao extends JpaRepository<UserRole,Integer> {
    void deleteByUserId(Long userId);

    List<UserRole> findAllByUserId(Long userId);

    Optional<UserRole> findById(Long id);
}
