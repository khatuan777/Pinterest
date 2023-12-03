package com.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmedia.model.Types;

public interface TypeRepository extends JpaRepository<Types, Integer> {

}