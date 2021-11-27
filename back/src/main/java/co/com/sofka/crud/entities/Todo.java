package co.com.sofka.crud.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "idGroupTodos", referencedColumnName = "idGroupTodos")
    private GroupTodos groupTodos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public GroupTodos getGroupTodos() {
        return groupTodos;
    }

    public void setGroupTodos(GroupTodos groupTodos) {
        this.groupTodos = groupTodos;
    }
}
