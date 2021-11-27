package co.com.sofka.crud.services;

import co.com.sofka.crud.businessexceptions.EmptyFieldException;
import co.com.sofka.crud.businessexceptions.NotFoundGroupIdException;
import co.com.sofka.crud.businessexceptions.NotFoundTodoIdException;
import co.com.sofka.crud.entities.GroupTodos;
import co.com.sofka.crud.entities.Todo;
import co.com.sofka.crud.repository.GroupTodosRepository;
import co.com.sofka.crud.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GroupTodosService {

    private static final String NOT_FOUND_GROUP_ID = "El id del grupo no existe";

    @Autowired
    private GroupTodosRepository groupTodosRepository;

    @Autowired
    private TodoRepository todoRepository;

    public Iterable<GroupTodos> listGroupsTodos(){
        return groupTodosRepository.findAll();
    }

    public GroupTodos getGroupById(Long groupId) {
        return groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));
    }

    public GroupTodos createNewGroupTodo(GroupTodos groupTodo) {
        if(groupTodo.getName().trim().isEmpty()){
            throw new EmptyFieldException("El campo nombre no puede estar vacío");
        }

        return groupTodosRepository.save(groupTodo);
    }

    public void deleteGroupById(Long id) {
        GroupTodos groupTodos = groupTodosRepository.findById(id)
                .orElseThrow( () -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        groupTodosRepository.delete(groupTodos);
    }

    public Todo createNewTodoByGroupId(Long groupId, Todo todo) {
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        if(todo.getName().trim().isEmpty()){
            throw new EmptyFieldException("Campo nombre no puede estár vacío");
        }

        todo.setGroupTodos(group);
        Todo todoDb = todoRepository.save(todo);

        return todoDb;
    }

    public Todo updateTodoByGroupId(Long groupId, Todo todo) {
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        group.getTodos().stream().forEach((t) -> {
            if(t.getId() == todo.getId()){
                t.setName(todo.getName());
                t.setCompleted(todo.isCompleted());
                t.setId(todo.getId());
            }
        });
        todo.setGroupTodos(group);
        groupTodosRepository.save(group);

        return todo;
    }

    public void deleteTodoById(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundTodoIdException("El todo con id especificado no existe"));

        todoRepository.delete(todo);
    }
}
