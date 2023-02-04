package com.example.servletStart;

import com.example.servletStart.logic.Dataset;
import com.example.servletStart.logic.Model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {
    Model model = Model.getInstance();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();// Для REST

/*    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter pw = response.getWriter();
        int id = Integer.parseInt(request.getParameter("id"));
        if (id==0){
            pw.print("<html>" +
                    "<h3>Доступные пользователи:</h3></br>" +
                    "id ползьзователя: " +
                    "<ul>");
            for (Map.Entry<Integer,User> entry:model.getFromList().entrySet()) {
                pw.print("<li>" + entry.getKey() + "</li>" +
                "<ul>" +
                "<li>Имя: " + entry.getValue().getName() + "</li>" +
                "<li>Фамилия: " + entry.getValue().getSurname() + "</li>" +
                "<li>Зарплата: " + entry.getValue().getSalary() + "</li>" +
                "</ul>");
            }
            pw.print("</ul>" +
                    "<a href=\"index.jsp\">Домой</a>" +
                    "</html>");
        } else if (id > 0){
            if (id > model.getFromList().size()){
                pw.print("<html>" +
                        "<h3>Такого пользователя нет</h3>" +
                        "<a href=\"index.jsp\">Домой</a>" +
                        "</html>");
            } else {
                pw.print("<html>" +
                        "<h3>Запрошенный пользователь: </h3>" +
                        "</br>" +
                        "Имя: " + model.getFromList().get(id).getName() + "</br>" +
                        "Фамилия: " + model.getFromList().get(id).getSurname() + "</br>" +
                        "Зарплата: " + model.getFromList().get(id).getSalary() + "</br>" +
                        "<a href=\"index.jsp\">Домой</a>" +
                        "</html>");
            }
        }else {
            pw.print("<html>" +
                    "<h3>id должен быть больше 0</h3>" +
                    "<a href=\"index.jsp\">Домой</a>" +
                    "</html>");
        }
    }*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
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


        if (id == 0) {
            pw.print(gson.toJson(model.getFromList()));
        } else if (id > 0) {
            if (id > model.getFromList().size()) {
                Dataset dataset = new Dataset();
                dataset.code = "INTERNAL_ERROR";
                dataset.message = "Пользователя с таким ID не существует!";
                pw.print(gson.toJson(dataset));
            } else {
                pw.print(gson.toJson(model.getFromList().get(id)));
            }
        } else {
            Dataset dataset = new Dataset();
            dataset.code = "INTERNAL_ERROR";
            dataset.message = "Id должен быть больше 0!";
            pw.print(gson.toJson(dataset));
        }
    }
}
