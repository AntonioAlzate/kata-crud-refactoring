package co.com.sofka.crud.controllers;

import co.com.sofka.crud.dtos.TodoDTO;
import co.com.sofka.crud.services.TodoService;
import co.com.sofka.crud.entities.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping(value = "api/todos")
    public List<TodoDTO> list(){
        return service.list();
    }



}