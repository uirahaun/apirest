# apirest
API Rest - CRUD Cliente

Projeto Cadastro Cliente - CRUD API REST

## Tecnologias Utilizadas
- Spring boot
- JPA
- Hibernate
- Maven
- Rest Assure
- JUnit
- HSQLDB

# Detalhes do Projeto
O Projeto consiste em uma aplicação REST para cadastro de Cliente (CRUD)
Abaixo seguem os métodos HTTP utilizados para cada situação.

- Cadastro > POST
- Alteração > PUT
- Remoção > DELETE
- Buscar por ID > GET
- Buscar todos > GET

# Descrição detalhada das funcionalidades

### Cadastro do cliente
\[`POST`\] `/cliente`

Corpo: JSON com dados do cliente a serem cadastrados

    Atributos:
        - nome: string
        - email: string
        - dataDeNascimento: date

Exemplo de corpo
```json
{
    "nome": "José",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

Resposta: JSON com dados do cliente acrescido do id (atributo identificador)

    Código da resposta: 201
```json
{
    "id": 1,
    "nome": "José",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

### Listagem do cliente
\[`GET`\] `/cliente?limite={numero}&pagina={numero}`

    Parametros Opcionais:
        - limite: valor que define quantidade maxima a ser retornada
            caso não seja enviado na requisição, usar valor padrao igual a 10
        - pagina: paginação da listagem baseado no total
            caso não seja enviado na requisição, usar valor padrao igual a 1

Corpo: _vazio_

Resposta: lista com objetos cadastrados na base limitados pelo parametro `limite` acrescido com o total de dados existente na base (usado para paginação do frontend)

Exemplo de resposta de requisição com `limite` igual a 1

    Código da resposta: 200
```json
{
    "total": 2039,
    "lista": [
        {
            "id": 1,
            "nome": "José",
            "email": "jose@email.com",
            "dataDeNascimento": "01/01/2000"
        }
    ]
}
```

### Detalhamento do cliente
\[`GET`\] `/cliente/{id}`

    Parametro Obrigatório:
        - id: identificador do cliente a ser retornado

Corpo: _vazio_

Resposta: objeto com dados do cliente

Exemplo de resposta da requisição

    Código: 200
```json
{
    "id": 1,
    "nome": "José",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

### Alteração do cliente
\[`PUT`\] `/cliente/{id}`

    Parametro obrigatório:
    - id: identificador do cliente a ser atualizado

Corpo: dados a serem atualizados do cliente

Exemplo do corpo
```json
{
    "nome": "José da Silva"
}
```

Resposta: objeto com dados do cliente

Exemplo da resposta

    Código da resposta: 200
```json
{
    "id": 1,
    "nome": "José da Silva",
    "email": "jose@email.com",
    "dataDeNascimento": "01/01/2000"
}
```

### Remoção do cliente
\[`DELETE`\] `/cliente/{id}`

    Parametro obrigatório:
        - id: identificador do cliente a ser removido

Corpo: _vazio_

Resposta: _vazio_

    Código da resposta: 200

# Testes Realizados
1) Testes na camada Repository
2) Testes na camada Service
3) Testes nos métodos HTTP (GET, PUT, POST)
