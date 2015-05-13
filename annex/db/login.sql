use muro
go

IF OBJECT_ID ('proc_AccesoUsuario') IS NOT NULL
   DROP PROCEDURE proc_AccesoUsuario
GO
/*Retorna las publicaciones de un determinado muro*/
CREATE PROCEDURE proc_AccesoUsuario
	-- Add the parameters for the stored procedure here
	@usuario varchar(10),
	@password varchar(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	 SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select a.id_usuario, p.nombre, p.apellido
	from AccesoUsuario a join Persona p
		on a.dni_persona = p.dni_persona
	where a.id_usuario = @usuario
	 and a.password = @password
END


