package co.com.sofka.crud.services;

import co.com.sofka.crud.businessexceptions.EmptyFieldException;
import co.com.sofka.crud.businessexceptions.NotFoundGroupIdException;
import co.com.sofka.crud.entities.GroupTodos;
import co.com.sofka.crud.repository.GroupTodosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GroupTodosService {

    @Autowired
    private GroupTodosRepository groupTodosRepository;

    public Iterable<GroupTodos> listGroupsTodos(){
        return groupTodosRepository.findAll();
    }

    public GroupTodos getGroupById(Long groupId) {
        return groupTodosRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundGroupIdException("El id del grupo no existe"));
    }

    public GroupTodos createNewGroupTodo(GroupTodos groupTodo) {
        if(groupTodo.getName().trim().isEmpty()){
            throw new EmptyFieldException("El campo nombre no puede estar vacío");
        }

        return groupTodosRepository.save(groupTodo);
    }

    public void deleteGroupById(Long id) {
        GroupTodos groupTodos = groupTodosRepository.findById(id)
                .orElseThrow( () -> new NotFoundGroupIdException("El id del grupo no existe"));

        groupTodosRepository.delete(groupTodos);
    }
}
