package com.abraham.jsf.crud.local.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.abraham.jsf.crud.local.models.Empleado;

@Stateless
public class EmpleadoDAO {

    @PersistenceContext
    private EntityManager em;

    public Empleado obtener(int id) {
        return em.find(Empleado.class, id);
    }

    public List<Empleado> listar() {
        return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
    }

    public void agregar(Empleado emp) {
        em.persist(emp);
    }

    public void actualizar(Empleado emp) {
        em.merge(emp);
    }

    public void eliminar(int id) {
        Empleado emp = em.find(Empleado.class, id);
        if (emp != null) {
            em.remove(emp);
        }
    }

    public boolean correoDuplicado(String correo, int idActual) {
        Long count = em.createQuery(
                "SELECT COUNT(e) FROM Empleado e WHERE e.correo = :correo AND e.id != :idActual", Long.class)
                .setParameter("correo", correo)
                .setParameter("idActual", idActual)
                .getSingleResult();

        return count > 0;
    }

}
