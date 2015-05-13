package model.dts;


/**
 * DTO = Data Transfer Object
 */
public class UserDTO {
    private String id_usuario;
    private String password;
    private String dni_persona;
    private short id_rol;
    private String fecha_acceso;
    private String nombre;
    private String apellido;
    
    public UserDTO() {
    }
    
    public UserDTO(String id_usuario) {
        this.id_usuario = id_usuario;
    }
 
    public UserDTO(String id_usuario, String password){
        this.id_usuario = id_usuario;
        this.password = password;
    }
    
    public UserDTO(String id_usuario, String nombre, String apellido) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public UserDTO(String id_usuario, String password, String dni_persona, short id_rol, String fecha_acceso, String nombre, String apellido) {
        this.id_usuario = id_usuario;
        this.password = password;
        this.dni_persona = dni_persona;
        this.id_rol = id_rol;
        this.fecha_acceso = fecha_acceso;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getIdUsuario() {
        return id_usuario;
    }

    public String getPassword() {
        return password;
    }

    public String getDni() {
        return dni_persona;
    }

    public short getIdRol() {
        return id_rol;
    }

    public String getFechaAcceso() {
        return fecha_acceso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFechaAcceso(String fecha_acceso) {
        this.fecha_acceso = fecha_acceso;
    }
}
