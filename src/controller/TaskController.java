package controller;

import java.util.ArrayList;
import java.util.Date;

// acceso a clases
import model.Project;
import model.Task;

public class TaskController {

    private final ArrayList<Project> proyectos = new ArrayList<>();
    private final ArrayList<Task> todasLasTareas = new ArrayList<>();

    private int nextProyectoId = 1;
    private int nextTaskId = 1;

    public ArrayList<Project> getProyectos() {
        return new ArrayList<>(proyectos);
    }

    public ArrayList<Task> getTodasLasTareas() {
        return new ArrayList<>(todasLasTareas);
    }

    public Project crearProyecto(String nombre) {
        Project p = new Project(nextProyectoId++, nombre);
        proyectos.add(p);
        return p;
    }

    public Task crearTask(String nombre, Date fechaLimite, int prioridad, String nombreProyecto) {
        Task t = new Task(nextTaskId++, nombre, fechaLimite, prioridad, false, nombreProyecto);
        todasLasTareas.add(t);

        // Asociar al proyecto si existe
        Project p = buscarProyectoPorNombre(nombreProyecto);
        if (p != null) {
            p.agregarTask(t);
        }
        return t;
    }

    public Project buscarProyectoPorNombre(String nombreProyecto) {
        if (nombreProyecto == null) return null;
        String n = nombreProyecto.trim().toLowerCase();
        for (Project p : proyectos) {
            if (p.getNombre() != null && p.getNombre().trim().toLowerCase().equals(n)) {
                return p;
            }
        }
        return null;
    }

    public void cambiarEstadoCompletada(int idTask, boolean completada) {
        Task t = buscarTaskPorId(idTask);
        if (t != null) {
            t.setCompletada(completada);
        }
    }

    public Task buscarTaskPorId(int idTask) {
        for (Task t : todasLasTareas) {
            if (t.getIdTask() == idTask) return t;
        }
        return null;
    }

    public boolean eliminarTask(int idTask) {
        Task t = buscarTaskPorId(idTask);
        if (t == null) return false;

        // eliminar de lista global
        todasLasTareas.remove(t);

        // eliminar de su proyecto
        Project p = buscarProyectoPorNombre(t.getNombreProyecto());
        if (p != null) {
            p.getTareas().remove(t);
        }
        return true;
    }
}
