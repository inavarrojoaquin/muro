package model.dto;

public class LikeDTO {
    private String id_usuario;
    private int id_publicacion;
    private String nombre;
    private String apellido;

    public LikeDTO() {}
    
    public LikeDTO(int id_publicacion) {
        this.id_publicacion = id_publicacion;
    }
    
    public LikeDTO(String id_usuario, String nombre, String apellido) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }
    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}
