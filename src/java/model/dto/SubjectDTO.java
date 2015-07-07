package model.dto;

public class SubjectDTO {
    private short id_materia;
    private String nombre;
    private WallDTO muro;

    public SubjectDTO() {
    }

    public SubjectDTO(short id_materia, String nombre, WallDTO muro) {
        this.id_materia = id_materia;
        this.nombre = nombre;
        this.muro = muro;
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
    public WallDTO getMuro() {
        return muro;
    }
}
