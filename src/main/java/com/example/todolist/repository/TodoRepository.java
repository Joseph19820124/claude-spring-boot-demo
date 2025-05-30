package com.example.todolist.repository;

import com.example.todolist.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    
    // 查找所有未完成的任务
    List<Todo> findByCompletedFalse();
    
    // 查找所有已完成的任务
    List<Todo> findByCompletedTrue();
    
    // 按创建时间倒序排列
    @Query("SELECT t FROM Todo t ORDER BY t.createdAt DESC")
    List<Todo> findAllOrderByCreatedAtDesc();
    
    // 根据标题模糊查询
    List<Todo> findByTitleContainingIgnoreCase(String title);
}
