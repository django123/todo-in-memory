package com.enzo.todo.domain;

public class ToDoEntityBuilder {

    private static ToDoEntityBuilder instance = new ToDoEntityBuilder();

    private String id = null;

    private String description = "";

    private ToDoEntityBuilder(){}

    public static ToDoEntityBuilder create(){
        return instance;
    }

    public ToDoEntityBuilder withDescription(String description){
        this.description = description;

        return instance;
    }

    public ToDoEntityBuilder withId(String id){
        this.id = id;

        return instance;
    }

    public ToDoEntity build(){
        ToDoEntity result = new ToDoEntity(this.description);
        if (id != null)
            result.setId(id);
        return result;
    }
}
