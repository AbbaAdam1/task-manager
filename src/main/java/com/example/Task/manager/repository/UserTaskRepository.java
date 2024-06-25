package com.example.Task.manager.repository;

import com.example.Task.manager.entity.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Integer> {
    List<UserTask> findByUserId(Integer userId);
    List<UserTask> findByTaskId(Integer taskId);
}
