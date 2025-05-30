package com.example.todolist.controller;

import com.example.todolist.model.Todo;
import com.example.todolist.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class TodoController {
    
    @Autowired
    private TodoService todoService;
    
    // 首页 - 显示所有任务
    @GetMapping
    public String index(Model model, @RequestParam(value = "filter", defaultValue = "all") String filter) {
        List<Todo> todos;
        
        switch (filter) {
            case "completed":
                todos = todoService.getCompletedTodos();
                break;
            case "incomplete":
                todos = todoService.getIncompleteTodos();
                break;
            default:
                todos = todoService.getAllTodos();
        }
        
        model.addAttribute("todos", todos);
        model.addAttribute("currentFilter", filter);
        model.addAttribute("newTodo", new Todo());
        return "index";
    }
    
    // 添加新任务
    @PostMapping("/add")
    public String addTodo(@ModelAttribute Todo todo, RedirectAttributes redirectAttributes) {
        try {
            todoService.saveTodo(todo);
            redirectAttributes.addFlashAttribute("successMessage", "任务添加成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "添加任务失败：" + e.getMessage());
        }
        return "redirect:/";
    }
    
    // 切换任务状态
    @PostMapping("/toggle/{id}")
    public String toggleTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            todoService.toggleTodoStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "任务状态更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新任务状态失败：" + e.getMessage());
        }
        return "redirect:/";
    }
    
    // 删除任务
    @PostMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            todoService.deleteTodo(id);
            redirectAttributes.addFlashAttribute("successMessage", "任务删除成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "删除任务失败：" + e.getMessage());
        }
        return "redirect:/";
    }
    
    // 编辑任务页面
    @GetMapping("/edit/{id}")
    public String editTodoForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Todo> todoOpt = todoService.getTodoById(id);
        if (todoOpt.isPresent()) {
            model.addAttribute("todo", todoOpt.get());
            return "edit";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "任务不存在！");
            return "redirect:/";
        }
    }
    
    // 更新任务
    @PostMapping("/update/{id}")
    public String updateTodo(@PathVariable Long id, @ModelAttribute Todo todo, RedirectAttributes redirectAttributes) {
        try {
            todo.setId(id);
            todoService.updateTodo(todo);
            redirectAttributes.addFlashAttribute("successMessage", "任务更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "更新任务失败：" + e.getMessage());
        }
        return "redirect:/";
    }
    
    // 搜索任务
    @GetMapping("/search")
    public String searchTodos(@RequestParam("query") String query, Model model) {
        List<Todo> todos = todoService.searchTodosByTitle(query);
        model.addAttribute("todos", todos);
        model.addAttribute("searchQuery", query);
        model.addAttribute("newTodo", new Todo());
        return "index";
    }
}
