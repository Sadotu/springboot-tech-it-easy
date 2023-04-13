package com.novi.techiteasy.repositories;

import com.novi.techiteasy.Television;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TelevisionRepository extends JpaRepository<Television, Long> { }
