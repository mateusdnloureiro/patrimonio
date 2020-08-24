# Patrimonio - Web Rest Api

Implementação de uma Web API REST para o gerenciamento de patrimônios de uma empresa


#### Configurações Iniciais
- JDK Java 11.0.5 + Spring Boot + Rest
- Utilização de banco de dados PostgresSQL
- Script de criação das tabelas src/main/resources/script-ddl.sql

#### Autenticação com Token JWT
- Login via endpoint http://localhost:8080/login 
- JSON (body)
```
{
    "email":"admin@gmail.com",
    "senha":"admin123"
}
```

- Response com Token
```
{
    "id": 1,
    "nome": "Admin",
    "email": "admin@gmail.com",
    "senha": "admin123",
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJwYXRyaW1vbmlvSldUIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5ODIyNTYxMywiZXhwIjoxNTk4MjI2MjEzfQ.0Gow4UpVvNjwFCfa4e7wuGrzKLQFOk8ETQMSiqvolU5QZb929hI4q06MXhBMhjarf4VpXb7HntJHGLjC-o6fGQ"
}
```

- Incluir o Token nos Headers das próximas requisições
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJwYXRyaW1vbmlvSldUIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTU5ODIyNTYxMywiZXhwIjoxNTk4MjI2MjEzfQ.0Gow4UpVvNjwFCfa4e7wuGrzKLQFOk8ETQMSiqvolU5QZb929hI4q06MXhBMhjarf4VpXb7HntJHGLjC-o6fGQ 
```
#### Manutenção de Usuários

Endpoints
- Listagem de usuários: GET http://localhost:8080/usuarios
- Listagem de um único usuário: GET http://localhost:8080/usuarios/10
- Exclusão de um usuário: DELETE http://localhost:8080/usuarios/10
- Manutenção de usuários (Inclusão/Edição): POST http://localhost:8080/usuarios/save  (não necessita autenticação)
```
{
    "id": 1,                            (não é necessário na inclusão)    
    "nome":"Usuario Exemplo",
    "email": "usuarioexemplo@gmail.com",
    "senha": "teste123"      
}
```

#### Manutenção de Marcas

Endpoints
- Listagem de marcas: GET http://localhost:8080/marcas
- Listagem de uma única marca: GET http://localhost:8080/marcas/5
- Exclusão de uma marca: DELETE http://localhost:8080/marcas/5
- Manutenção de Marcas (Inclusão/Edição): POST http://localhost:8080/marcas/save
```
{
    "id": 5,                (não é necessário na inclusão)    
    "nome":"Marca Exemplo"      
}
```

#### Manutenção de Patrimônios

Endpoints
- Listagem de patrimonios: GET http://localhost:8080/patrimonios
- Listagem de um único patrimonio: GET http://localhost:8080/patrimonios/1
- Exclusão de um patrimonio: DELETE http://localhost:8080/patrimonios/1
- Manutenção de patrimonios (Inclusão/Edição): POST http://localhost:8080/patrimonios/save 
```
{
    "id": 1,                                (não é necessário na inclusão)    
    "nome":"Exemplo Patrimônio",
    "marcaId": 1,                           (id da marca existente no banco)
    "descricao": "Descrição do patrimônio"
    "numeroTombo": 5                        (gerado automaticamente pelo sistema)      
}
```

#### Testes (SpringBootTest + TestRestTemplate)

- PatrimonioApplicationTests: Cobertura de testes básicos sobre as funcionalidades da API Rest

- NumeroServiceTest: Validação do algoritmo de retorno do maior numero da familia dado um numero N qualquer, exemplo: dado o numero 335, o retorno deve ser 533   
