

//This is used for static data for the todo page

//AFter we cretated todorepository and changed the code in todocontroller.java

//Then we dont need todoservice.java ,we can delete it also

package com.example.practice.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	
	private static List<Todo> todos=new  ArrayList<>();
	
	private static int todosCount=0;
	static {
		todos.add(new Todo(++todosCount,"sai","Learn Java",
				LocalDate.now().plusYears(1),false));
		
		todos.add(new Todo(++todosCount,"sai","Learn AWS",
				LocalDate.now().plusYears(2),false));
		
		todos.add(new Todo(++todosCount,"sai","Learn Angular",
				LocalDate.now().plusYears(3),false));
		
	}
	
	//Create a method to return list of todos
	public List<Todo> findByUsername(String username){
		Predicate<? super Todo> predicate=todo->todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
	}
	public void addTodo(String username,String description,LocalDate targetDate,boolean done) {
		Todo todo=new Todo(++todosCount,username,description,targetDate,done);
		todos.add(todo);
	}
	public void deleteById(int id) {
		
		Predicate<? super Todo> predicate
					=todo->todo.getId()==id;
		todos.removeIf(predicate);
	}
	public Todo findById(int id) {
		Predicate<? super Todo> predicate
			=todo->todo.getId()==id;
		Todo todo = todos.stream().filter(predicate).findFirst().get();
		return todo;
	}
	public void updateTodo(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
		
	}
	
}
