package ru.job4j.dream.servlets;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if (PsqlStore.instOf().findUserByEmail(email).getEmail()!=null){
            req.setAttribute("error", "Пользователь с таким email уже существует");
            req.getRequestDispatcher("reg.jsp").forward(req, resp);
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        //req.getSession().setAttribute("user", PsqlStore.instOf().save(user));
        resp.sendRedirect(req.getContextPath() + "/login.");
    }
}
