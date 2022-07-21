package com.ah.business;
import com.ah.data.api.TodoService;
import java.util.ArrayList;
import java.util.List;

/** TodoBusinessImpl SUT (System under test)
 * TodoService is a dependency
 *  To write tests for the SUT we need to stub or mock the dependency
 *  (We do not have the real implementation of the dependency)
 *  The dependency we need to mock or stub could be a web service or
 *  interface/class being developed by another team i.e. not available
 */

public class TodoBusinessImpl {
    private TodoService todoService;

    public TodoBusinessImpl(TodoService todoService) {
        this.todoService = todoService;
    }

    public List<String> retrieveTodosRelatedToSpring(String user){
        List<String> filterTodos = new ArrayList<String>();
        List<String> todos = todoService.retrieveTodos(user);
        for(String todo : todos) {
            if(todo.contains("Spring")) {
                filterTodos.add(todo);
            }
        }

        return filterTodos;
    }
}
