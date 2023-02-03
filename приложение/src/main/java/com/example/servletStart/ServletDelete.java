package com.example.servletStart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logic.Dataset;
import logic.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        PrintWriter pw = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));

        if (id > 0) {
            if (id > model.getFromList().size()) {
                Dataset dataset = new Dataset();
                dataset.code = "INTERNAL_ERROR";
                dataset.message = "Пользователя с таким ID не существует!";
                pw.print(gson.toJson(dataset));
            } else {
                Dataset dataset = new Dataset();
                dataset.message = "Пользователя с ID - " + id + " удален!";
                pw.print(gson.toJson(dataset));
                model.delete(id);
            }
        } else {
            Dataset dataset = new Dataset();
            dataset.code = "INTERNAL_ERROR";
            dataset.message = "Id должен быть больше 0!";
            pw.print(gson.toJson(dataset));
        }
    }
}
