


# Sistema-djjv projeto1

Siga os passos abaixo para executar a aplicação.  
O processo basicamente será copiar e colar os comandos na sequência apresentada abaixo.
Os comandos são apresentados em uma única linha e devem ser executados desta  forma. 
Na primeira vez o processo irá demorar um pouco  pois diversas dependências serão baixadas.

### Abra um terminal
use as teclas **CTRL + ALT + T** 

### Configurar o git

```shell
git config --global credential.helper store
```

### Clonar o projeto
Será solictado seu usuário e senha na primeira vez

```shell
git clone https://gitlab.com/mauricio.gamarra/sistema-djjv-projeto1.git
```
Exemplo de saida
```shell
Cloning into 'sistema-djjv-projeto1'...
Username for 'https://github.com': mgamarra
Password for 'https://mgamarra@github.com': 
remote: Enumerating objects: 1201, done.
remote: Counting objects: 100% (1201/1201), done.
remote: Compressing objects: 100% (770/770), done.
remote: Total 1201 (delta 393), reused 1201 (delta 393), pack-reused 0
Receiving objects: 100% (1201/1201), 1.19 MiB | 1.59 MiB/s, done.
Resolving deltas: 100% (393/393), done.

```

### Acesso o diretório do projeto 

```shell
cd sistema-djjv-projeto1
```

###  Dar permissão de execução nos scripts 
```shell
chmod +x *.sh
```


## Os passos acima só serão executados na primeira vez.
### Nas proximas execuções, para atualizar o código, deve-se entar no diretório **sistema-djjv-projeto1** e utilizar o   comando:*git*



```shell
git pull
```



### Compilar as aplicações
```shell
./01-build.sh
./02-docker-build.sh 
```
### Subir o ambiente

```shell
./infra-up.sh
```
### Acesse a aplicação
Abra o browser e acesse o endereço http://localhost/

### Baixar o **ambiente**
```shell
./infra-down.sh
```

### Limpar o ambiente

```shell
cd ..
sudo rm -rf sistema-djjv-projeto1/
```


### Limpar a base local
Eventualmente durante o desenvolvimento pode ser nécesário a limpeza do banco de dados.

Dentro do  diretório **sistema-djjv-projeto1**  execute os comandos abaixo

##### Baixe o ambiente, caso o mesmo ainda esteja rodando.

```shell
./infra-down.sh
```
##### Remover o banco de dados.

```shell
sudo rm -rf postgres_data/
```

### Executar todas as operações de uma vez

```shell
./infra-down.sh && sudo rm -rf postgres_data/ && ./pacotes.sh && ./01-build.sh && ./02-docker-build.sh && ./infra-up.sh 
```

## PGAdmin
Para acesso ao banco utilize as informações abaixo

url: http://localhost:5050
user: pgadmin@example.com
pwd: admin

Dados da conexão ao banco
host name: postgres
port: 5432
Maintenance Database: tcc
usr: postgres
pwd: postgres


## Passos para limpar totalmente seu ambiente
#### Atenção ao executar esse passo, ele remove todo o diretório do projeto. Serár necessário executar desde o inicio,

1. Abra o terminal (CTL + ALT +T)
2. Digite sudo rm -rf sistema-djjv-projeto1/
3. informe sua senha (caso seja solicitado)
