package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import logic.Model;
import logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet{
    private AtomicInteger counter = new AtomicInteger(4);
    Model model = Model.getInstance();

    Gson gson = new GsonBuilder().setPrettyPrinting().create();// Для REST

    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        double salary = Double.parseDouble(request.getParameter("salary"));
        User user = new User(name,surname,salary);
        model.add(user, counter.getAndIncrement());
        pw.print("<html>" +
                "<h3>Пользователь " + name + " " + surname + " " + "с зарплатой " + salary + " " + "успешно создан</h3>" +
                "<a href=\"addUser.html\">Создать нового польщователя</a></br>" +
                "<a href=\"index.jsp\">Домой</a></br>" +
                "</html>");
    }*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuffer jb = new StringBuffer();
        String line;
        try{
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null){
                jb.append(line);
            }
        } catch (Exception e){
            System.out.println("Error");
        }
        JsonObject jObj = gson.fromJson(String.valueOf(jb), JsonObject.class);

        request.setCharacterEncoding("UTF-8");

        String name = jObj.get("name").getAsString();
        String surname = jObj.get("surname").getAsString();
        double salary = jObj.get("salary").getAsDouble();

        User user = new User(name,surname,salary);
        model.add(user, counter.getAndIncrement());

        response.setContentType("application/json;charset=utf8");
        PrintWriter pw = response.getWriter();

        pw.print(gson.toJson(model.getFromList()));
    }
}
