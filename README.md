# mockito-examples
Examples using Mockito in JUnit

### System under test:

The SUT is represented by a simple class in Java that contains an instance of the TodoService interface (an object that implements it), which will be the dependency 
to be stubbed/mocked.

##### Stubbing TodoService:
1. I created TodoServiceStub in the test directory:
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
* So, stubs requires maintenance
* For dynamic conditions, stubs become large and difficult
* Stubs are good for small and simple tests

### Mocking

Mocking is creating objects that simulate behaviour of real objects
Unlike stubs, mocks can be dynamically created at runtime
Mocks offer more functionality
You can verify method calls and a lot more

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

When <code>when</code> is called with a specific value, 

<code>thenReturn</code> defines what is given back
the method 

<code>retrieveTodos()</code> is called internally in the SUT's method <code>todoBusinessImpl.retrieveTodosRelatedToSpring()</code>.





