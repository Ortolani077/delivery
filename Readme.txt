Delivery API
A Delivery API é uma aplicação backend desenvolvida em Java com Spring Boot, projetada para gerenciar pedidos e produtos em um sistema de delivery. A API permite adicionar e remover produtos de pedidos, listar itens de pedidos e atualizar quantidades de produtos, facilitando o gerenciamento eficiente de um sistema de pedidos online.

Tecnologias Utilizadas
Java 17: Linguagem de programação.
Spring Boot 3.2.4: Framework para desenvolvimento de aplicações Java.
JPA/Hibernate: ORM para mapeamento objeto-relacional.
PostgreSQL: Sistema de gerenciamento de banco de dados relacional.
Funcionalidades
Gerenciamento de Pedidos:

Criar, listar, atualizar e remover produtos de pedidos.
Atualizar a quantidade de produtos em um pedido.
Buscar detalhes de um pedido específico.
Gerenciamento de Produtos:

Adicionar, atualizar, listar e remover produtos.
Configuração
Configuração do Banco de Dados
Certifique-se de ter um banco de dados PostgreSQL em funcionamento e configure as seguintes propriedades no arquivo application.properties ou application.yml:

 

Dependências
Adicione as seguintes dependências no seu arquivo build.gradle:

 

Endpoints
Pedidos
Criar um Pedido

POST /pedidos/criar
Body:
 
Remover Produto do Pedido

DELETE /pedidos/{pedidoId}/produtos/{produtoId}
Atualizar Quantidade de Produto

PUT /pedidos/{pedidoId}/produtos/{produtoId}
Body:
 
Listar Produtos do Pedido

GET /pedidos/{pedidoId}/produtos
Buscar Pedido por ID

GET /pedidos/{pedidoId}
Produtos
Listar Produtos

GET /produtos
Adicionar um Novo Produto

POST /produtos
Body:
 

Atualizar Produto

PUT /produtos/{produtoId}
Body:

 
Remover Produto

DELETE /produtos/{produtoId}



Exemplos de Uso
Criar um Pedido

 
Listar Produtos do Pedido

 
Contribuindo
Se você deseja contribuir para o projeto, siga estas etapas:

Faça um fork do repositório.
Crie uma nova branch (git checkout -b feature/nova-funcionalidade).
Faça suas alterações e commit (git commit -am 'Adiciona nova funcionalidade').
Envie a branch para o repositório remoto (git push origin feature/nova-funcionalidade).
Crie um Pull Request no GitHub para revisão.
Licença
Este projeto está licenciado sob a Licença MIT. Veja o arquivo LICENSE para mais detalhes.

