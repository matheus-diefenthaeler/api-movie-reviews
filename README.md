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

- 1º Passo é preciso realizar o cadastro do usuario 
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
