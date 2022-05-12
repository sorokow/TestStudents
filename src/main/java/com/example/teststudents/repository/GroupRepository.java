package com.example.teststudents.repository;

import com.example.teststudents.entity.GroupStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface GroupRepository extends JpaRepository<GroupStudent, Long> {
}
