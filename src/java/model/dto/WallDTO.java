package model.dto;

public class WallDTO {
    private short id_muro;
    private String fecha_creacion;

    public WallDTO() {}

    public WallDTO(short id_muro) {
        this.id_muro = id_muro;
    }

    public WallDTO(short id_muro, String fecha_creacion) {
        this.id_muro = id_muro;
        this.fecha_creacion = fecha_creacion;
    }

    public short getId_muro() {
        return id_muro;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }
}
