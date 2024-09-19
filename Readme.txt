Delivery API
A Delivery API � uma aplica��o backend desenvolvida em Java com Spring Boot, projetada para gerenciar pedidos e produtos em um sistema de delivery. A API permite adicionar e remover produtos de pedidos, listar itens de pedidos e atualizar quantidades de produtos, facilitando o gerenciamento eficiente de um sistema de pedidos online.

Tecnologias Utilizadas
Java 17: Linguagem de programa��o.
Spring Boot 3.2.4: Framework para desenvolvimento de aplica��es Java.
JPA/Hibernate: ORM para mapeamento objeto-relacional.
PostgreSQL: Sistema de gerenciamento de banco de dados relacional.
Funcionalidades
Gerenciamento de Pedidos:

Criar, listar, atualizar e remover produtos de pedidos.
Atualizar a quantidade de produtos em um pedido.
Buscar detalhes de um pedido espec�fico.
Gerenciamento de Produtos:

Adicionar, atualizar, listar e remover produtos.
Configura��o
Configura��o do Banco de Dados
Certifique-se de ter um banco de dados PostgreSQL em funcionamento e configure as seguintes propriedades no arquivo application.properties ou application.yml:

 

Depend�ncias
Adicione as seguintes depend�ncias no seu arquivo build.gradle:

 

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
Se voc� deseja contribuir para o projeto, siga estas etapas:

Fa�a um fork do reposit�rio.
Crie uma nova branch (git checkout -b feature/nova-funcionalidade).
Fa�a suas altera��es e commit (git commit -am 'Adiciona nova funcionalidade').
Envie a branch para o reposit�rio remoto (git push origin feature/nova-funcionalidade).
Crie um Pull Request no GitHub para revis�o.
Licen�a
Este projeto est� licenciado sob a Licen�a MIT. Veja o arquivo LICENSE para mais detalhes.

