# Projeto BD2

Este projeto utiliza Docker e Docker Compose para gerenciar o ambiente de desenvolvimento.

## Pré-requisitos

- [Docker](https://www.docker.com/get-started) instalado
- [Docker Compose](https://docs.docker.com/compose/install/) instalado

## Como rodar o projeto

1. Clone o repositório:
```bash
git clone <URL_DO_REPOSITORIO>
cd <NOME_DO_REPOSITORIO>
```

2. Inicie os containers com o Docker Compose:
```bash
docker-compose up --build -d
```

3. Acesse o serviço no navegador ou via terminal conforme configurado no `docker-compose.yml`.
```bash
docker exec -it jdbc-tutorial bash

## Parar os containers

Para parar os containers, utilize:
```bash
docker-compose down
```

## Estrutura do projeto

- `docker-compose.yml`: Configuração dos serviços Docker.
- `README.md`: Documentação do projeto.

## Contribuição

Sinta-se à vontade para abrir issues ou enviar pull requests.
