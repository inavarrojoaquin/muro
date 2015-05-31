package model.dts;

public class SubjectDTO {
    private short id_materia;
    private String nombre;

    public SubjectDTO() {
    }

    public SubjectDTO(short id_materia, String nombre) {
        this.id_materia = id_materia;
        this.nombre = nombre;
    }

    public short getId_materia() {
        return id_materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
