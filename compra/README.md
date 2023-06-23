#### Rodar serviço rabbitmq com docker compose.
* docker-compose up --build rabbitmq

#### Rodar todos os serviços com docker compose.
* docker compose up --build backen

#### Corrigir erro de associação de role ao criar ambiente

* Criar instância:
aws iam create-instance-profile --instance-profile-name EXAMPLEPROFILENAME

* Associar ao perfil criado:
aws iam add-role-to-instance-profile --instance-profile-name EXAMPLEPROFILENAME --role-name EXAMPLEROLENAME
