CREATE TABLE tbl_retirada (
    id_retirada BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    usuario_id BIGINT not null,
    dt_retirada DATETIME DEFAULT NULL, 
    total INT not null
)engine=InnoDB DEFAULT CHARSET=utf8;

alter table tbl_retirada add constraint FK3cccmjvm9ytuxbe00h3wmtm77y foreign key (usuario_id) references tbl_usuario (id_usuario);