package com.example.practice.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

//@Controller
@SessionAttributes("name")
public class TodoController {
	
	private TodoService todoService;
	//Generate a constructor to autowire 
	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}
	//list-todos
	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		String username=getLoggedinUsername(model);
		List<Todo> todos = todoService.findByUsername(username);
		model.addAttribute("todos",todos);
		return "listTodos";
	}
	
	//GET,POST this method is handling
	@RequestMapping(value="add-todo",method=RequestMethod.GET)
	public String showNewAddTodo(ModelMap model) {
		String username=getLoggedinUsername(model);
		Todo todo=new Todo(0,username,"",LocalDate.now().plusYears(1),false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo",method=RequestMethod.POST)
	public String addNewTodo(ModelMap model,@Valid Todo todo,BindingResult result) {
		String username=getLoggedinUsername(model);
		if(result.hasErrors()) {
			return "todo";
		}
		
		todoService.addTodo(username,
					todo.getDescription(),todo.getTargetDate(), false);
		return "redirect:list-todos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		//Delete todo
		todoService.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo",method=RequestMethod.GET)
	public String ShowUpdateTodoPage(@RequestParam int id,ModelMap model) {
		Todo todo=todoService.findById(id);
		model.addAttribute("todo",todo);
		return "todo";
	}
	@RequestMapping(value="update-todo",method=RequestMethod.POST)
	public String updatedTodoPage(ModelMap model,@Valid Todo todo,BindingResult result) {
		if(result.hasErrors()) {
			return "todo";
		}
		String username=getLoggedinUsername(model);
		todo.setUsername(username);
		todoService.updateTodo(todo);
		return "redirect:list-todos";
		
	}
	private String getLoggedinUsername(ModelMap model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		return authentication.getName();
	}
	
}
