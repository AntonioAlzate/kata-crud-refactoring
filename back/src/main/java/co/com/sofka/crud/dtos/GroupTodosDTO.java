package co.com.sofka.crud.dtos;

import co.com.sofka.crud.entities.Todo;

import java.util.ArrayList;
import java.util.List;

public class GroupTodosDTO {

    private long idGroupTodos;
    private String name;
    private List<TodoDTO> todos = new ArrayList<>();

    public GroupTodosDTO() {
    }

    public GroupTodosDTO(long idGroupTodos, String name, List<TodoDTO> todos) {
        this.idGroupTodos = idGroupTodos;
        this.name = name;
        this.todos = todos;
    }

    public long getIdGroupTodos() {
        return idGroupTodos;
    }

    public void setIdGroupTodos(long idGroupTodos) {
        this.idGroupTodos = idGroupTodos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TodoDTO> getTodos() {
        return todos;
    }

    public void setTodos(List<TodoDTO> todos) {
        this.todos = todos;
    }
}
