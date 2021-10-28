package com.enzo.todo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;


@Data

public class ToDo {

    @NotNull

    private String id;

    @NotNull
    @NotBlank
    private String description;

    private boolean completed;
    private LocalDateTime created;
    private LocalDateTime modified;


    public ToDo() {
        LocalDateTime date = LocalDateTime.now();

        this.id = UUID.randomUUID().toString();
        this.created = date;
        this.modified = date;
    }

    public ToDo(String description) {
        this();
        this.description = description;
    }
}
