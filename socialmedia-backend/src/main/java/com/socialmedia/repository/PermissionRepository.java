package com.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.Permissions;

public interface PermissionRepository extends JpaRepository<Permissions, Integer> {

}