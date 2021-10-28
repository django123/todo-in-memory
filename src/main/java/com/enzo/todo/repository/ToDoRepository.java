package com.enzo.todo.repository;

import com.enzo.todo.domain.ToDo;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> todos = new HashMap<>();

    @Override
    public ToDo save(ToDo domain) {

        ToDo result = todos.get(domain.getId());
        if(result != null){
            result.setModified(LocalDateTime.now());
            result.setDescription(domain.getDescription());
            result.setCompleted(domain.isCompleted());
            domain = result;
        }

        todos.put(domain.getId(), domain);

        return todos.get(domain.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {

        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo domain) {
       todos.remove(domain.getId());
    }

    @Override
    public ToDo findById(String id) {
        return todos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return todos.entrySet().stream().sorted(entryComparator).
                map(Map.Entry::getValue).collect(Collectors.toList());
    }

    private Comparator<Map.Entry<String, ToDo>> entryComparator = (
            Map.Entry<String,ToDo> o1, Map.Entry<String, ToDo>o2) -> {
        return o1.getValue().getCreated().compareTo(o2.getValue().getCreated());
    };
}
