# BACK END – Grupo (até 5 pessoas)

## Objetivo

Aplicar, de forma prática, os conceitos da disciplina de programação modular, com foco em:

* modularização;

* organização de código;

* persistência de dados;

* testes;

* versionamento;

* documentação de serviços.

Cada grupo deverá desenvolver seu Back-End, utilizando a linguagem e as tecnologias de sua escolha (Java, Python, Node.js, PHP etc.). O sistema deverá utilizar um banco de dados (de sua preferência) para armazenar e recuperar as informações da aplicação.

# Sistema de Simulação de Eleição Eletrônica

## Descrição do Minimundo

O sistema tem como objetivo simular uma eleição eletrônica, contemplando o gerenciamento da eleição, o cadastro dos participantes, a realização da votação e o acompanhamento dos resultados.

Existem três tipos de usuários no sistema: Chefe da Seção, Eleitor e Candidato.

O Chefe da Seção é responsável pela administração do processo eleitoral. Cabe a ele cadastrar os eleitores e os candidatos que participarão da eleição. Também é sua responsabilidade cadastrar uma eleição, informando a data, o turno, o horário de abertura e o horário de encerramento da votação.

Cada eleição poderá estar em uma das seguintes situações: AGENDADA, ABERTA ou ENCERRADA. Somente o Chefe da Seção poderá realizar a abertura e o encerramento da votação.

Os candidatos cadastrados deverão concorrer a um dos seguintes cargos:

* Deputado Estadual;

* Deputado Federal;

* Senador;

* Governador;

* Presidente da República.

Cada candidato deverá possuir um número de identificação eleitoral e estar associado a um partido e ao cargo para o qual está concorrendo.

O Eleitor deverá estar previamente cadastrado para participar da eleição. Após realizar login no sistema, poderá votar nos candidatos dos diferentes cargos. Um eleitor poderá votar uma única vez para cada cargo em uma determinada eleição e turno.

A votação somente poderá ocorrer enquanto a eleição estiver ABERTA. Depois da confirmação de um voto, ele não poderá ser alterado.

O sistema deverá preservar o sigilo do voto. Portanto, embora seja necessário controlar se determinado eleitor já votou para impedir votos duplicados, não deverá ser possível consultar posteriormente em qual candidato aquele eleitor votou.

Durante o período em que a eleição estiver aberta, o Eleitor poderá acompanhar a evolução da votação para cada um dos cargos.

O Candidato também poderá realizar login no sistema. Entretanto, seu acompanhamento será restrito ao cargo para o qual está concorrendo. Por exemplo, um candidato a Governador poderá visualizar a evolução da votação dos candidatos a Governador, mas não os resultados dos demais cargos.

Os resultados deverão ser atualizados à medida que novos votos forem registrados, permitindo acompanhar a quantidade de votos recebidos pelos candidatos durante a eleição.

Quando o Chefe da Seção encerrar a eleição, nenhum novo voto poderá ser registrado. O sistema deverá então disponibilizar o resultado final, apresentando a quantidade de votos recebidos por cada candidato e o candidato mais votado para cada cargo.

Todas as informações necessárias ao funcionamento do sistema deverão ser armazenadas em banco de dados, incluindo usuários, eleitores, candidatos, eleições e votos.

## Requisitos do Sistema

A partir do minimundo apresentado, o grupo deverá desenvolver os serviços necessários para atender às seguintes funcionalidades:

**1\. Autenticação**

O sistema deverá permitir que Chefe da Seção, Eleitores e Candidatos realizem login por meio de suas credenciais.

As funcionalidades disponíveis deverão respeitar o perfil do usuário autenticado.

**2\. Gerenciamento da Eleição**

O Chefe da Seção deverá poder:

* cadastrar uma eleição;

* definir sua data;

* definir o turno;

* definir os horários de abertura e encerramento;

* abrir a votação;

* encerrar a votação;

* consultar a situação atual da eleição.

**3\. Gerenciamento de Eleitores**

O Chefe da Seção deverá poder:

* cadastrar eleitores;

* alterar os dados de um eleitor;

* consultar eleitores;

* excluir eleitores.

**4\. Gerenciamento de Candidatos**

O Chefe da Seção deverá poder:

* cadastrar candidatos;

* alterar os dados de um candidato;

* consultar candidatos;

* excluir candidatos;

* associar cada candidato a um cargo e a uma eleição.

**5\. Votação**

O Eleitor deverá poder:

* consultar os candidatos disponíveis para cada cargo;

* registrar seu voto;

* confirmar seu voto.

O sistema deverá impedir:

* votação em uma eleição que não esteja aberta;

* voto de usuário que não seja um eleitor;

* mais de um voto do mesmo eleitor para o mesmo cargo;

* alteração de um voto já confirmado.

**6\. Acompanhamento da Votação**

O Eleitor poderá acompanhar a evolução da votação para todos os cargos.

O Candidato poderá acompanhar a evolução da votação apenas do cargo para o qual está concorrendo.

O sistema deverá apresentar a quantidade de votos recebidos por cada candidato.

**7\. Resultado da Eleição**

Após o encerramento da votação, o sistema deverá permitir a consulta dos resultados finais, apresentando:

* candidatos;

* respectivos cargos;

* quantidade de votos recebidos;

* candidato mais votado para cada cargo.

## Banco de Dados

O sistema deverá utilizar um banco de dados para persistência das informações.

A modelagem deverá contemplar as informações e os relacionamentos necessários para representar o minimundo apresentado.

A definição das tabelas ou coleções, chaves, relacionamentos e restrições de integridade será responsabilidade do grupo.

O sistema deve contemplar

* ✔ Estrutura modular clara;

* ✔ Persistência das informações em banco de dados;

* ✔ Separação das funcionalidades de acordo com o perfil do usuário;

* ✔ Implementação das regras de negócio descritas no minimundo;

* ✔ Testes unitários e testes de integração;

* ✔ Versionamento no Git, com histórico de commits coerente;

* ✔ Documentação da API utilizando Swagger UI ou similar.


  


  


  

## Entregáveis

O grupo deverá entregar:

* Link do repositório no Git;

* Link da documentação da API;

* Arquivo (PDF ou README) contendo:

  * explicação da arquitetura;

  * descrição da modularização;

  * descrição da modelagem do banco de dados.

## Durante a apresentação, o grupo deverá demonstrar

* A execução do sistema utilizando uma ferramenta de teste de serviços (ex.: Insomnia, Postman ou similar);

* O funcionamento dos diferentes perfis de usuário;

* O cadastro de eleitores e candidatos;

* O cadastro, abertura e encerramento de uma eleição;

* A realização da votação;

* O funcionamento das principais regras de negócio;

* O acompanhamento da votação;

* A consulta ao resultado final;

* Como os testes foram organizados, diferenciando testes unitários e de integração;

* A estrutura do projeto, evidenciando a organização em módulos, camadas ou componentes;

* Como a documentação da API foi gerada, utilizando Swagger UI ou ferramenta equivalente.


## Forma de Avaliação

Cada membro do grupo será arguido separadamente.

