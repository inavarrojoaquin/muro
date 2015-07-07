package model.dto;

public class WallDTO {
    private short id_muro;
    private boolean habilitado;
    private String fecha_creacion;

    public WallDTO() {}

    public WallDTO(short id_muro, boolean habilitado, String fecha_creacion) {
        this.id_muro = id_muro;
        this.habilitado = habilitado;
        this.fecha_creacion = fecha_creacion;
    }

    public short getId_muro() {
        return id_muro;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }
}
