package co.com.sofka.crud.factory;

import co.com.sofka.crud.dtos.GroupTodosDTO;
import co.com.sofka.crud.entities.GroupTodos;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupTodosFactory {

    private TodosFactory todosFactory;

    public GroupTodosFactory() {
        todosFactory = new TodosFactory();
    }

    public GroupTodosDTO toGroupDTO(GroupTodos group){
        GroupTodosDTO groupDTO = new GroupTodosDTO();
        groupDTO.setIdGroupTodos(group.getIdGroupTodos());
        groupDTO.setName(group.getName());

        if(groupDTO.getTodos() != null)
            groupDTO.setTodos(todosFactory.toTodoList(group.getTodos()));

        return groupDTO;
    }

    public List<GroupTodosDTO> toGroupsDTO(List<GroupTodos> groups){
        List<GroupTodosDTO> groupsDTO = groups.stream().map(this::toGroupDTO).collect(Collectors.toList());

        return groupsDTO;
    }

    public GroupTodos toGroupTodos(GroupTodosDTO groupTodosDTO){
        GroupTodos groupTodos = new GroupTodos();

        groupTodos.setIdGroupTodos(groupTodosDTO.getIdGroupTodos());
        groupTodos.setName(groupTodosDTO.getName());
        groupTodos.setTodos(new ArrayList<>());

        return groupTodos;
    }

}
