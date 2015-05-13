use muro
go

-- Creates the login AbolrousHazem with password '340$Uuxwp7Mcxo7Khy'.
CREATE LOGIN desarrollador
    WITH PASSWORD = 'intel123!';
GO

-- Creates a database user for the login created above.
CREATE USER desarrollador FOR LOGIN desarrollador;
GO