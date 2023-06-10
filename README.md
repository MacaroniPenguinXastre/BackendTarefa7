# BackendTarefa7
Repositório do backend da tarefa 7



## Como fazer requisições para.

As requisições podem ser feitas com JSON, sendo assim é necessário que no cabeçalho (ou Header) 
da requisição seja: Content-Type: application/json.
Além disso, é necessário que o mesmo coloque no body o JSON que a API requisita.
Não é necessário sempre trafegar todos os dados de todas as colunas, podendo enviar apenas as informações requisitadas de 
cada endpoint.

IMPORTANTE: Mesmo que não passe todas os campos, é no mínimo NECESSÁRIO que passe o ID no corpo da requisição. O ÚNICO que foge a regra, são as requisições que começam com o caminho: 
```/public```
A seguir, serão listados como fazer a interação com a API. Os métodos são HTTP, mostrando o tipo de requisição HTTP, o endpoint e o JSON.

OBSERVAÇÃO: As vezes, serão usados DTO. Eles são úteis por vários motivos, como segurança, deixa o código mais modular e fácil de se ler.
Além disso, ele ajuda a contornar algumas pequenas "limitações" do Spring Boot 3.0, como por exemplo, não é possível em um endpoint pedir mais de um parâmetro no body, mas criando um único objeto que "abriga" outros, torna possível passar "múltiplos parâmetros". 

### JSON das entidades com exemplos
São importantes caso alguma requisição exija ou para receber as informações solicitadas.

IMPORTANTE: Os cargos disponíveis são: 

- ALUNO
- ADM
- MENTOR
- EMPRESA_PARCEIRA

Cargos precisam estar EXATAMENTE do mesmo formato acima.

- USER
```
{
  "id": 1,
  "nome": "Nome do Usuário",
  "email": "user@example.com",
  "senha": "senha",
  "cargo": "CARGO_DO_USUARIO"
}
```
- TREINAMENTO
```
{
  "id": 1,
  "cargaHorariaTotal": 40,
  "nomeComercial": "Treinamento ABC",
  "descricao": "Um treinamento para aprimorar habilidades técnicas",
  "dataInicioInscricao": "2022-01-01T00:00:00Z",
  "dataFimInscricao": "2022-01-31T23:59:59Z",
  "dataInicioTreinamento": "2022-02-01T00:00:00Z",
  "dataFimTreinamento": "2022-02-28T23:59:59Z",
  "quantidadeMinima": 10,
  "quantidadeMaxima": 50
}
```
- PERGUNTA
```
{
  "id": 1,
  "enunciado": "Qual é a capital do Brasil?",
  "alternativaA": "São Paulo",
  "alternativaB": "Brasília",
  "alternativaC": "Rio de Janeiro",
  "alternativaD": "Belo Horizonte",
  "alternativaCorreta": "B",
  "admCriador": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "********",
    "cargo": "ADM"
  },
  "quizAssociados": [
    {
      "id": 1,
      "nome": "Quiz 1"
    },
    {
      "id": 2,
      "nome": "Quiz 2"
    }
  ]
}
```

- QUIZ
```
{
  "id": 1,
  "perguntas": [
    {
      "id": 1,
      "enunciado": "Qual é a capital do Brasil?",
      "alternativaA": "São Paulo",
      "alternativaB": "Brasília",
      "alternativaC": "Rio de Janeiro",
      "alternativaD": "Belo Horizonte",
      "alternativaCorreta": "B",
      "admCriador": {
        "id": 1,
        "nome": "João Silva",
        "email": "joao@example.com",
        "senha": "********",
        "cargo": "ADM"
      },
      "quizAssociados": null
    },
    {
      "id": 2,
      "enunciado": "Qual é a maior cidade do mundo?",
      "alternativaA": "São Paulo",
      "alternativaB": "Tóquio",
      "alternativaC": "Nova Iorque",
      "alternativaD": "Cidade do México",
      "alternativaCorreta": "B",
      "admCriador": {
        "id": 1,
        "nome": "João Silva",
        "email": "joao@example.com",
        "senha": "********",
        "cargo": "ADM"
      },
      "quizAssociados": null
    }
  ],
  "admCriador": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "********",
    "cargo": "ADM"
  },
  "treinamentosQuiz": []
}
```
- CURSO
```
{
  "id": 1,
  "titulo": "Introdução à Programação",
  "descricao": "Curso básico de programação para iniciantes",
  "admCriador": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "********",
    "cargo": "ADM"
  },
  "materialDidatico": "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
  "treinamentosCurso": []
}
```

### Cadastro de usuário:
MÉTODO: POST

ENDPOINT: /public/register
```
{
    "nome": "",
    "cargo": "",
    "senha": "",
    "email": ""
}

{
    "nome": "Crepaldi",
    "cargo": "ALUNO",
    "senha": "12345",
    "email": "marcio@email.com"
}
```

Observação: a senha é automaticamente encriptada ao ser recebida no cadastro.
### Login do usuário

MÉTODO: POST

ENDPOINT: /public/login

Body do Request:
```
{
    "email" : "",
    "password" : ""
}

{
    "email" : "marcio@email.com",
    "password" : "12345"
}
```

Response:

Caso seja bem sucedida:

- Status Code: 200
- Body:
```
{
    "nome": "Crepaldi",
    "cargo": "ALUNO",
    "senha": "12345",
    "email": "marcio@email.com"
}
```
Caso falhe:
- Status Code: 400 ou 500

A senha não precisa ser criptografada para requisitar um login.

### Consulta de Cursos

MÉTODO: GET

ENDPOINT: /cursos

Request 

Body: USER
```
{
    "nome": "",
    "cargo": "",
    "senha": "",
    "email": ""
}

```

Response

- Caso cargo de User não seja ADM:

Status Code: 403

- Caso parâmetro tenha sido passado incorretamente:

Status Code: 400 ou 500

- Caso tenha sido feito com sucesso:

Status Code: 200

Body: CURSO
```
{
  "id": 1,
  "titulo": "Introdução à Programação",
  "descricao": "Curso básico de programação para iniciantes",
  "admCriador": {
    "id": 1,
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "********",
    "cargo": "ADM"
  },
  "materialDidatico": "Lorem ipsum dolor sit amet, consectetur adipiscing elit...",
  "treinamentosCurso": []
}
```
