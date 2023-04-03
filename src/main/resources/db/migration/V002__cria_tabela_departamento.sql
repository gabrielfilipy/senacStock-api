CREATE TABLE tbl_departamento (
    id_departamento BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome_departamento VARCHAR(255) NOT NULL, 
    descricao_departamento TEXT NOT NULL
)engine=InnoDB DEFAULT CHARSET=utf8;