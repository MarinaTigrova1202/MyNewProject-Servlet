package com.example.servletStart;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.example.servletStart.logic.Dataset;
import com.example.servletStart.logic.Model;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/delete")
public class ServletDelete extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

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
