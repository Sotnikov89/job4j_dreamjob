package ru.job4j.dream.servlets;


import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("method") != null) {
            doDelete(req, resp);
        }
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.setAttribute("cities", PsqlStore.instOf().findAllCities());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().save(new Candidate.Builder()
                .setId(Integer.parseInt(req.getParameter("id")))
                .setName(req.getParameter("name"))
                .setCity_id(Integer.parseInt(req.getParameter("cityId")))
                .build());
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        PsqlStore.instOf().deleteCandidate(Integer.parseInt(req.getParameter("id")));
    }
}
