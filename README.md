# 🚗 Documentação do Aplicativo Mobiauto

## 📌 Introdução
O **Mobiauto** é um aplicativo projetado para facilitar a compra e venda de veículos de maneira intuitiva e eficiente. Ele conecta compradores e vendedores, permitindo uma experiência de negócio segura e ágil.

## 🚀 Funcionalidades Principais

- Sistema robusto de cadastro, autenticação e gerenciamento de senhas
- Gerenciamento de permissões para o cadastro de novos usuários e revendas
- Direcionamento inteligente de oportunidades

## 🔧 Tecnologias Utilizadas

### 🖥️ Backend
- **Spring Boot 3**
- **JPA/Hibernate** para persistência de dados
- **Flyway** para gerenciamento de migração do banco de dados
- **JWT** para autenticação segura
- **TestContainers** para testagem em um ambiente mais próximo à realidade
- **ParameterizedTest** para rápida implementação de casos de uso
- Listagens com filtro e paginação.

### 🏗 Arquitetura do Backend
A arquitetura do backend segue o padrão de Arquitetura em Camadas (Layered Architecture), garantindo separação de responsabilidades e organização do código. Ela é composta pelos seguintes componentes:

1. Model – Representa os dados da aplicação.
2. Repository – Responsável pela persistência e recuperação de dados do banco.
3. Service – Contém a lógica da aplicação e intermedia a comunicação entre Repositories e Controllers.
4. Controller – Expõe as APIs REST e recebe as requisições do frontend ou de clientes externos.

## 🛠️ Configuração e Instalação

### 🎯 Requisitos
- Java 21
- Docker

### 🚧 Deploy Backend
```sh
# Clonar repositório
$ git clone https://github.com/VictorHugoLeme/mobiauto-backend-202502.git
$ cd mobiauto-backend-202502
# Compilar aplicação
$ mvn clean package
# Subir aplicação e banco de dados com docker
$ docker compose up -d --build
```

## 🖱️ Utilização

### 🐣Primeiro contato
- A aplicação sobe com um usuário padrão com o cargo ```ADMINISTRADOR``` criado.
- Você pode cadastrar uma nova senha realizando uma requisição ```POST``` para o endpoint ```/v1/auth/recover-password```, contendo o seguinte body:
```JSON
{ "email": "user1@example.com" }
```
- A resposta conterá um token, que pode ser utilizado no endpoint ```/v1/auth/reset-password```, realizando uma requisição ```POST``` com o body:
```JSON
{
  "token": "<token-aqui>", 
  "newPassword": "<nova-senha>"
}
```
- Feito isso, basta realizar o login no endpoint ```/v1/auth/login``` com uma requisição ```POST``` e o body:
```JSON
{
  "login": "user1@example.com", 
  "password": "<nova-senha>"
}
```
- Se estiver utilizando a collection do Postman fornecida, o token JWT será automaticamente salvo e suas outras requisições já serão autenticadas.

### 🛒 Gerenciando Revendas

- A apenas um requisição de distância, você pode criar, buscar, alterar e deletar revendas.
- Para criar uma nova Revenda, realize uma requisição ```POST``` no endpoint ```/v1/revenda```, com um body similar a:
```JSON
{
  "cnpj": "36037402000108",
  "nomeSocial": "Revenda Número 1"
}
```

> [!TIP]  
> Endpoints de cadastro recebem no header de resposta ```Location``` o caminho para o recurso criado.

### 👤 Gerenciando Usuários

- Criada a Revenda, você agora pode criar e associar usuários a essa revenda.
- Realize uma requisição ```POST``` para o endpoint ```/v1/usuario``` com um body parecido com:
```JSON
{
  "nome": "assis3_rev3",
  "email": "assis3_rev3@gmail.com",
  "cargo": "ASSISTENTE",
  "revendaId": 3,
  "generatePassword": true
}
```
- Definindo ```generatePassword``` como *true*, você pula a etapa de cadastro de nova senha para este usuário. O token não será enviado, e a conta estará pronta para uso.
- *Esta funcionalidade está presente apenas para fins práticos*

### 💥 Gerenciando Oportunidades

- Com pelo menos um ```ASSISTENTE``` cadastrado, você pode começar a cadastrar Oportunidades.
- As Oportunidades são distribuídas entre os assistentes, ordenando por quem tem menos oportunidades associadas. Se o número for igual entre eles, é escolhido o que está a mais tempo sem receber uma oportunidade.
> [!WARNING]  
> A regra de negócio que diz respeito ao status da Oportunidade não ficou clara.
> - Cada oportunidade possui status que pode ser novo, em atendimento e concluído. O
status inicial é novo e quando concluída, deve-se informar um moƟvo de conclusão.
>
> Não diz ao certo em que momento uma Oportunidade deixaria de ser *NOVO* para estar *EM_ATENDIMENTO* 
> Assumi que uma Oportunidade estaria *EM_ATENDIMENTO* assim que fosse atribuída a um Usuário.
> 
> Pensei em separar os Status e permitir que o Usuário atribuído iniciasse o atendimento, mas isso entraria em conflito com esta outra regra de negócio:
> - O sistema deve ter a inteligência de distribuir as oportunidades sem responsável para
    os assistentes da loja em forma de fila, onde o próximo a receber seja o que possui a
    menor quantidade de **oportunidades em andamento** e maior tempo sem receber
    uma oportunidade.
> Desta forma, o sistema ignoraria as oportunidades *NOVA*s já atribuídas a este Usuário, podendo sobrecarregar um Usuário que teria menos Oportunidades *EM_ATENDIMENTO*.
> 
> Se este era o comportamento esperado, peço perdão. Mas achei melhor manter simples e pontuar aqui esta dúvida. 

- Para cadastrar uma oportunidade, basta realizar uma requisição ```POST``` no endpoint ```/v1/oportunidade```, com um body no seguinte formato:
```JSON
{
  "clienteNome": "Cliente 1",   
  "clienteEmail": "cliente1@example.com", 
  "clienteTelefone": "xx 1234 5678", 
  "veiculoMarca": "Chevrolet", 
  "veiculoModelo": "Cobalt", 
  "veiculoVersao": "LT", 
  "veiculoAno": "2015", 
  "revendaId": "<id-da-revenda-cadastrada>"
}
```

- Administradores, proprietários e gerentes da Revenda também podem transferir Oportunidades de um usuário para outro.
Para isso, realize uma requisição ```PUT``` para o endpoint ```/v1/oportunidade/transfer/<id-da-oportunidade>```, com as seguintes informações no body:
```JSON
{
  "id": 1, 
  "newResponsavelId": 1
}
```

- O usuário com a Oportunidade atribuída pode finalizá-la, realizando uma requisição ```PUT``` no endpoint ```/v1/oportunidade/finish/<id-da-oportunidade>```, contendo id e motivo no body:
```JSON
{
  "id": 1, 
  "motivoConclusao": "Fi-lo porque qui-lo"
}
```

### 📜 Listagem de objetos.

- Esta aplicação conta com listagem e paginação utilizando Specifications.
- Implementei cada Specification para possibilitar filtragem por todos ou quase todos os campos presentes em cada entidade.
- Oportunidades, por exemplo, podem ser filtradas pelos seguintes campos:
```JAVA
private String clienteNome;
private String clienteEmail;
private String clienteTelefone;
private String veiculoMarca;
private String veiculoModelo;
private String veiculoVersao;
private String veiculoAno;
private StatusOportunidade status;
private Long responsavelId;
private LocalDateTime conclusaoBefore;
private LocalDateTime conclusaoAfter;
private LocalDateTime atribuicaoBefore;
private LocalDateTime atribuicaoAfter;
private Long revendaId;
```
- Estes campos devem ser passados via ```QueryParameters``` ao realizar uma requisição ```GET``` para o endpoint ```/v1/oportunidade```
- Se estiver utilizando a Collection do Postman fornecida, os parâmetros já estarão presentes, faltando apenas preenchimento.

- Campos como *clienteNome* contam com especificação branda, utilizando o método ```fieldLike``` da classe ```SpecificationUtils```.

---

## 🕵️ Testes
- A Cobertura de testes está muito abaixo do ideal. Devido ao tempo que havia disponível para trabalhar neste teste, apliquei um teste parametrizado a apenas ao método de criação de Revendas.
- Acredito que as partes mais críticas e que eu gostaria de ter tido mais tempo para testar consiste na autorização e na distribuição de oportunidades.
Porém, são features que foram testadas manualmente durante seus desenvolvimentos.

---

📢 **Dúvidas ou Sugestões?** Entre em contato pelo [victorhleme.dev@gmail.com](mailto:victorhleme.dev@gmail.com).