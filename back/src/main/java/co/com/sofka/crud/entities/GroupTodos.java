package co.com.sofka.crud.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class GroupTodos {

    @Id
    @GeneratedValue
    private long idGroupTodos;
    private String name;

    @OneToMany(mappedBy = "groupTodos")
    @JsonIgnoreProperties("groupTodos")
    private Set<Todo> todos;

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

    public Set<Todo> getTodos() {
        return todos;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }
}
