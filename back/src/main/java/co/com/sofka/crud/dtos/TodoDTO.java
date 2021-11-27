package co.com.sofka.crud.dtos;

public class TodoDTO {
    private Long id;
    private String name;
    private boolean completed;
    private Long groupTodoId;

    public TodoDTO() {
    }

    public TodoDTO(Long id, String name, boolean completed, Long groupTodoId) {
        this.id = id;
        this.name = name;
        this.completed = completed;
        this.groupTodoId = groupTodoId;
    }

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

    public Long getGroupTodoId() {
        return groupTodoId;
    }

    public void setGroupTodoId(Long groupTodoId) {
        this.groupTodoId = groupTodoId;
    }
}
