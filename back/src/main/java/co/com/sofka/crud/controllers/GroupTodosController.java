package co.com.sofka.crud.controllers;

import co.com.sofka.crud.businessexceptions.NotFoundTodoIdException;
import co.com.sofka.crud.dtos.GroupTodosDTO;
import co.com.sofka.crud.dtos.TodoDTO;
import co.com.sofka.crud.entities.GroupTodos;
import co.com.sofka.crud.entities.Todo;
import co.com.sofka.crud.services.GroupTodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GroupTodosController {

    @Autowired
    private GroupTodosService groupTodosService;


    // Operaciones para Grupos de Todos
    @GetMapping(value = "api/groups")
    public List<GroupTodosDTO> getAllGroups(){
        return groupTodosService.listGroupsTodos();
    }

    @GetMapping(value = "api/group/{groupId}")
    public GroupTodosDTO getGroupById(@PathVariable("groupId") Long groupId){
        return groupTodosService.getGroupById(groupId);
    }

    @PostMapping(value = "api/grouptodos")
    public GroupTodosDTO createNewGroupTodo(@RequestBody GroupTodosDTO groupDTO){
        return groupTodosService.createNewGroupTodo(groupDTO);
    }

    @DeleteMapping(value = "api/grouptodos/{groupId}")
    public void deleteGroupById(@PathVariable("groupId") Long id){
        groupTodosService.deleteGroupById(id);
    }

    // Operaciones sobre un To do en particular
    @PostMapping(value = "api/todo/{groupId}")
    public TodoDTO createNewTodoByGroupId(@PathVariable("groupId") Long groupId, @RequestBody TodoDTO todoDTO){
        return groupTodosService.createNewTodoByGroupId(groupId, todoDTO);
    }

    @PutMapping(value = "api/todo/{groupId}")
    public TodoDTO updateTodoByGroupId(@PathVariable("groupId") Long groupId, @RequestBody TodoDTO todoDTO){
        if(todoDTO.getId() != null){
            return groupTodosService.updateTodoByGroupId(groupId, todoDTO);
        }
        throw new NotFoundTodoIdException("El ToDo a actualizar debe contener un id");
    }

    @DeleteMapping(value = "api/todo/{todoId}")
    public void deleteTodoById(@PathVariable("todoId") Long todoId){
        groupTodosService.deleteTodoById(todoId);
    }
}
