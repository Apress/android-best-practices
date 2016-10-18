package com.example.todo.service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/todo")
public class ToDoResource {

    private final ToDoAppEngineData datastore = new ToDoAppEngineData();

    public ToDoResource(){
    }

    @GET
    @Path("list/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ToDo> getToDoList(@PathParam("email") String email) {

        final List<ToDo> result = datastore.getAll(email);

        return result;
    }

    @DELETE
    @Path("{id}")
    public void deleteToDo(@PathParam("id") long id) {

        if(!datastore.deleteToDo(id)) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveToDo(ToDo item) {

        if(!datastore.updateToDo(item)){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ToDoId createToDo(ToDo item) {

        final Long newId = datastore.createToDo(item);

        if(newId == -1){
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }

        final ToDoId result = new ToDoId(newId);

        return result;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ToDo getToDo(@PathParam("id") Long id) {

        final ToDo result = datastore.getToDo(id);
        if(result == null)
            throw new WebApplicationException(Response.Status.NOT_FOUND);

        return result;
    }
}
