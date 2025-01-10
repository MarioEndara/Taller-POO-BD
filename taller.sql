create database taller;
use taller;
create table usuariosnatacion(
	id int auto_increment primary key,
    nombre varchar(100),
    edad int,
    usuario varchar(100),
    contraseña varchar(100) unique
);
select * from usuariosnatacion;

