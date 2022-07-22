package com.ah.business;
import com.ah.data.api.TodoService;
import com.ah.TodoServiceStub;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.*;

public class TodoBusinessImplMockTest {

    @Test
    public void testRetrieveTodosRelatedToSpringUsingAMock() {

        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
        List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2,  filteredTodos.size());
    }

    @Test
    public void testRetrieveTodosRelatedToSpringUsingBDD() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);
        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");

        // Then
        assertThat(filteredTodos.size(), is(2));
    }

    @Test
    public void testDeleteTodosNotRelatedToSpringUsingBDD() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then
        verify(todoServiceMock).deleteTodo("Learn to Dance");
    }

    @Test
    public void verifyDeleteTodoIsCalled() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then
        verify(todoServiceMock).deleteTodo("Learn to Dance");
    }

    @Test
    @DisplayName("Verify deleteTodo() is not called with an argument containing 'Spring'")
    public void verifyDeleteTodoIsNotCalled() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then  --verify deleteTodo() is not called
        verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
    }

    @Test
    @DisplayName("Verify deleteTodo() is called x number of times")
    public void verifyDeleteTodoIsCalledNumberOfTimes() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then  --verify deleteTodo() is not called
        verify(todoServiceMock, times(1)).deleteTodo("Learn to Dance");
    }

    @Test
    @DisplayName("Verify deleteTodo() is called at least once")
    public void verifyDeleteTodoIsCalledAtLeastOnce() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then  --verify deleteTodo() is not called
        verify(todoServiceMock, atLeastOnce()).deleteTodo("Learn to Dance");
    }

    @Test
    @DisplayName("Verify deleteTodo() is called at least x number of times")
    public void verifyDeleteTodoIsCalledAtLeastXNumberOfTimes() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then  --verify deleteTodo() is not called
        verify(todoServiceMock, atLeast(1)).deleteTodo("Learn to Dance");
    }

    @Test
    @DisplayName("Verify deleteTodo() is called at least x number of times")
    public void verifyDeleteTodoIsCalledAtMostNumberOfTimes() {

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // Then  --verify deleteTodo() is not called
        verify(todoServiceMock, atMost(1)).deleteTodo("Learn to Dance");
    }



}
