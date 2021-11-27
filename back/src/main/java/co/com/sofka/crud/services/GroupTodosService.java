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

        // Por medio de la fabrica se pasa la lista de entidad a DTO para retornarla
        return groupsFactory.toGroupsDTO(groups);
    }

    public GroupTodosDTO getGroupById(Long groupId) {
        // Se verifica la existencia de un Group de Todos
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        // Por medio de la fabrica se pasa la entidad a DTO para retornarla
        return groupsFactory.toGroupDTO(group);
    }

    public GroupTodosDTO createNewGroupTodo(GroupTodosDTO groupDTO) {
        // Se realiza la validación para que el nombre no este vacío
        if(groupDTO.getName().trim().isEmpty()){
            throw new EmptyFieldException("El campo nombre no puede estar vacío");
        }

        // Se utiliza la fabrica para pasar el DTO de parametro a una Entidad
        GroupTodos groupEntity = groupsFactory.toGroupTodos(groupDTO);

        // Se lleva a cabo el almacenamiento de la entidad y se pasa a DTO nuevamente
        groupDTO = groupsFactory.toGroupDTO(groupTodosRepository.save(groupEntity));

        return groupDTO;
    }

    public void deleteGroupById(Long id) {
        // Se valida la existencia de un grupo con el id que se paso
        GroupTodos groupTodos = groupTodosRepository.findById(id)
                .orElseThrow( () -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        groupTodosRepository.delete(groupTodos);
    }

    public TodoDTO createNewTodoByGroupId(Long groupId, TodoDTO todoDTO) {
        // Se valida que el campo nombre contenga algo
        if(todoDTO.getName().trim().isEmpty()){
            throw new EmptyFieldException("Campo nombre no puede estár vacío");
        }

        // Se valida que exista un grupo con el id pasado por parametro
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        // Se crea una entidad pasando el DTO por parametro a la fabrica
        Todo todoEntity = todosFactory.toTodoEntity(todoDTO);

        // Se le asigna el grupo correspondiente a la entidad
        todoEntity.setGroupTodos(group);

        // Se almacena el todoEntity y luego se pasa a DTO para retornarlo
        todoDTO = todosFactory.toTodoDTO(todoRepository.save(todoEntity));

        return todoDTO;
    }

    public TodoDTO updateTodoByGroupId(Long groupId, TodoDTO todoDTO) {
        // Se valida que exista un grupo con el id pasado por parametro
        GroupTodos group = groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException(NOT_FOUND_GROUP_ID));

        // Se valida que el id que viene en el DTO existe en BD
        todoRepository.findById(todoDTO.getId())
                .orElseThrow(() -> new NotFoundTodoIdException("No existe un Todo con el id ingresado"));

        // Se crea una entidad a partir del DTO gracias a la fabrica
        Todo todoEntity = todosFactory.toTodoEntity(todoDTO);

        // Se le asigna el grupo correspondiente a la entidad
        todoEntity.setGroupTodos(group);

        // Se busca en el grupo la entidad con el id para asignarle los valores y así actualizar
        // el objeto con el estado que venia en el DTO
        group.getTodos().stream().forEach((t) -> {
            if(t.getId() == todoEntity.getId()){
                t.setName(todoEntity.getName());
                t.setCompleted(todoEntity.isCompleted());
                t.setId(todoEntity.getId());
            }
        });

        // Se le pasa a la entidad el grupo con las actualizados
        todoEntity.setGroupTodos(group);

        // Se almacena en BD con los cambios
        groupTodosRepository.save(group);

        // Se pasa a DTO la entidad para retornarla correctamente
        todoDTO = todosFactory.toTodoDTO(todoEntity);

        return todoDTO;
    }

    public void deleteTodoById(Long todoId) {
        // Se valida la existencia de un To do con el id enviado
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new NotFoundTodoIdException("El todo con id especificado no existe"));

        todoRepository.delete(todo);
    }
}
