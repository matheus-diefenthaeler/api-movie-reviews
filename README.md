# CHALLENGE - Sistema de críticas de filmes
### PARTE 1 - Sistema e regras de negócio
Crie um api onde um usuário poderá se cadastrar. Cada usuário terá um perfil na plataforma, sendo eles: LEITOR, BÁSICO, AVANÇADO, e MODERADOR. Todo usuário deve começar como LEITOR e poderá ir avançando de perfil conforme a interação com a plataforma.

- LEITOR: Após o cadastro, esse usuário poderá logar e buscar por um filme. Ele poderá ver as informações de um filme, comentários e dar uma nota para o filme. A cada filme que o usuário avaliar, ele ganha 1 ponto em seu perfil.
- BÁSICO: O usuário leitor poderá se tornar BÁSICO quando adquirir 20 pontos. Nesse perfil será possível postar comentários, notas e responder comentários. Cada resposta que o usuário enviar, ele ganha 1 ponto.
- AVANÇADO: O usuário básico poderá se tornar AVANÇADO quando adquirir 100 pontos. Esse perfil tem as capacidades do BÁSICO, e mais citar outros comentários (comentários feitos por outros usuários) e marcar comentários como “gostei” ou "não gostei”.
- MODERADOR: Um usuário poderá se tornar MODERADOR de 2 formas: um moderador torna outro usuário moderador ou por pontuação, para se tornar MODERADOR o usuário deverá ter 1000 pontos. O moderador tem as capacidades do AVANÇADO, e mais excluir um comentário ou marcar como repetida.

A busca pelo filme na sua api deve ser feita consultando uma api pública chamada OMDb API (https://www.omdbapi.com/) e os comentários e notas devem ser salvos no seu sistema.

### PARTE 2 - Segurança
A segurança de um sistema é um dos pontos mais importantes que precisamos ter para garantir uma maior confiabilidade para os nossos clientes e para o nosso próprio projeto. Quando falamos de segurança em um projeto, podemos ir além de funcionalidades básicas que linguagens e frameworks nos entregam. Podemos pensar, arquitetar e desenvolver soluções totalmente voltadas para promover uma maior segurança.

Com base nisso, neste desafio você deve criar uma nova API de autorização de login.

Para que um usuário cadastrado possa realizar as operações no seu sistema, ele deve se autenticar. Com isso, crie uma nova API que ficará somente responsável de autenticar esse usuário.

- Quando sua API de críticas receber uma requisição de login com os dados do usuário de login e senha, ela deverá fazer uma requisição para a API de autenticação passando as informações de login e senha.

- Sua API de autenticação deverá fazer a validação daquele login e senha estão corretos. Caso esteja, deverá ser gerado um token e devolvido para a API de críticas, que devolverá para o usuário.

- Caso o login e senha estiverem errado, sua API de autenticação deverá salvar um cache com uma tentativa de login e a cada nova tentativa de login errada esse cache deve ser atualizado.

## API movie-reviews (Parte 1)

- Para acessar o console do H2 da api-movie-reviews, utilize o caminho: http://localhost:8080/h2-console e, no campo `JDBC URL:` informe o valor `jdbc:h2:./testdb`
- Utilize a colection e o enviroment disponibilizados no link: https://drive.google.com/file/d/1h4-ryIfz_iMl_haKYCTA3CqhC6aCWsjJ/view?usp=sharing para fazer as requisiçoes, 
## API authorization-token (Parte 2)
- link para api authorization-login
https://github.com/matheus-diefenthaeler/authorization-token
- Para acessar o console do H2 da api authorization-token, utilize o caminho: http://localhost:8081/h2-console e, no campo `JDBC URL:` informe o valor `jdbc:h2:tcp://localhost:9090/./testdb`
# Testando a aplicacao
## Observações:
- Execute a primeiro a api-movie-reviews e somente após execute api authorization-token!

## Passo a passo dos testes utilizando a collection disponibilizada no inicio do readme.

- 1º Passo realize o cadastro do usuario, através da subpasta `Register` na request `Register user`, passando como body o exemplo abaixo
  * Endpoint `POST localhost:8080/registration` 
```JSON
{
    "email": "matheus@teste.com",
    "password": "123123",
}
```

- Resultado esperado: Status: `201 CREATED`
```JSON
{
    "id": 1,
    "email": "matheus@teste.com",
    "password": "123123",
    "score": 0,
    "profile": "LEITOR"
}
```

- 2º Passo efetue o login do usuario cadastrado, através da subpasta `Authorization - Token` na request `Authenticate`, passando como `key` de nome `user` o email cadastrado, no exemplo acima foi utilizado `matheus@teste.com` e na segunda `key` de nome `password` informe a senha cadastrada `123123`
  * Endpoint `POST http://localhost:8081/authenticate`.
    - Resultado esperado: Status: `200 OK`
- Será printado um Token como String que deverá ser passado no `Header` de key `Authorization` em todas as demais requisiçoes, exemplo de um token abaixo:
  
Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoibW9kZXJhZG9yQHRlc3RlLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJpYXQiOjE2NTY3MDE0MDQsImV4cCI6MTY1NjcwMjAwNH0.sS4y3q-8hkK9yW7L7jGHk-eD2UkMCMtD2orMZcXomPzq3qlKRlVASIMiUPFvmg-s2pZ-1omAEFvjOGLVR-jMjw
  
![image](https://user-images.githubusercontent.com/76569275/176964349-2b6f3d45-9e95-45b3-8ff6-338c319255b1.png)

 - 3º Passo, com usuario cadastrado podemos agora enviar uma avaliaçao, para isso passe o token gerado anteriormente, no header `Authorization` na request `Rate Movie by imdbID` da pasta `Movie`.
 ![image](https://user-images.githubusercontent.com/76569275/176965056-0000949c-0a6c-4d37-81d0-1c10fac9da32.png)

 
 No body informe o id do usuario, a mensagem de avaliação, uma nota e o ID de um filme que pode ser buscado na API (https://www.omdbapi.com/), abaixo exemplo:
 * Endpoint `POST localhost:8080/movie/rate` 
  ```JSON
{
    "idUser": 1,
    "message": "Gostei do filme",
    "rate": 10,
    "imdbID": "tt0120338"
}
```
- Resultado esperado: Status: `201 CREATED`
  
```JSON
{
    "idUser": 1,
    "message": "Gostei do filme",
    "rate": 10,
    "imdbID": "tt0120338"
}
```

 - 4º Passo, com uma avaliaçao no sistema, um usuario de perfil acima de `LEITOR` é possivel fazer comentarios, para isso informe tambem o mesmo token gerado anteriormente, no header `Authorization` na request `Comment rate by idRating` da pasta `Comment`.
 ![image](https://user-images.githubusercontent.com/76569275/176965757-a7355b70-c13f-4687-8c24-b9003a77764e.png)
 
 No body informe id da avaliçao `idRating`, uma mesagem e o id do usuario `idUser`
 * Endpoint `POST localhost:8080/comment` 

```JSON 
 {
    "idRating": 1,
    "message": "Filme muito bom!",
    "idUser": 1
}
```

- Resultado esperado: Status: `201 CREATED`

```JSON 
 {
    "idRating": 1,
    "message": "Filme muito bom!",
    "idUser": 1
}
```
