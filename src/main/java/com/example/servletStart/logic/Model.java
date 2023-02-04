package com.example.servletStart.logic;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {
    private static final Model instanse = new Model();
    private final Map<Integer,User>model;

    public static Model getInstance(){
        return instanse;
    }

    private Model(){
        model = new HashMap<>();

        model.put(1,new User("Nazar","Groove",80000));
        model.put(2,new User("Nikita","Petrov",150000));
        model.put(3,new User("Serena","Saratov",100000));
    }
    public void add(User user, int id){
        model.put(id,user);
    }
    public void delete(int id){
        model.remove(id);
    }

    public Map<Integer, User>getFromList(){
        return model;
    }
}
