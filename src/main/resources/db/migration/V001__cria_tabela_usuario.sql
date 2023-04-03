CREATE TABLE tbl_usuario (
    id_usuario BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    matricula_usuario VARCHAR(50) DEFAULT NULL, 
    email_usuario VARCHAR(100) NOT NULL, 
    senha_usuario VARCHAR(10) DEFAULT NULL, 
    nome_usuario VARCHAR(255) NOT NULL, 
    ativo BOOLEAN DEFAULT NULL
)engine=InnoDB DEFAULT CHARSET=utf8;