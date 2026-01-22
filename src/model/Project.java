package model;

import java.util.ArrayList;

public class Project {
    private int idProyecto;
    private String nombre;
    private ArrayList<Task> tareas;

    public Project(int idProyecto, String nombre) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.tareas = new ArrayList<>();
    }

    public int getIdProyecto() { return idProyecto; }
    public void setIdProyecto(int idProyecto) { this.idProyecto = idProyecto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public ArrayList<Task> getTareas() { return tareas; }

    public void agregarTask(Task task) {
        tareas.add(task);
    }
}

