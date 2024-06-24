package com.example.Task.manager.service;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.entity.User;
import com.example.Task.manager.repository.TaskRepository;
import com.example.Task.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setName(taskDetails.getName());
            task.setDescription(taskDetails.getDescription());
            task.setUsers(taskDetails.getUsers());
            return taskRepository.save(task);
        } else {
            return null;
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        if (task != null && user != null) {
            task.getUsers().add(user);
            taskRepository.save(task);
        }
    }
}


/*
package com.example.Task.manager.service;

import com.example.Task.manager.entity.Task;
import com.example.Task.manager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTasksById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task tasks = taskRepository.findById(id).orElse(null);
        if (tasks != null) {
            tasks.setName(taskDetails.getName());
            tasks.setDescription(taskDetails.getDescription());
            //tasks.setAssignedTo(taskDetails.getAssignedTo());
            return taskRepository.save(tasks);
        } else {
            return null;
        }
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
*/