package model.dts;

public class CareerDTO {
    private short id_carrera;
    private String nombre;
    private String dni_director;
    private short id_rol;

    public CareerDTO() {}
    
    public CareerDTO(short id_carrera) {
        this.id_carrera = id_carrera;
    }

    public CareerDTO(String nombre) {
        this.nombre = nombre;
    }
    
    public CareerDTO(short id_carrera, String nombre) {
        this.id_carrera = id_carrera;
        this.nombre = nombre;
    }

    public short getId_carrera() {
        return id_carrera;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni_director() {
        return dni_director;
    }

    public short getId_rol() {
        return id_rol;
    }
}
