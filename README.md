XY Inc. Rest API
================

Plataforma de serviços Rest em Java para manutenção e busca de Pontos de Interesse (POIs)

Pré-requisitos para execução local
-------------------------------------------------------
Os seguintes pacotes devem estar instalados e configurados na máquina local:
 * [Java 1.7+](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html)
 * [MySQL 5.6](http://dev.mysql.com/downloads/)
 * [Apache Maven](http://maven.apache.org/download.cgi)
 * [Apache Tomcat 7+](http://tomcat.apache.org/download-80.cgi)
 * [Eclipse Luna for Java EE Developers](https://www.eclipse.org/downloads/)

Configuração do banco de dados
-------------------------------------------------------
* No aplicativo MySQL Workbench, conecte na base de dados local, crie um novo schema chamado 'xyincdb' e defina-o como schema default.
* Execute o arquivo '[xyinc-pois.sql](https://github.com/felipeacardoso/xy-inc/blob/master/scripts/xyinc-pois.sql)' para criar a tabela de POIs e popular com os pontos de interesse de exemplo.
 
Configuração do projeto
-------------------------------------------------------
* Faça o clone do repositório ou o download dos arquivos do projeto.
* No Eclipse, importe o projeto Maven 'restapi'.
* Altere o arquivo '[hibernate.cfg.xml](https://github.com/felipeacardoso/xy-inc/blob/master/src/restapi/src/main/resources/hibernate.cfg.xml)' com as informações de conexão com o banco de dados.
* Ainda no Eclipse, configure o servidor Tomcat para executar o módulo 'restapi'.
* Execute o Maven install para baixar as dependências e gerar o pacote, e em seguida, inicie o servidor Tomcat.
 
Endpoints
-------------------------------------------------------
A plataforma de serviços possui as seguintes operações:

* Cadastrar POI
  * Endpoint: http://localhost:8080/restapi/rest/pois
  * Método: POST
  * Content-Type: application/json
  * Response-Type: application/json
  * Parâmetros: { "name" : "NOME_DO_LOCAL", "x" : COORDENADA_X, "y" : COORDENADA_Y }
  * Resultado: POI cadastrado ou mensagem de erro

* Buscar todos os POIs
  * Endpoint: http://localhost:8080/restapi/rest/pois
  * Método: GET
  * Response-Type: application/json
  * Resultado: Lista de POIs cadastrados ou mensagem de erro
  
* Buscar POIs por proximidade de um ponto de referência (COORDENADA_X, COORDENADA_y) e uma distância máxima (DISTANCIA_MAX)
  * Endpoint: http://localhost:8080/restapi/rest/pois/near?x=COORDENADA_X&y=COORDENADA_Y&dmax=DISTANCIA_MAX
  * Método: GET
  * Response-Type: application/json
  * Exemplo: http://localhost:8080/restapi/rest/pois/near?x=20&y=10&dmax=10
  * Resultado: Lista de POIs que estejam a uma distância menor ou igual a DISTANCIA_MAX a partir do ponto de referência
  
* Excluir POI
  * Endpoint: http://localhost:8080/restapi/rest/pois/{POI_ID}
  * Método: DELETE
  * Resultado: Exclusão do POI ou mensagem de erro

Teste Online
-------------------------------------------------------
Todos os serviços podem ser testados online a partir da url abaixo:
 * [http://socialbe-prd.elasticbeanstalk.com/rest/pois](http://socialbe-prd.elasticbeanstalk.com/rest/pois)
  
