# mockito-examples
Examples using Mockito in JUnit

### System under test

The SUT is represented by

* The <code>TodoBusinessImpl</code> class which contains a method, <code>retrieveTodosRelatedToSpring</code> which takes a 'user' string as input and returns a filtered <code>List</code> of Todos that contain "Spring".

* The SUT has a dependency, <code>TodoService</code>, a Java interface that has a method <code>retrieveTodos</code> that takes a 'user' string as input and must return a <code>List</code> of Todos.

The SUT internally makes use of the dependency's method, so we mock the dependency in order to test the SUT.


##### Stubbing TodoService:

I used a stub before using mocking...

1. I created a TodoServiceStub in the test directory:
```java
public class TodoServiceStub implements TodoService {
    @Override
    public List<String> retrieveTodos(String user) {
        return Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");
    }
}
```
2. Testing TodoBusinessImpl using TodoServiceStub
```java
public class TodoBusinessImplStubTest {

    @Test
    public void testRetrieveTodosRelatedToSpringUsingAStub() {
        TodoService todoServiceStub = new TodoServiceStub();
        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceStub);
        List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2, filteredTodos.size());
    }
}
```

### Disadvantages of stubs

* As more scenarios are tested, more and more code needs to be written. For example, different results for different dummy users
* If the dependency being stubbed changes, i.e. more methods are added, the stub will be outdated 
* When there's a change in the dependency that you're stubbing, you need to change the stub
* So, stubs require maintenance
* For dynamic conditions, stubs become large and difficult
* Stubs are good for small and simple tests

### Mocking

Mocking is creating objects that simulate behaviour of real objects
Unlike stubs, mocks can be dynamically created at runtime.
Mocks offer more functionality. 
You can verify method calls and a lot more.

#####

Mocking instead of stubbing:

```java
    @Test
    public void testRetrieveTodosRelatedToSpringUsingAMock() {

        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        when(todoServiceMock.retrieveTodos("Dummy")).thenReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);
        List<String> filteredTodos = todoBusinessImpl.retrieveTodosRelatedToSpring("Dummy");
        assertEquals(2,  filteredTodos.size());
    }
```

The mock object is created of a given class

<code>when</code> is a method in Mockito used to stub a method

When <code>when</code> is called with a specific value, <code>thenReturn</code> defines what is returned.

<code>retrieveTodos()</code> is called internally in the SUT's method <code>todoBusinessImpl.retrieveTodosRelatedToSpring()</code>. 

We can test the behaviour of the SUT's method knowing that the dependency's method that it calls internally returns values specified by <code>thenReturn</code>.





