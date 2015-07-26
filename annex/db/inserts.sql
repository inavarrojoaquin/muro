use muro
go

delete from [dbo].[Like]
go
delete from Comentario
go
DBCC CHECKIDENT ('Comentario', RESEED,0)
go
delete from Compartir
go
DBCC CHECKIDENT ('Compartir', RESEED,0)
go
delete from Publicacion
go
DBCC CHECKIDENT ('Publicacion', RESEED,0)
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

/**Only for testing*/
insert into AlumnosCarMateria(id_carrera,id_materia,dni_alumno,id_rol)
values (2, 3, '555', 3)
go

insert into AccesoUsuario(id_usuario,password,dni_persona,id_rol, fecha_acceso)
select pr.dni_persona as id_usuario,pr.dni_persona as password,pr.dni_persona,pr.id_rol, CONVERT(VARCHAR(10), GETDATE(), 103) + ' '  + convert(VARCHAR(8), GETDATE(), 14) as fecha_acceso
from PersonaRol pr 
go

/*
		PROCEDIMIENTOS ALMACENADOS
*/
IF OBJECT_ID ('proc_selectAllUsers') IS NOT NULL
   DROP PROCEDURE proc_selectAllUsers
GO
/*Retorna todos los usuarios*/
CREATE PROCEDURE proc_selectAllUsers
	-- Add the parameters for the stored procedure here
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	SELECT a.id_usuario, p.nombre
	FROM AccesoUsuario a join Persona p 
	on p.dni_persona = a.dni_persona
END
GO

IF OBJECT_ID ('proc_updateAccesoUsuario_fecha_acceso') IS NOT NULL
   DROP PROCEDURE proc_updateAccesoUsuario_fecha_acceso
GO
/*Actualiza la fecha_acceso, cada vez que un usuario ingresa al muro*/
CREATE PROCEDURE proc_updateAccesoUsuario_fecha_acceso
	-- Add the parameters for the stored procedure here
	@usuario varchar(10),
	@fecha	varchar(25)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update AccesoUsuario
	set fecha_acceso = @fecha
	where id_usuario = @usuario
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
	select distinct c.id_carrera, c.nombre
	from AccesoUsuario au 
	join (select acm.id_carrera, acm.id_materia, acm.dni_alumno  as dni_persona
		from AlumnosCarMateria acm
		UNION
		select cm.id_carrera, cm.id_materia, cm.dni_profesor
		from CarreraMateria cm) t
	on au.dni_persona = t.dni_persona
	join Carrera c on c.id_carrera = t.id_carrera
	where au.dni_persona = @usuario
END
GO

IF OBJECT_ID ('proc_getMateriaCarreraUsuario') IS NOT NULL
   DROP PROCEDURE proc_getMateriaCarreraUsuario
GO
/*Retorna las materias y el muro asociado a cada materia de un alumno en una carrera*/
CREATE PROCEDURE proc_getMateriaCarreraUsuario
	-- Add the parameters for the stored procedure here
	@id_usuario varchar(10),
	@id_carrera varchar(50)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select mat.id_materia, mat.nombre, m.id_muro, m.habilitado, m.fecha_creacion
	from Muro m
	join (select distinct t.id_carrera, m.id_materia, m.nombre
		from AccesoUsuario au 
		join (select acm.id_carrera, acm.id_materia, acm.dni_alumno  as dni_persona
			from AlumnosCarMateria acm
			UNION
			select cm.id_carrera, cm.id_materia, cm.dni_profesor
			from CarreraMateria cm) t
		on au.dni_persona = t.dni_persona
		join Materia m on m.id_materia = t.id_materia
		where au.dni_persona = @id_usuario
		and t.id_carrera = @id_carrera)mat
	on m.id_carrera = mat.id_carrera
	and m.id_materia = mat.id_materia
END
GO

IF OBJECT_ID ('proc_updateMuro_EnableDisable') IS NOT NULL
   DROP PROCEDURE proc_updateMuro_EnableDisable
GO
/*Deshabilita o habilita el muro correspondiente a una carrera-materia*/
CREATE PROCEDURE proc_updateMuro_EnableDisable
	-- Add the parameters for the stored procedure here
	@id_carrera tinyint,
	@id_materia tinyint,
	@habilitado bit
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update Muro
	set habilitado = @habilitado
	where id_carrera = @id_carrera
		and id_materia = @id_materia
END
go

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
	select p.id_publicacion, p.texto, p.likes, p.id_usuario, pe.nombre+' '+pe.apellido as nombre, p.fecha_publicacion
	from Publicacion p 
	join AccesoUsuario au on p.id_usuario = au.id_usuario
	join Persona pe on au.dni_persona = pe.dni_persona
	join Muro m on m.id_muro = p.id_muro
	where p.id_muro = @muro
     and m.habilitado = 1
	 and p.habilitado = 1
	 and p.eliminado = 0
END
GO

IF OBJECT_ID ('proc_getPublicacionesMuroAfterDate') IS NOT NULL
   DROP PROCEDURE proc_getPublicacionesMuroAfterDate
GO
/*Retorna las 5 publicaciones de un determinado muro posteriores a la fecha ingresada*/
CREATE PROCEDURE proc_getPublicacionesMuroAfterDate
	-- Add the parameters for the stored procedure here
	@muro tinyint,
	@fecha varchar(25)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select top 5 p.id_publicacion, p.texto, p.likes, p.id_usuario, pe.nombre+' '+pe.apellido as nombre, p.fecha_publicacion
	from Publicacion p 
	join AccesoUsuario au on p.id_usuario = au.id_usuario
	join Persona pe on au.dni_persona = pe.dni_persona
	join Muro m on m.id_muro = p.id_muro
	where p.id_muro = @muro
	 and m.habilitado = 1
	 and p.fecha_publicacion > @fecha
	 and p.habilitado = 1
	 and p.eliminado = 0
END
GO

IF OBJECT_ID ('proc_insertPublicacion') IS NOT NULL
   DROP PROCEDURE proc_insertPublicacion
GO
/*Inserta una publicacion en un determinado muro*/
CREATE PROCEDURE proc_insertPublicacion
	-- Add the parameters for the stored procedure here
	@texto varchar(150),
	@id_muro tinyint,
	@id_usuario varchar(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	insert into Publicacion(texto,id_muro,id_usuario, fecha_publicacion)
	values	(@texto, @id_muro, @id_usuario, CONVERT(VARCHAR(10), GETDATE(), 103) + ' '  + convert(VARCHAR(8), GETDATE(), 14))
END
GO

IF OBJECT_ID ('proc_deletePublicacion') IS NOT NULL
   DROP PROCEDURE proc_deletePublicacion
GO
/*Actualiza el atributo 'elimnado=1' a una publicacion de un determinado muro*/
CREATE PROCEDURE proc_deletePublicacion
	-- Add the parameters for the stored procedure here
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update Publicacion
	set eliminado = 1
	where id_publicacion = @id_publicacion
END
GO

IF OBJECT_ID ('proc_updatePublicacion_EnableDisable') IS NOT NULL
   DROP PROCEDURE proc_updatePublicacion_EnableDisable
GO
/*Habilita o deshabilita una publicacion de un determinado muro*/
CREATE PROCEDURE proc_updatePublicacion_EnableDisable
	-- Add the parameters for the stored procedure here
	@id_publicacion int,
	@habilitado bit
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update Publicacion
	set habilitado = @habilitado
	where id_publicacion = @id_publicacion
END
GO

IF OBJECT_ID ('proc_getAllUsersByPublication_Like') IS NOT NULL
   DROP PROCEDURE proc_getAllUsersByPublication_Like
GO
/*Retorna los usuarios que dieron 'Me gusta' en una determinada publicacion*/
CREATE PROCEDURE proc_getAllUsersByPublication_Like
	-- Add the parameters for the stored procedure here
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select l.id_usuario, p.nombre, p.apellido
	from [Like] l join AccesoUsuario au
	on au.id_usuario = l.id_usuario
	join Persona p
	on p.dni_persona = au.dni_persona
	where l.id_publicacion = @id_publicacion
END
GO

IF OBJECT_ID ('proc_getUserLike') IS NOT NULL
   DROP PROCEDURE proc_getUserLike
GO
/*Retorna el usuario que dio 'Me gusta' en una determinada publicacion*/
CREATE PROCEDURE proc_getUserLike
	-- Add the parameters for the stored procedure here
	@id_usuario varchar(10)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select l.id_publicacion
	from [Like] l join AccesoUsuario au
	on au.id_usuario = l.id_usuario
	join Persona p
	on p.dni_persona = au.dni_persona
	where l.id_usuario = @id_usuario
END
GO

IF OBJECT_ID ('proc_insertLike') IS NOT NULL
   DROP PROCEDURE proc_insertLike
GO
/*Inserta un like a una determinada publicacion*/
CREATE PROCEDURE proc_insertLike
	-- Add the parameters for the stored procedure here
	@id_usuario varchar(10),
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	if exists(select *
			from Publicacion p
			where p.id_publicacion = @id_publicacion)
		insert into [dbo].[Like](id_usuario, id_publicacion)
		values (@id_usuario, @id_publicacion)
	else 
		raiserror ('Error, no se pudo insertar el Like', 16, 1)
END
GO

IF OBJECT_ID ('trigger_Like') IS NOT NULL
   DROP TRIGGER trigger_Like
GO
/**Este trigger se dispara cuando se hace una insercion en la tabla Like, sumando 1 a la variable 'like' de Publicacion*/
CREATE TRIGGER dbo.trigger_Like
   ON  [Like]
   AFTER INSERT
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;
	
	-- Insert statements for trigger here
	declare @id_publicacion  int 
	select @id_publicacion = i.id_publicacion from inserted i

	if COUNT(@id_publicacion) > 0
		exec proc_updateLikePublicacion @id_publicacion
	else
	  begin
		raiserror ('Error, no pudo actualizarse el "like" de Publicacion', 16, 1)
		rollback transaction
	  end

END
GO

IF OBJECT_ID ('proc_updateLikePublicacion') IS NOT NULL
   DROP PROCEDURE proc_updateLikePublicacion
GO
/*Actualiza el atributo 'like += 1' a una publicacion de un determinado muro*/
CREATE PROCEDURE proc_updateLikePublicacion
	-- Add the parameters for the stored procedure here
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update Publicacion
	set likes += 1
	where id_publicacion = @id_publicacion
END
GO

IF OBJECT_ID ('proc_getAllCommentsByPublication') IS NOT NULL
   DROP PROCEDURE proc_getAllCommentsByPublication
GO
/*Retorna todos los comentarios de una publicacion*/
CREATE PROCEDURE proc_getAllCommentsByPublication
	-- Add the parameters for the stored procedure here
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	select c.id_comentario, c.texto, c.id_publicacion, p.nombre, p.apellido, c.fecha_creacion
	from Comentario c
	join AccesoUsuario au on au.id_usuario = c.id_usuario
	join Persona p on p.dni_persona = au.dni_persona
	where c.id_publicacion = @id_publicacion
	and c.habilitado = 1
	and c.eliminado = 0
END
GO

IF OBJECT_ID ('proc_getCommentsPublicationAfterDate') IS NOT NULL
   DROP PROCEDURE proc_getCommentsPublicationAfterDate
GO
/*Retorna los comentarios despues de la fecha*/
CREATE PROCEDURE proc_getCommentsPublicationAfterDate
	-- Add the parameters for the stored procedure here
	@id_publicacion int,
	@fecha varchar(25)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

	-- Insert statements for procedure here
	select c.id_comentario, c.texto, c.id_publicacion, p.nombre, p.apellido, c.fecha_creacion
	from Comentario c
	join AccesoUsuario au on au.id_usuario = c.id_usuario
	join Persona p on p.dni_persona = au.dni_persona
	where c.id_publicacion = @id_publicacion
	and c.habilitado = 1
	and c.eliminado = 0
	and c.fecha_creacion > @fecha
END
GO

IF OBJECT_ID ('proc_insertCommentInPublication') IS NOT NULL
   DROP PROCEDURE proc_insertCommentInPublication
GO
/*Inserta un comentario a una determinada publicacion*/
CREATE PROCEDURE proc_insertCommentInPublication
	-- Add the parameters for the stored procedure here
	@texto varchar(150),
	@id_usuario varchar(10),
	@id_publicacion int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	insert into Comentario(texto,id_usuario,id_publicacion, fecha_creacion)
	values	(@texto, @id_usuario, @id_publicacion, CONVERT(VARCHAR(10), GETDATE(), 103) + ' '  + convert(VARCHAR(8), GETDATE(), 14))
END
GO

IF OBJECT_ID ('proc_deleteComment') IS NOT NULL
   DROP PROCEDURE proc_deleteComment
GO
/*Actualiza el atributo 'eliminado = 1' de un comentario */
CREATE PROCEDURE proc_deleteComment
	-- Add the parameters for the stored procedure here
	@id_comentario int
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	update Comentario
	set eliminado = 1
	where id_comentario = @id_comentario
END
GO

IF OBJECT_ID ('proc_insertSharePublication') IS NOT NULL
   DROP PROCEDURE proc_insertSharePublication
GO
/*Inserta datos de la publicacion compartida en facebook o twitter*/
CREATE PROCEDURE proc_insertSharePublication
	-- Add the parameters for the stored procedure here
	@id_publicacion int,
	@id_usuario_comparte varchar(10),
	@texto varchar(150),
	@destino char(1)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT OFF;

    -- Insert statements for procedure here
	insert into Compartir(id_publicacion, id_usuario_comparte, texto, destino)
	values	(@id_publicacion, @id_usuario_comparte, @texto, @destino)
END
GO
