package model.dto;

public class ShareDTO {
    private int id_publicacion;
    private String id_usuario_comparte;
    private String texto;
    private String destino;

    public ShareDTO() {
    }

    public ShareDTO(int id_publicacion, String id_usuario_comparte, String texto, String destino) {
        this.id_publicacion = id_publicacion;
        this.id_usuario_comparte = id_usuario_comparte;
        this.texto = texto;
        this.destino = destino;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public String getId_usuario_comparte() {
        return id_usuario_comparte;
    }

    public String getTexto() {
        return texto;
    }

    public String getDestino() {
        return destino;
    }
}
