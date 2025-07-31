package com.abraham.jsf.crud.local.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.abraham.jsf.crud.local.models.Empleado;

@Named
@ViewScoped
public class EmpleadoBean implements Serializable {

    private List<Empleado> empleados = new ArrayList<>();
    private Empleado empleadoActual = new Empleado();
    private int contadorId = 1;

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
        empleados.add(new Empleado(contadorId++, "Juan Pérez", "juan@example.com"));
        empleados.add(new Empleado(contadorId++, "María López", "maria@example.com"));
        empleados.add(new Empleado(contadorId++, "Carlos Gómez", "carlos@example.com"));
        empleados.add(new Empleado(contadorId++, "Ana Torres", "ana@example.com"));
        empleados.add(new Empleado(contadorId++, "Luis Castillo", "luis@example.com"));
    }

    public void guardar() {
        if (correoDuplicado(empleadoActual.getCorreo(), empleadoActual.getId())) {
            FacesContext.getCurrentInstance().addMessage("form:mail",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Este correo ya está registrado.", null));
            return;
        }

        if (isEditando()) {
            empleados.stream()
                    .filter(e -> e.getId() == empleadoActual.getId())
                    .findFirst()
                    .ifPresent(e -> {
                        e.setNombre(empleadoActual.getNombre());
                        e.setCorreo(empleadoActual.getCorreo());
                    });

        } else {
            empleadoActual.setId(contadorId++);
            empleados.add(empleadoActual);
        }

        empleadoActual = new Empleado();
    }

    private boolean correoDuplicado(String correo, int idActual) {
        return empleados.stream()
                .anyMatch(e -> !Objects.equals(e.getId(), idActual)
                        && e.getCorreo().equalsIgnoreCase(correo));
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
        empleados.removeIf(e -> e.getId() == id);
    }
}
