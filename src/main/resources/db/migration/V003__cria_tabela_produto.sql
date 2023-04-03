CREATE TABLE tbl_produto (
    id_produto BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome_produto VARCHAR(255) NOT NULL, 
    desc_produto TEXT NOT NULL,
    qtd_produto INT NOT NULL, 
    dt_cadastro_prod DATETIME NOT NULL, 
    dt_vencimento_prod DATETIME DEFAULT NULL, 
    usuario_id BIGINT not null,
    departamento_id BIGINT not null,
    ativo BOOLEAN DEFAULT NULL,
    valor_produto DECIMAL(19,2) NOT NULL
)engine=InnoDB DEFAULT CHARSET=utf8;

alter table tbl_produto add constraint FKcccmjvm9ytuxbe00h3wmtm77y foreign key (usuario_id) references tbl_usuario (id_usuario);
alter table tbl_produto add constraint FKlywk2iw1c6xydso8skig70m0v foreign key (departamento_id) references tbl_departamento (id_departamento);