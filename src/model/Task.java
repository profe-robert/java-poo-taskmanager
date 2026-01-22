package model;

import java.util.Date;

public class Task {
    private int idTask;
    private String nombre;
    private Date fechaLimite;
    private int prioridad; // 1=Alta, 2=Media, 3=Baja
    private boolean completada;

    // Referencia simple al proyecto (nombre) para mostrar en tabla fácilmente
    private String nombreProyecto;

    public Task(int idTask, String nombre, Date fechaLimite, int prioridad, boolean completada, String nombreProyecto) {
        this.idTask = idTask;
        this.nombre = nombre;
        this.fechaLimite = fechaLimite;
        this.prioridad = prioridad;
        this.completada = completada;
        this.nombreProyecto = nombreProyecto;
    }

    public int getIdTask() { return idTask; }
    public void setIdTask(int idTask) { this.idTask = idTask; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(Date fechaLimite) { this.fechaLimite = fechaLimite; }

    public int getPrioridad() { return prioridad; }
    public void setPrioridad(int prioridad) { this.prioridad = prioridad; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

    public String getNombreProyecto() { return nombreProyecto; }
    public void setNombreProyecto(String nombreProyecto) { this.nombreProyecto = nombreProyecto; }

    public String prioridadComoTexto() {
        switch (prioridad) {
            case 1: return "ALTA";
            case 2: return "MEDIA";
            case 3: return "BAJA";
            default: return "DESCONOCIDA";
        }
    }
}

