package com.example.todolist;

import com.example.todolist.model.Todo;
import com.example.todolist.repository.TodoRepository;
import com.example.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TodoListApplicationTests {

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void contextLoads() {
        // 测试应用上下文是否能正常加载
        assertThat(todoService).isNotNull();
        assertThat(todoRepository).isNotNull();
    }

    @Test
    void testCreateTodo() {
        // 测试创建任务
        Todo todo = new Todo("测试任务", "这是一个测试任务");
        Todo savedTodo = todoService.saveTodo(todo);

        assertThat(savedTodo.getId()).isNotNull();
        assertThat(savedTodo.getTitle()).isEqualTo("测试任务");
        assertThat(savedTodo.getDescription()).isEqualTo("这是一个测试任务");
        assertThat(savedTodo.isCompleted()).isFalse();
        assertThat(savedTodo.getCreatedAt()).isNotNull();
    }

    @Test
    void testToggleTodoStatus() {
        // 测试切换任务状态
        Todo todo = new Todo("测试任务", "测试描述");
        Todo savedTodo = todoService.saveTodo(todo);
        
        // 初始状态应该是未完成
        assertThat(savedTodo.isCompleted()).isFalse();
        
        // 切换状态
        Todo toggledTodo = todoService.toggleTodoStatus(savedTodo.getId());
        assertThat(toggledTodo.isCompleted()).isTrue();
        
        // 再次切换
        Todo toggledAgain = todoService.toggleTodoStatus(savedTodo.getId());
        assertThat(toggledAgain.isCompleted()).isFalse();
    }

    @Test
    void testFindByCompleted() {
        // 测试按完成状态查询
        Todo completedTodo = new Todo("已完成任务", "这个任务已完成");
        completedTodo.setCompleted(true);
        todoService.saveTodo(completedTodo);

        Todo incompleteTodo = new Todo("未完成任务", "这个任务未完成");
        todoService.saveTodo(incompleteTodo);

        assertThat(todoService.getCompletedTodos()).hasSize(1);
        assertThat(todoService.getIncompleteTodos()).hasSize(1);
    }

    @Test
    void testSearchTodos() {
        // 测试搜索功能
        Todo todo1 = new Todo("学习Spring Boot", "学习Spring Boot框架");
        Todo todo2 = new Todo("学习Java", "学习Java编程");
        Todo todo3 = new Todo("看书", "阅读技术书籍");
        
        todoService.saveTodo(todo1);
        todoService.saveTodo(todo2);
        todoService.saveTodo(todo3);

        // 搜索包含"学习"的任务
        assertThat(todoService.searchTodosByTitle("学习")).hasSize(2);
        
        // 搜索包含"Spring"的任务
        assertThat(todoService.searchTodosByTitle("Spring")).hasSize(1);
    }
}
