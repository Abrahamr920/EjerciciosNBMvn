package com.abraham.jsf.crud.local.dao;

import java.io.InputStream;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.abraham.jsf.crud.local.models.Empleado;

public class EmpleadoDAO {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");

    public void agregar(Empleado emp) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(emp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Empleado obtener(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public List<Empleado> listar() {
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("META-INF/persistence.xml");
        if (is == null) {
            System.out.println("No se encontró persistence.xml en META-INF");
        } else {
            System.out.println("persistence.xml encontrado");
        }

        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizar(Empleado emp) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(emp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Empleado emp = em.find(Empleado.class, id);
            if (emp != null) {
                em.remove(emp);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean correoDuplicado(String correo, int idActual) {
        EntityManager em = emf.createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(e) FROM Empleado e WHERE e.correo = :correo AND e.id != :idActual", Long.class)
                    .setParameter("correo", correo)
                    .setParameter("idActual", idActual)
                    .getSingleResult();

            return count > 0;
        } finally {
            em.close();
        }
    }
}
