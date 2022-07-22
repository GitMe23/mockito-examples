# mockito-examples
Examples using Mockito in JUnit

I created this repo to refer back to when using the [Mockito](https://site.mockito.org/ "Mockito Homepage") testing framework.

## Jump to...

* [Stubbing](#stubbing)
* [Mockito (intro, imports)](#mockito)
* [Argument Matchers](#argument-matchers)
* [Default Values](#default-values)
* [Throwing exceptions](#throwing-exceptions)
* [BDDMockito](#bddmockito)
* [Verifying method calls](#verifying-method-calls)
* [Argument Capture](#argument-capture)
* [Hamcrest Matchers](#hamcrest-matchers)

### System under test

The SUT is represented by

* The <code>TodoBusinessImpl</code> class which contains a method, <code>retrieveTodosRelatedToSpring</code> which takes a 'user' string as input and returns a filtered <code>List</code> of Todos that contain "Spring".

* The SUT has a dependency, <code>TodoService</code>, a Java interface that has a method <code>retrieveTodos</code> that takes a 'user' string as input and must return a <code>List</code> of Todos.

The SUT internally makes use of the dependency's method, so we mock the dependency in order to test the SUT.


##### Stubbing 

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
---
### Mockito 

```java
import static org.mockito.Mockito.*;
```
```xml
    <dependency>
      <groupId>org.mockito</groupId>
      <artifactId>mockito-all</artifactId>
      <version>1.10.19</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-library</artifactId>
      <version>2.2</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.powermock</groupId>
      <artifactId>powermock-api-mockito</artifactId>
      <version> 1.7.4</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.powermock</groupId>
      <artifactId>powermock-module-junit4</artifactId>
      <version>2.0.9</version>
      <scope>test</scope>
    </dependency>
```

---

Simple example: mocking a <code>List</code> and making it return a certain value <code>when</code> one if its particular methods is called.
```java
    @Test
    public void test() {
        List listMock = mock(List.class);
        when(listMock.size()).thenReturn(2);
        assertEquals(2, listMock.size());
    }
```

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

We can chain multiple <code>thenReturn()</code>'s to return different values each time:

```java
    @Test
    public void testMockListSize_ReturnMultiple_Values() {
        List listMock = mock(List.class);
        when(listMock.size()).thenReturn(2).thenReturn(3).thenReturn(4);
        assertEquals(2, listMock.size());
        assertEquals(3, listMock.size());
        assertEquals(4, listMock.size());
    }
```
---
### Default values
When you don't specify a return value and use a mocked object's method, Mockito returns a default value:
```java
    // passes
    @Test
    public void testMockWithNoValue() {
        List listMock = mock(List.class);
        assertEquals(null, listMock.get(3));
    }
```

Type | Default
--- | --- 
int | <code>0</code>
boolean | <code>false</code>
Object | <code>null</code>

---




### Argument Matchers

The <code>Mockito</code> class extends <code>Matchers</code> which contains several matcher methods for representing data types. 
Example:

<code>anyInt()</code>
```java
when(listMock.get(anyInt())).thenReturn("foo");
```

Others include

<code>anyBoolean()</code>
<code>anyChar()</code>
<code>anyObject()</code>
<code>anyString()</code>
<code>anyCollection()</code>

...and more. There's also <code>any()</code> for a generic type.

Mockito does not allow combinations of arg matchers and hard coded values. 
The following is **not** valid:
```java
// Not valid:
when(listMock.subList(anyInt(), 5))
```
---
### Throwing exceptions

<code>thenThrow()</code> can be used instead of <code>thenReturn()</code> if used with JUnit's annotation that passes a test if a particular exception is thrown:
```java
    @Test (expected=RuntimeException.class)
    public void testMockListThrowException() {
        List listMock = mock(List.class);
        // Argument Matcher
        when(listMock.get(anyInt())).thenThrow(new RuntimeException("Something"));
        listMock.get(0);
    }
```
---
### BDDMockito
```java
import static org.mockito.BDDMockito.*;
```

Mockito provides BDD methods to implement:

1. Given --setup
2. When --actual method call
3. Then --assertion

Instead of 
```java
when().thenReturn()
```
...BDDMockito uses:
```java
given().willReturn()
```

And instead of
```java
assertEquals(2, filteredTodos.size());
```

BDDMockito uses a more BDD-like statement[^1]
```java
assertThat(filteredTodos.size(), is(2));
```
[^1]: Note that <code>assertThat()</code> and <code>is()</code> are imported from hamcrest with <code>import static org.hamcrest.CoreMatchers.*;</code> and <code>import static org.hamcrest.MatcherAssert.assertThat;
</code>

Examples:
```java
    @Test
    public void testMockListGet_UsingBDD() {
       // Given
        List<String> listMock = mock(List.class);
        given(listMock.get(anyInt())).willReturn("foo");

        // When
        String firstElement = listMock.get(0);

        // Then
        assertThat(firstElement, is("foo"));
    }
```
```java
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
```
---

### Verifying method calls

Verify that a method in a dependency being mocked has been called.

Example:

We now have a method in the dependency, <code>TodoService</code>, 
that deletes todos **not** containing "Spring".

It's still the dependency being mocked, so we're simulating its return values.

The dependency contains a method called <code>deleteTodo()</code>

The SUT has a method <code>deleteTodosNotRelatedToSpring()</code> that uses <code>deleteTodo()</code> internally

We want to verify that the method in the mocked dependency has been called.

<code>deleteTodo()</code> is only reached in the SUT's method if it iterates over a string not containing "Spring".


```java
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
```

You can also verify that a method has **not** been called with <code>never()</code>. In this cae, deleteTodo() should not be called with an argument containing 'Spring':
```java
        verify(todoServiceMock, never()).deleteTodo("Learn Spring MVC");
```
Or verify that a method has been called a number of times with <code>times()</code>
The following method should only be called once with "Learn to Dance":
```java
verify(todoServiceMock, times(1)).deleteTodo("Learn to Dance");
```
Or <code>atLeastOnce()</code> could be used:
```java
verify(todoServiceMock, atLeastOnce()).deleteTodo("Learn to Dance");
```
Also:

<code>atLeast(int minNumberOfTimes)</code>
<code>atMost(int maxNumberOfTimes)</code>
---

### Argument Capture

Better than <code>verify(todoServiceMock).deleteTodo("Learn to Dance");</code> in the above is:
```java
then(todoServiceMock).should().deleteTodo("Learn to Dance");
```
And with <code>never()</code>:
```java
then(todoServiceMock).should(never()).deleteTodo("Something with Spring");
```

#### ArgumentCaptor<>

1. Declare Argument Captor
2. Define Argument captor on specific method call
3. Capture the argument

Example:
```java
   @Test
    @DisplayName("capturing arguments")
    public void testArgumentCapture() {

        // Declare Argument Captor
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        
        // Define Argument captor on specific method call

        // Given
        TodoService todoServiceMock = mock(TodoService.class);

        List<String> todos = Arrays.asList("Learn Spring MVC", "Learn Spring", "Learn to Dance");

        given(todoServiceMock.retrieveTodos("Dummy")).willReturn(todos);

        TodoBusinessImpl todoBusinessImpl = new TodoBusinessImpl(todoServiceMock);

        // When
        todoBusinessImpl.deleteTodosNotRelatedToSpring("Dummy");

        // capturing the argument:
        then(todoServiceMock).should().deleteTodo(stringArgumentCaptor.capture());

        // check that the argument is the non 'Spring'
        assertThat(stringArgumentCaptor.getValue(), is("Learn To Dance"));
    }
```
#### Capturing arguments when a method is called multiple times

Adding another non 'Spring' todo to the list so that the method runs more than once...

We use <code>getAllValues()</code> with <code>times()</code> and <code>size()</code>instead of <code>getValue()</code>. 
We verify the number of arguments instead of the value....
```java
then(todoServiceMock).should(times(2)).deleteTodo(stringArgumentCaptor.capture());
assertThat(stringArgumentCaptor.getAllValues().size(), is(2));
```
---
### Hamcrest Matchers
```java
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.MatcherAssert.assertThat;
```
```java
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-library</artifactId>
      <version>2.2</version>
      <scope>test</scope>
    </dependency>
```

Some methods like <code>hasSize()</code> seem to need an older Hamcrest dependency on top of newer version:
```java
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-library</artifactId>
      <version>2.2</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>org.hamcrest</groupId>
      <artifactId>hamcrest-library</artifactId>
      <version>1.3</version>
    </dependency>
```

Hamcrest has several in-built methods to make readable assertions:
```java
public class HamcrestMatchersTest {
    
    @Test
    public void test() {
        List<Integer> scores = Arrays.asList(99, 100, 101, 105);
        assertThat(scores, hasSize(4));
        assertThat(scores, hasItems(99, 100));
        assertThat(scores, everyItem(greaterThan(90)));
        assertThat(scores, everyItem(lessThan(190)));

        // Strings
        assertThat("", isEmptyString());
        assertThat(null, isEmptyOrNullString());

        // Arrays
        Integer[] marks = {1, 2, 3};

        assertThat(marks, arrayWithSize(3));
        assertThat(marks, arrayContaining(1,2,3));
        assertThat(marks, arrayContainingInAnyOrder(2,3,1));
    }
}
```








