package com.enzo.todo.controller;

import com.enzo.todo.domain.ToDoBuilder;
import com.enzo.todo.domain.ToDoEntity;
import com.enzo.todo.domain.ToDoEntityBuilder;
import com.enzo.todo.repository.ToDoDao;
import com.enzo.todo.validation.ToDoValidationError;
import com.enzo.todo.validation.ToDoValidationErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ToDoResource {

    private final ToDoDao toDoDao;

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDoEntity>> getToDos(){
        return ResponseEntity.ok(toDoDao.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDoEntity> getToDoById(@PathVariable String id){
        ToDoEntity toDo = toDoDao.findById(id).get();
        if (toDo != null)
            return ResponseEntity.ok(toDo);
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDoEntity> setCompleted(@PathVariable String id){
        Optional<ToDoEntity> toDo = toDoDao.findById(id);
        if(!toDo.isPresent())
            return ResponseEntity.notFound().build();
        ToDoEntity result = toDo.get();
        result.setCompleted(true);
        toDoDao.save(result);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.ok().header("Location",location.toString()).build();
    }

    @RequestMapping(value="/todo", method = {RequestMethod.
            POST,RequestMethod.PUT})
    public ResponseEntity<?> createToDo(@Valid @RequestBody ToDoEntity toDo, Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(ToDoValidationErrorBuilder.fromBindingErrors(errors));
        }
        ToDoEntity result = toDoDao.save(toDo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDoEntity> deleteToDo(@PathVariable String id){
        toDoDao.delete(ToDoEntityBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/todo")
    public ResponseEntity<ToDoEntity> deleteToDo(@RequestBody ToDoEntity toDo){
        toDoDao.delete(toDo);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception) {
        return new ToDoValidationError(exception.getMessage());
    }



}
