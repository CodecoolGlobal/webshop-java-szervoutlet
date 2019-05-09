package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.jdbc.UserDaoJDBC;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/registration"})
public class Registration extends HttpServlet {

    static UserDaoJDBC userDaoJDBC = UserDaoJDBC.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        engine.process("product/registration.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password1 = req.getParameter("pass1");
        String password2 = req.getParameter("pass2");
        int phoneNumber = Integer.parseInt(req.getParameter("phoneNumber"));
        String shippingAddress = req.getParameter("Baddress") +","+ req.getParameter("Bzip")+","+req.getParameter("Bcity")+","+req.getParameter("Bcountry");
        String billingAddress = req.getParameter("Saddress")+","+req.getParameter("Szip")+","+req.getParameter("Scity")+","+req.getParameter("Scountry");
        userDaoJDBC.add(name, email, password1, phoneNumber, shippingAddress, billingAddress);
        resp.sendRedirect("/login");
    }
}
