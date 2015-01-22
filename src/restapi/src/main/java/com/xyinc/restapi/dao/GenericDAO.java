package com.xyinc.restapi.dao;

import java.util.List;
import java.io.Serializable;

/**
 * An interface shared by all business data access objects.
 * <p>
 * All CRUD (create, read, update, delete) basic data access operations are
 * isolated in this interface and shared accross all DAO implementations.
 * The current design is for a state-management oriented persistence layer
 * (for example, there is no UDPATE statement function) that provides
 * automatic transactional dirty checking of business objects in persistent
 * state.
 *
 * @author Christian Bauer
 */
public interface GenericDAO<T, ID extends Serializable> {

    T findById(ID id, boolean lock) throws Exception;

    List<T> findAll() throws Exception;

    List<T> findByExample(T exampleInstance, String... excludeProperty) throws Exception;

    T makePersistent(T entity) throws Exception;

    void makeTransient(T entity) throws Exception;

}