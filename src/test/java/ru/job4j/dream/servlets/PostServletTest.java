package ru.job4j.dream.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void doGet() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);

        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        when(req.getRequestDispatcher("posts.jsp")).thenReturn(dispatcher);

        new PostServlet().doGet(req, resp);
        verify(req, times(1)).getSession();
        verify(req, times(1)).getRequestDispatcher("posts.jsp");
        verify(dispatcher, times(1)).forward(req, resp);
    }

    @Test
    public void doPost() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);

        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);

        when(req.getParameter("id")).thenReturn("5");
        when(req.getParameter("name")).thenReturn("Vadim");
        when(req.getContextPath()).thenReturn("http://localhost:8080/dreamjob");

        new PostServlet().doPost(req, resp);
        verify(req, times(1)).getParameter("id");
        verify(req, times(1)).getParameter("name");
        verify(req, times(1)).getContextPath();
        verify(resp, times(1)).sendRedirect("http://localhost:8080/dreamjob/posts.do");
        assertEquals(store.findPostById(5).getName(), "Vadim");
        assertEquals(store.findAllPosts().size(), 4);
    }
}