package com.ah.data.api;

import java.util.List;

//Create Todo
public interface TodoService {
    public List<String> retrieveTodos(String user);
}
