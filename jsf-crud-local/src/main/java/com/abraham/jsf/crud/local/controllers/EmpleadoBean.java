package com.abraham.jsf.crud.local.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abraham.jsf.crud.local.dao.EmpleadoDAO;
import com.abraham.jsf.crud.local.models.Empleado;

@Named
@ViewScoped
public class EmpleadoBean implements Serializable {

    private List<Empleado> empleados = new ArrayList<>();
    private Empleado empleadoActual = new Empleado();

    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public Empleado getEmpleadoActual() {
        return empleadoActual;
    }

    public void setEmpleadoActual(Empleado empleadoActual) {
        this.empleadoActual = empleadoActual;
    }

    public boolean isEditando() {
        return empleadoActual.getId() > 0;
    }

    @PostConstruct
    public void init() {
        empleados = empleadoDAO.listar();
    }

    public void guardar() {
        if (empleadoDAO.correoDuplicado(empleadoActual.getCorreo(), empleadoActual.getId())) {
            FacesContext.getCurrentInstance().addMessage("form:mail",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Este correo ya está registrado.", null));
            return;
        }

        if (isEditando()) {
            empleadoDAO.actualizar(empleadoActual);
        } else {
            empleadoDAO.agregar(empleadoActual);
        }
        empleados = empleadoDAO.listar(); // refresca lista
        empleadoActual = new Empleado();
    }

    // Preparar para editar
    public void seleccionarParaEditar(Empleado empleado) {
        this.empleadoActual = new Empleado(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getCorreo());
    }

    // Cancelar edición
    public void cancelarEdicion() {
        empleadoActual = new Empleado();
    }

    // Eliminar empleado
    public void eliminar(int id) {
        empleadoDAO.eliminar(id);
        empleados = empleadoDAO.listar();
    }
}
