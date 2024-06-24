package com.example.Task.manager.repository;

import com.example.Task.manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsersId(Long userId);
}

/*
package com.example.Task.manager.repository;

import com.example.Task.manager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedToId(Long userId);
}
*/