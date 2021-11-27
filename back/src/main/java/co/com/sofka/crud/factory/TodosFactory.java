package co.com.sofka.crud.factory;

import co.com.sofka.crud.dtos.TodoDTO;
import co.com.sofka.crud.entities.Todo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodosFactory {

    public TodoDTO toTodoDTO(Todo todo){
        TodoDTO todoDTO = new TodoDTO();

        todoDTO.setId(todo.getId());
        todoDTO.setCompleted(todo.isCompleted());
        todoDTO.setName(todo.getName());
        todoDTO.setGroupTodoId(todo.getGroupTodos().getIdGroupTodos());

        return todoDTO;
    }

    public List<TodoDTO> toTodoList(List<Todo> todos){
        List<TodoDTO> todosDTO = todos.stream().map(this::toTodoDTO).collect(Collectors.toList());

        return todosDTO;
    }

    public Todo toTodoEntity(TodoDTO todoDTO){
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setName(todoDTO.getName());
        todo.setCompleted(todoDTO.isCompleted());

        return todo;
    }
}
