CREATE TABLE tbl_retirada_produto (
	id_retirada_pro BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    retirada_id BIGINT not null,
    produto_id BIGINT not null
)engine=InnoDB DEFAULT CHARSET=utf8;

alter table tbl_retirada_produto add constraint FK4cccmjvm9ytuxbe00h3wmtm77y foreign key (retirada_id) references tbl_retirada (id_retirada);
alter table tbl_retirada_produto add constraint FK5cccmjvm9ytuxbe00h3wmtm77y foreign key (produto_id) references tbl_produto (id_produto);