# Teste back-end
 Candidato: Cleber Santaterra
 
 ### Projeto
 
 O projeto foi estruturado seguindo **clean architecture pattern** de Uncle Bob.
 
  [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
  
 ### Tecnologias
 - Java 1.8
 - Mongo
 - ELK

  ### Solução
  Criar uma API Rest para gerenciamento de tarefas
  
  Foram criados os Verbos HTTP GET, POST, PUT, DELETE e PATCH.
  
  As tasks são salvas no banco não relacional MONGO.
  
  
  ### Iniciando a aplicação
  
  #### Necessário
  - Docker versão 18.09.2
  - docker-compose versão 1.23.2
  - O projeto utiliza o Lombok.
    - Se você utiliza o intelliJ é necessário instalar o plugin (*https://dicasdejava.com.br/como-configurar-o-lombok-no-intellij-idea/*)
    - Se você utiliza o ecplipse é necessário seguir esse tutorial (*https://dicasdejava.com.br/como-configurar-o-lombok-no-eclipse/*)
  
  No diretório do projeto execute:
  
  - docker-compose -f docker-componse.yml up -d --build
  - mvn clean install spring-boot:run
  
  Esse comando irá subir toda a estrutura da aplicação:
  
  - API de tarefas
  - Mongo
  - ELK (utilizado para retirar métricas via logs da aplicação)
  
  Para utilizar os boards criados no Kibana é necessário seguir os seguintes passos:
  **A aplicação deve estar UP**
  
  - Acessar o kibana *http://localhost:5601*
  - No input Adicionar o valor **tasklog*** e clicar em **Next step**
  - Na proxima tela selecionar o valor **@timestamp** e clicar em **Create index pattern**
  - Na parte superior da tela selecionar a opção **Saved Object** e clicar o botão **import**
  - Selecionar o arquivo **Kibana-reports.json** que está no **root** do projeto e click em todos os botões de confirmar
  - No menu lateral click na opção **Dashboard** selecione **Operacional Tarefas**
  - A tela apresentada é com os boards da aplicação. (Para mostrar todos os cenário configurei os testes unitários para enviar dados para o kibana)
  
  ##### Boards
  - Criar Tarefa: relação de tarefas criadas com sucesso e com erro.
  - Atualizar Tarefa: relação de tarefas atualizadas com sucesso e com erro.
  - Consultar Tarefas: relação de consulta tarefas (findAll) realizadas com sucesso e com erro.
  - Consultar Tarefa ID: relação de consultas de tarefas com o id realizadas com sucesso e com erro.
  - Consultar Tarefa Status: relação de consultas de tarefas com o status da tarefa realizadas com sucesso e com erro.
  - Deletar Tarefa: relação de tarefas deletadas com sucesso e com erro.
  - Total: relação de quantidade de operações realizadas por funcionalidade
    - SAVE: Tarefas criadas
    - FIND_ALL: Pesquisa de todas as tasks
    - FIND_BY_ID: Pesquisa de task com o ID
    - FIND_BY_STATUS: Pesquisa todas as tasks em um determinado status
    - UPDATE: Atualiza a task
    - DELETE_BY_ID: Delete a task
    
  
  ### API
  - API de tarefas: *http://localhost:8080*
  - Documentação: *http://localhost:8080/swagger-ui.html*
  - Kibana: *http://localhost:5601*
  
  ### Coverage
  
  ![coverage](https://github.com/cleberms/task-list-api-app/blob/master/coverage/coverage.png)
