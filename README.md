# BackendTarefa7
Repositório do backend da tarefa 7

## Cabeçalho JSON para requisições com exemplos.
### Cadastro de usuário:
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
