package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.UserDaoJDBC;
import com.codecool.shop.model.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(urlPatterns = {"/login"})
public class Login extends HttpServlet {

    static UserDaoJDBC userDaoJDBC = UserDaoJDBC.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session=req.getSession(false);
        if(session != null) resp.sendRedirect("/");

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/logAndReg/login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
        if(userDaoJDBC.find(email, pass) != null){
            User loggedInUser = userDaoJDBC.find(email, pass);
            HttpSession session=req.getSession();
            session.setAttribute("name",loggedInUser.getName());
            session.setAttribute("id", loggedInUser.getId());
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/login");
        }
    }

}
