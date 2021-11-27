package co.com.sofka.crud.services;

import co.com.sofka.crud.businessexceptions.EmptyFieldException;
import co.com.sofka.crud.businessexceptions.NotFoundGroupIdException;
import co.com.sofka.crud.businessexceptions.NotFoundTodoIdException;
import co.com.sofka.crud.dtos.GroupTodosDTO;
import co.com.sofka.crud.dtos.TodoDTO;
import co.com.sofka.crud.entities.GroupTodos;
import co.com.sofka.crud.entities.Todo;
import co.com.sofka.crud.factory.GroupTodosFactory;
import co.com.sofka.crud.factory.TodosFactory;
import co.com.sofka.crud.repository.GroupTodosRepository;
import co.com.sofka.crud.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupTodosService {

    private static final String NOT_FOUND_GROUP_ID = "El id del grupo no existe";

    @Autowired
    private GroupTodosRepository groupTodosRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private GroupTodosFactory groupsFactory;

    @Autowired
    private TodosFactory todosFactory;

    public List<GroupTodosDTO> listGroupsTodos(){
        List<GroupTodos> groups = (List<GroupTodos>) groupTodosRepository.findAll();

        return groupsFactory.toGroupsDTO(groups);
    }

    public GroupTodosDTO getGroupById(Long groupId) {
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        return groupsFactory.toGroupDTO(group);
    }

    public GroupTodosDTO createNewGroupTodo(GroupTodosDTO groupDTO) {

        if(groupDTO.getName().trim().isEmpty()){
            throw new EmptyFieldException("El campo nombre no puede estar vacío");
        }

        GroupTodos groupEntity = groupsFactory.toGroupTodos(groupDTO);
        groupDTO = groupsFactory.toGroupDTO(groupTodosRepository.save(groupEntity));

        return groupDTO;
    }

    public void deleteGroupById(Long id) {
        GroupTodos groupTodos = groupTodosRepository.findById(id)
                .orElseThrow( () -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        groupTodosRepository.delete(groupTodos);
    }

    public TodoDTO createNewTodoByGroupId(Long groupId, TodoDTO todoDTO) {
        if(todoDTO.getName().trim().isEmpty()){
            throw new EmptyFieldException("Campo nombre no puede estár vacío");
        }

        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        Todo todoEntity = todosFactory.toTodoEntity(todoDTO);
        todoEntity.setGroupTodos(group);

        todoDTO = todosFactory.toTodoDTO(todoRepository.save(todoEntity));

        return todoDTO;
    }

    public TodoDTO updateTodoByGroupId(Long groupId, TodoDTO todoDTO) {
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        todoRepository.findById(todoDTO.getId())
                .orElseThrow(() -> new NotFoundTodoIdException("No existe un Todo con el id ingresado"));

        Todo todoEntity = todosFactory.toTodoEntity(todoDTO);
        todoEntity.setGroupTodos(group);

        group.getTodos().stream().forEach((t) -> {
            if(t.getId() == todoEntity.getId()){
                t.setName(todoEntity.getName());
                t.setCompleted(todoEntity.isCompleted());
                t.setId(todoEntity.getId());
            }
        });
        todoEntity.setGroupTodos(group);
        groupTodosRepository.save(group);

        todoDTO = todosFactory.toTodoDTO(todoEntity);

        return todoDTO;
    }

    public void deleteTodoById(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundTodoIdException("El todo con id especificado no existe"));

        todoRepository.delete(todo);
    }
}
