package co.com.sofka.crud.controllers;

import co.com.sofka.crud.entities.GroupTodos;
import co.com.sofka.crud.services.GroupTodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class GroupTodosController {

    @Autowired
    private GroupTodosService groupTodosService;

    @GetMapping(value = "api/groups")
    public Iterable<GroupTodos> getAllGroups(){
        return groupTodosService.listGroupsTodos();
    }

    @GetMapping(value = "api/group/{groupId}")
    public GroupTodos getGroupById(@PathVariable("groupId") Long groupId){
        return groupTodosService.getGroupById(groupId);
    }

    @PostMapping(value = "api/grouptodos")
    public GroupTodos createNewGroupTodo(@RequestBody GroupTodos groupTodo){
        return groupTodosService.createNewGroupTodo(groupTodo);
    }

}
