package com.example.servletStart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.example.servletStart.logic.Dataset;
import com.example.servletStart.logic.Model;
import com.example.servletStart.logic.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/put")
public class ServletPut extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");

        StringBuffer sb = new StringBuffer();
        PrintWriter pw = response.getWriter();

        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            System.out.println("ERROR");
        }

        JsonObject jObj = gson.fromJson(String.valueOf(sb), JsonObject.class);

        int id = jObj.get("id").getAsInt();
        String name = jObj.get("name").getAsString();
        String surname = jObj.get("surname").getAsString();
        double salary = jObj.get("salary").getAsDouble();

        if (id > 0) {
            if (id > model.getFromList().size()) {
                Dataset dataset = new Dataset();
                dataset.code = "INTERNAL_ERROR";
                dataset.message = "Пользователя с таким ID не существует!";
                pw.print(gson.toJson(dataset));
            } else {
                model.delete(id);
                User user = new User(name, surname, salary);
                model.add(user, id);

                Dataset dataset = new Dataset();
                dataset.message = "Пользователя с ID - " + id + " изменен!";
                pw.print(gson.toJson(dataset));
            }
        } else {
            Dataset dataset = new Dataset();
            dataset.code = "INTERNAL_ERROR";
            dataset.message = "Id должен быть больше 0!";
            pw.print(gson.toJson(dataset));
        }
    }
}
