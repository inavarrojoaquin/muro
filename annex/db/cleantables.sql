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