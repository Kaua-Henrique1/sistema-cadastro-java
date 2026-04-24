PRD: Sistema de Cadastro de Pets (Versão 2.0 - Refatoração REST/Hibernate)
1. Visão Geral do Projeto
   O objetivo desta etapa é evoluir o sistema de cadastro de pets de uma aplicação monolítica/local para uma arquitetura Client-Server baseada em HTTP (REST), utilizando Hibernate para a persistência de dados de forma eficiente.
2. Objetivos da Refatoração
   Desacoplamento: Separar a lógica de negócio (Backend) da interface ou do modo de acesso.
   Persistência Robusta: Substituir persistência em memória ou arquivos simples pelo uso de um Banco de Dados Relacional via Hibernate (JPA).
   Padronização: Implementar o protocolo HTTP.
3. Requisitos Funcionais (Features)
   O sistema deve permitir as operações básicas de CRUD para as entidades principais:
   Funcionalidade
   Descrição
   Cadastro de Pets
   Long id_pet, nome;endereco;Sexo sexo;TipoAnimal tipoAnimal;LocalDate dataNascimento; peso; raca;
   Consulta Avançada
   Buscar pets por raça, nome (se escrever apenas parte do nome, deve ser mostrado todos os pets que atendem o nome, Ex: “Re”, deve aparecer Pet ‘Rex’ e ‘Rexona’.) ou ID do tutor, nome, sexo, tipoAnimal, dataNascimento, peso, raca. Ao faz a consulta especidifca, deve mostrar todos pets que atendem a esse atributo.

Exportar para as Planilhas
4. Arquitetura Técnica
   Nesta nova fase, o projeto seguirá o padrão de camadas para facilitar a manutenção:
   Stack Tecnológica:
   Linguagem: Java
   Framework: Spring Boot
   Persistência: Hibernate / JPA. (Ja tenho a image do postgreSQL conectada por meio do resources
   Protocolo: HTTP/REST (JSON).
   Banco de Dados: PostgreSQL.

6. Definição de Endpoints (API)
   Para que o sistema seja acessível via HTTP, os seguintes endpoints devem ser refatorados/criados:
   GET /api/pets - Lista todos os pets.
   GET /api/pets/{‘todos os que sao requisitos especificos} - Detalhes de um pet específico.
   POST /api/pets - Cria um novo pet.
   PUT /api/pets/{id} - Atualiza os dados de um pet (segue a mesma regra do de criar um pet).
   DELETE /api/pets/{id} - Remove um pet do sistema.

7. Critérios de Aceite
   As queries ao banco devem ser feitas via HQL ou Criteria API (através do Hibernate).
   As respostas da API devem retornar códigos de status HTTP corretos (200 OK, 201 Created, 404 Not Found).
   O sistema não deve perder dados ao ser reiniciado (uso de banco persistente).


Camada
Classe
Status
Mudança Principal
Domain
Pet.java
Manter
Lógica de negócio pura, sem anotações de banco.
Infra
PetEntity.java
Refatorar
Adicionar @Entity e mapeamentos do Hibernate.
Infra
PetRepository
Refatorar
Mudar de Classe para Interface (JpaRepository).
App
PetController
Refatorar
Adicionar anotações REST e injetar o Service.
App
PetService
Refatorar
Onde fica a regra de "posso ou não cadastrar este pet".



