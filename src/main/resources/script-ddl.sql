create table patrimonio
(
    id           serial primary key,
    nome         varchar(255) not null,
    marca_id     bigint       not null,
    descricao    varchar(500),
    numero_tombo serial not null,
    constraint patrimonio_marca_fk foreign key (marca_id) references marca (id)
);

create table marca
(
    id   serial primary key,
    nome varchar(255) not null unique
);

create table usuario
(
    id    serial primary key,
    nome  varchar(255) not null,
    email varchar(100) not null unique,
    senha varchar(50)  not null
);

insert into usuario (nome, email, senha) values ('Admin', 'admin@gmail.com', 'admin123');