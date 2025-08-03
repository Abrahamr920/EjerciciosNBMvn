package com.abraham.jsf.crud.local.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
