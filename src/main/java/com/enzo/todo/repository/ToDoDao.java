package com.enzo.todo.repository;

import com.enzo.todo.domain.ToDoEntity;
import org.springframework.data.repository.CrudRepository;

public interface ToDoDao extends CrudRepository<ToDoEntity, String> {
}
