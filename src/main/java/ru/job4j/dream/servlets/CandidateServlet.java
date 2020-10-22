package ru.job4j.dream.servlets;

import liquibase.pro.packaged.C;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@MultipartConfig
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int photoId = 0;
        if (req.getParameter("file") != null) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(req);
                File folder = new File("images");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                for (FileItem item : items) {
                    if (!item.isFormField()) {
                        File file = new File(folder + File.separator + item.getName());
                        try (FileOutputStream out = new FileOutputStream(file)) {
                            out.write(item.getInputStream().readAllBytes());
                            photoId = PsqlStore.instOf().savePhotoReturnId(item.getName());
                        }
                    }
                }
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }
        System.out.println(photoId);
        Candidate candidate = new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name"));
        if (photoId !=0 ){
            candidate.setPhotoId(photoId);
        }
        PsqlStore.instOf().save(candidate);

        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
