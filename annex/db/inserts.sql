use muro
go

delete from AccesoUsuario
go
delete from AlumnosCarMateria
go
delete from Muro
go
DBCC CHECKIDENT ('Muro', RESEED,0)
go
delete from CarreraMateria
go
delete from Carrera
go
delete from Materia
go
delete from PersonaRol
go
delete from Persona
go
delete from Rol
go

/*Inserts*/
insert into Persona(dni_persona, nombre, apellido)
values	('111','WALDO','GEREMIA'),
		('222','FERREIRA','FERREIRA'),
		('333','MARIELA','PASTARINI'),
		('444','CESAR','OSIMANI'),
		('555','JOAQUIN','NAVARRO'),
		('666','NICOLAS','OLIVER'),
		('777','NICOLAS','NAVARRO')
go

insert into Rol(id_rol,nom_rol)
values	(1,'DIRECTOR'),
		(2,'PROFESOR'),
		(3,'ALUMNO')
go

insert into PersonaRol(dni_persona, id_rol)
values	('111', 1),
		('222', 1),
		('333', 2),
		('444', 2),
		('555', 3),
		('666', 3),
		('777', 3)		
go

insert into Carrera(id_carrera,nombre, dni_director, id_rol)
values	(1,'INGENIERIA INFORMATICA','111',1),
		(2,'INGENIERIA TELECOMUNICACIONES','222',1)
go

insert into Materia(id_materia,nombre)
values	(1,'PDC'),
		(2,'DAS'),
		(3,'TECNICAS DE COMPILACION')
go

/*Trigger que carga la tabla Muro, un id_muro por cada CarreraMateria registrada*/
IF OBJECT_ID ('dbo.tr_CarreraMateria_i', 'TR') IS NOT NULL
   DROP TRIGGER dbo.tr_CarreraMateria_i
GO

CREATE TRIGGER dbo.tr_CarreraMateria_i
   ON  dbo.CarreraMateria
   FOR INSERT
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
    -- Insert statements for trigger here
	insert into Muro(id_carrera,id_materia)
	select i.id_carrera, i.id_materia
	from CarreraMateria i
	
END
GO

insert into CarreraMateria(id_carrera,id_materia,dni_profesor,id_rol)
values	(1,1,'333',2),
		(1,2,'444',2),
		(2,3,'444',2)
go

insert into AlumnosCarMateria(id_carrera,id_materia,dni_alumno,id_rol)
select cm.id_carrera,cm.id_materia, p.dni_persona, p.id_rol
from CarreraMateria cm, PersonaRol p
where cm.id_carrera = 1
 and p.id_rol = 3
go

insert into AccesoUsuario(id_usuario,password,dni_persona,id_rol)
select pr.dni_persona as id_usuario,pr.dni_persona as password,pr.dni_persona,pr.id_rol
from PersonaRol pr 
go
/*
		PROCEDIMIENTOS ALMACENADOS
*/
IF OBJECT_ID ('proc_updateAccesoUsuario_fecha_acceso') IS NOT NULL
   DROP PROCEDURE proc_updateAccesoUsuario_fecha_acceso
GO
/*Actualiza la fecha_acceso, cada vez que un usuario ingresa al muro*/
CREATE PROCEDURE proc_updateAccesoUsuario_fecha_acceso
	-- Add the parameters for the stored procedure here
	@usuario varchar(10),
	@password varchar(10),
	@fecha	varchar(20)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update AccesoUsuario
	set fecha_acceso = @fecha
	where id_usuario = @usuario
	 and password = @password
END
GO

IF OBJECT_ID ('proc_getCarreraPorUsuario') IS NOT NULL
   DROP PROCEDURE proc_getCarreraPorUsuario
GO
/*Retorna las Carreras para un determinado usuario*/
CREATE PROCEDURE proc_getCarreraPorUsuario
	-- Add the parameters for the stored procedure here
	@usuario varchar(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select distinct nombre from AccesoUsuario au join AlumnosCarMateria ac on au.dni_persona = ac.dni_alumno
	join Carrera c on ac.id_carrera = c.id_carrera
	where au.id_usuario = @usuario

END
GO

IF OBJECT_ID ('proc_getMateriaCarreraUsuario') IS NOT NULL
   DROP PROCEDURE proc_getMateriaCarreraUsuario
GO
/*Retorna las materias de un alumno en una carrera*/
CREATE PROCEDURE proc_getMateriaCarreraUsuario
	-- Add the parameters for the stored procedure here
	@carrera varchar(20),
	@usuario varchar(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select m.nombre 
	from AccesoUsuario au join AlumnosCarMateria ac on au.dni_persona = ac.dni_alumno
	join Carrera c on ac.id_carrera = c.id_carrera
	join Materia m on ac.id_materia = m.id_materia
	where au.id_usuario = @usuario
	 and c.nombre = @carrera
	
END
GO

IF OBJECT_ID ('proc_getPublicacionesMuro') IS NOT NULL
   DROP PROCEDURE proc_getPublicacionesMuro
GO
/*Retorna las publicaciones de un determinado muro*/
CREATE PROCEDURE proc_getPublicacionesMuro
	-- Add the parameters for the stored procedure here
	@muro tinyint
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select *
	from Publicacion p
	where p.id_muro = @muro
END


