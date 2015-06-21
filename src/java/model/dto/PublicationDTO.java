package model.dto;

public class PublicationDTO {
    private int id_publicacion;
    private String texto;
    private short id_muro;
    private String id_usuario;
    private String nombreUsuario;
    private short likes;
    private String userLike;
    private String fecha_publicacion;
    private boolean habilitado;
    private boolean eliminado;

    public PublicationDTO() {}
    
    public PublicationDTO(int id_publicacion, String texto, short likes, String userLike, String id_usuario, String nombreUsuario, String fecha_publicacion) {
        this.id_publicacion = id_publicacion;
        this.texto = texto;
        this.likes = likes;
        this.userLike = userLike;
        this.id_usuario = id_usuario;
        this.nombreUsuario = nombreUsuario;
        this.fecha_publicacion = fecha_publicacion;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public int getId_publicacion() {
        return id_publicacion;
    }

    public String getTexto() {
        return texto;
    }

    public short getId_muro() {
        return id_muro;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public short getLikes() {
        return likes;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setLikes(short likes) {
        this.likes = likes;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
    public void setUserLike(String userLike) {
        this.userLike = userLike;
    }    
}
