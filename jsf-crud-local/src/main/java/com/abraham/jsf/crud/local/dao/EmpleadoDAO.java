package com.abraham.jsf.crud.local.dao;

import java.util.List;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;

import com.abraham.jsf.crud.local.jpa.JpaUtil;
import com.abraham.jsf.crud.local.models.Empleado;
import java.io.Serializable;

@Dependent
public class EmpleadoDAO implements Serializable{

    public void agregar(Empleado emp) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(emp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Empleado obtener(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Empleado.class, id);
        } finally {
            em.close();
        }
    }

    public List<Empleado> listar() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizar(Empleado emp) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(emp);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminar(int id) {
        EntityManager em = JpaUtil.getEntityManager();
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
        EntityManager em = JpaUtil.getEntityManager();
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
