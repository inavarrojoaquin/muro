package model.dto;

public class CommentDTO {
    private int id_comentario;
    private String texto;
    private int id_publicacion;
    private String nombre;
    private String apellido;
    private String fecha_creacion;

    public CommentDTO() {
    }

    public CommentDTO(int id_comentario, String texto, int id_publicacion, String nombre, String apellido, String fecha_creacion) {
        this.id_comentario = id_comentario;
        this.texto = texto;
        this.id_publicacion = id_publicacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_creacion = fecha_creacion;
    }

    public int getId_comentario() {
        return id_comentario;
    }

    public String getTexto() {
        return texto;
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

    public String getFecha_creacion() {
        return fecha_creacion;
    }
}
