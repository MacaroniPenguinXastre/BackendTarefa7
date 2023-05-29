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
    "senha": "XerathZero",
    "email": "creps@hotmail.com"
}
```
Observação: a senha é automaticamente encriptada ao ser recebida no cadastro.
### Login do usuário
MÉTODO: POST
ENDPOINT: /public/login
```
{
    "email" : "",
    "senha" : ""
}

{
    "email" : "marcio@email.com",
    "senha" : "12345"
}
```
A senha não precisa ser criptografada no login.

### Cadastro de Curso
```
{
  "titulo": "Curso de Programação",
  "descricao": "Um curso abrangente de programação",
  "cargaHorariaCurso": 40,
  "mentor": {
    "id": 2
  },
  "alunos": [
    {
      "id": 1
    }
  ],
  "materialDidatico" : "Testando feature"
}
```

