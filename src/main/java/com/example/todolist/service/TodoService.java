package com.example.todolist.service;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    
    @Autowired
    private TodoRepository todoRepository;
    
    // 获取所有任务
    public List<Todo> getAllTodos() {
        return todoRepository.findAllOrderByCreatedAtDesc();
    }
    
    // 根据ID获取任务
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }
    
    // 保存新任务
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    // 更新任务
    public Todo updateTodo(Todo todo) {
        return todoRepository.save(todo);
    }
    
    // 删除任务
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
    
    // 切换任务完成状态
    public Todo toggleTodoStatus(Long id) {
        Optional<Todo> todoOpt = todoRepository.findById(id);
        if (todoOpt.isPresent()) {
            Todo todo = todoOpt.get();
            todo.setCompleted(!todo.isCompleted());
            return todoRepository.save(todo);
        }
        throw new RuntimeException("Todo not found with id: " + id);
    }
    
    // 获取未完成的任务
    public List<Todo> getIncompleteTodos() {
        return todoRepository.findByCompletedFalse();
    }
    
    // 获取已完成的任务
    public List<Todo> getCompletedTodos() {
        return todoRepository.findByCompletedTrue();
    }
    
    // 根据标题搜索任务
    public List<Todo> searchTodosByTitle(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }
}
