package com.ah.business;
import com.ah.TodoServiceStub;
import com.ah.data.api.TodoService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TodoBusinessImplStubTest {

    @Test
    public void testRetrieveTodosRelatedToSpringUsingAStub() {
        TodoService todoServiceStub = new TodoServiceStub();
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceStub);
        List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2,  filteredTodos.size());
    }
}
