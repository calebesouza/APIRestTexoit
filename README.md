# APIRestTexoit

Para executar o projeto, é necessário ter configurado o ambiente com:
  - Java JDK 8+
  - IDE Eclipse
  - Maven
  
No terminal executar:
> $ git clone https://github.com/calebesouza/APIRestTexoit.git

No Eclipse, ir em File -> Import -> Projects from Folder or Archive. Selecionar o diretório que o clone do projeto foi baixado e clicar em Finish.

Ainda no eclipse, apertar com o botão direito no projeto "Maven -> Download Sources" e aguardar baixar as dependências do projeto.

Para rodar o projeto pelo eclipse, apertar com o botão direito do mouse no arquivo: **src/main/java/br.com.calebe/Main.java**
> Run As -> Java Application

No navegador web, executar as urls abaixo para:
  
  - retornar a lista de filmes
  > 127.0.0.1:8080/filmes
  
  - retornar o produtor com maior intervalo entre dois prêmios e o que obteve dois prêmios mais ráṕido
  > 127.0.0.1:8080/intervaloPremios
  
Para rodar os testes, o projeto tem que estar em execução. 
Clicar com o botão direito do mouse no arquivo: **src/test/java/br.com.calebe.testesFilmeService/TesteAPI.java**   
> Run As -> JUnit Test
