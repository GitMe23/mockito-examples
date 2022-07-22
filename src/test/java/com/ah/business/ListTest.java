package com.ah.business;
import com.ah.data.api.TodoService;
import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ListTest {

   @Test
    public void simpleMockTest() {
        List listMock = mock(List.class);
        when(listMock.size()).thenReturn(2);
        assertEquals(2, listMock.size());
    }

    @Test
    public void testMockListSize_ReturnMultiple_Values() {
        List listMock = mock(List.class);
        when(listMock.size()).thenReturn(2).thenReturn(3);
        assertEquals(2, listMock.size());
        assertEquals(3, listMock.size());
    }

    @Test
    public void testMockListGet() {
       List listMock = mock(List.class);
       when(listMock.get(3)).thenReturn("foo");
       assertEquals("foo", listMock.get(3));
    }

    @Test
    public void testMockWithNoValue() {
       // Mockito returns default values if nothing else is defined
        List listMock = mock(List.class);
        assertEquals(null, listMock.get(3));
    }

    @Test
    public void testMockListMatcher() {
        List listMock = mock(List.class);
        // Argument Matcher
        when(listMock.get(anyInt())).thenReturn("foo");
        assertEquals("foo", listMock.get(3));
    }

    @Test (expected=RuntimeException.class)
    public void testMockListThrowException() {
        List listMock = mock(List.class);
        // Argument Matcher
        when(listMock.get(anyInt())).thenThrow(new RuntimeException("Something"));
        listMock.get(0);
    }

}
