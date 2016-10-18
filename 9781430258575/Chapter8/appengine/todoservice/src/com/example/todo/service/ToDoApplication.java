package com.example.todo.service;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ToDoApplication extends Application {

    public Set<Class<?>> getClasses() {
               final Set<Class<?>> s = new HashSet<Class<?>>();
               s.add(ToDoResource.class);
               return s;
    }
}
