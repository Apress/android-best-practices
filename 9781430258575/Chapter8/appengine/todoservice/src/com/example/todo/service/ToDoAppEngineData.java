package com.example.todo.service;

import javax.jdo.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoAppEngineData {

    //Ensure there is only one instance of the factory
    private static final PersistenceManagerFactory factory = JDOHelper
   			.getPersistenceManagerFactory("transactions-optional");

    private final PersistenceManager manager;

    public ToDoAppEngineData(){
        manager = factory.getPersistenceManager();
    }

    public Long createToDo(ToDo item) {

        if(item.getId() != null) {
            item.setId(null);
        }

        final Transaction trans = manager.currentTransaction();
        try {
            trans.begin();
            final ToDo newItem = manager.makePersistent(item);
            trans.commit();
            return newItem.getId();
        }
        catch (Exception ex) {
            trans.rollback();
            return -1l;
        }
        finally {
            manager.close();
        }
    }

    public boolean updateToDo(final ToDo item) {

        final ToDo existing = getToDo(item.getId());
        if(existing == null)
            return false;

        final Transaction trans = manager.currentTransaction();
        try {

            trans.begin();
            manager.makePersistent(item);
            trans.commit();
            return true;
        }
        catch (Exception ex) {
            trans.rollback();
            return false;
        }
        finally {
            manager.close();
        }
    }

    public ToDo getToDo(Long id) {

        try {
            ToDo item = manager.getObjectById(ToDo.class, id);
            item = manager.detachCopy(item);
            return item;
        }
        catch(JDOObjectNotFoundException ex){
            return null;
        }
    }


    public boolean deleteToDo(Long id) {

        final Transaction trans = manager.currentTransaction();

        try {
            trans.begin();
            final ToDo item = manager.getObjectById(ToDo.class, id);

            if(item == null)
                return false;

            manager.deletePersistent(item);
            trans.commit();
            return true;
        } catch(JDOObjectNotFoundException ex){
            trans.rollback();
            return false;
        } catch (Exception ex) {
            trans.rollback();
            return false;
        } finally {
            manager.close();
        }
    }

    public List<ToDo> getAll(String email) {

        final Query query = manager.newQuery(ToDo.class);
        query.setFilter("email == emailParam");
        query.declareParameters("String emailParam");
        List<ToDo> results;

        try {
            final List<ToDo> temp = (List<ToDo>) query.execute(email);
            if (temp.isEmpty()) {
                return new ArrayList<ToDo>();
            }

            results = (List<ToDo>) manager.detachCopyAll(temp);

        } catch (Exception e){
            results = new ArrayList<ToDo>();
            e.printStackTrace();
        } finally {
            query.closeAll();
            manager.close();
        }
        return results;
    }
}
