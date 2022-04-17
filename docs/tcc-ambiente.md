# Instalação e configuração do ambiente

Requisitos de SO e ferramentas.

Sigam os passos abaixo para deixar as máquinas preparadas para rodar a aplicação.

O processo basicamente será copiar e colar os camandos na sequencia apresentada abaixo.

Os comandos a serem executados estão nas linhas que começar com o símbolo **$** e são apresentados em linha única. O **$** não faz parte do comando. 

Exemplo:

```shell
# No comando:
$ sudo apt-get update
# Deve-se executar 
sudo apt-get update
```
#### Cuidado para não incluir um espaço em branco no começo da linha

## Ubuntu 19.10

Links de tutoriais de como efetuar a instalação do Ubuntu 19.10

* https://www.youtube.com/watch?v=fWpF8ICoi_c

* https://lcomlinux.wordpress.com/2018/08/20/instalacao-ubuntu-18-04-e-windows-10-dual-boot-uefi/

Defina uma senha fácil, para agilizar o processo de instalação.


### Atualize os pacotes
```shell
$ sudo apt-get update
```
## Tilix
* https://www.edivaldobrito.com.br/instalar-o-emulador-de-terminal-terminix/

Abra um terminal (use as teclas CTRL + ALT + T);
```shell
# Adicione o repositório do programa
$ sudo add-apt-repository ppa:webupd8team/terminix

$ sudo apt install tilix
```

Definir o tilix como terminal padrão
```shell
$ sudo update-alternatives --config x-terminal-emulator
```
Escolha o tilix na lista. Finalize o terminal e inicie novamente (CTRL + ALT + T);

## Java
Compilação e execução do back end da aplicação
```shell
$ wget https://download.java.net/openjdk/jdk11/ri/openjdk-11+28_linux-x64_bin.tar.gz -O /tmp/openjdk-11+28_linux-x64_bin.tar.gz
$ sudo mkdir -p /usr/lib/jvm
$ sudo tar xfvz /tmp/openjdk-11+28_linux-x64_bin.tar.gz --directory /usr/lib/jvm
$ rm -f /tmp/openjdk-11+28_linux-x64_bin.tar.gz

# configurar no alternatives
$ sudo update-alternatives --install /usr/bin/java java /usr/lib/jvm/jdk-11/bin/java 1
$ sudo update-alternatives --install /usr/bin/javac javac /usr/lib/jvm/jdk-11/bin/javac 1
# esse não vai ser necessário para esse sistema
#$ sudo update-alternatives --install /usr/bin/javaws javaws /usr/lib/jvm/jdk-11/bin/javaws 1

# testar configuração do java
$ sudo update-alternatives --config java
There is only one alternative in link group java (providing /usr/bin/java): /usr/lib/jvm/jdk-11/bin/java
Nothing to configure.

# testar a versão do java
$ java -version
openjdk version "11" 2018-09-25
# testar a versão do javac 
$ javac -version
javac 11
```

### Maven
Utilitário java para compilação das aplicações Java
```shell
$ sudo apt-get update
$ sudo apt install maven
$ mvn -version
Apache Maven 3.6.1
```
## NodeJs
Compilação e execução do front end da aplicação.
 *  [ https://www.devroom.io/2011/10/24/installing-node-js-and-npm-on-ubuntu-debian/](https://www.devroom.io/2011/10/24/installing-node-js-and-npm-on-ubuntu-debian/)

Atualize o sistema
```shell
$ sudo apt-get update
$ sudo apt-get install git-core curl build-essential openssl libssl-dev python
$ sudo apt-get install python3-distutils
```
Clone o repositório do Node.js 
```shell
$ git clone https://github.com/nodejs/node.git

```
Compile e instale o Node.js 
```shell
$ cd node
$ ./configure
# O make demora, tenha paciência
$ make
$ sudo make install
```

Verifique se o node foi instalado corretamente

```shell
$ node -v
v14.0.0-pre
```
### NPM
```shell
$ curl -L https://npmjs.org/install.sh | sudo sh
6.14.3
```

## Docker

Será utilizado para rodar as aplicações

 * https://www.digitalocean.com/community/tutorials/como-instalar-e-usar-o-docker-no-ubuntu-18-04-pt

```shell
$ sudo apt install apt-transport-https ca-certificates curl software-properties-common
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
$ sudo apt update
$ sudo apt install docker-ce

# Verifique se ele está sendo executado:
$ sudo systemctl status docker
docker.service - Docker Application Container Engine
# pressione (CTRL + c) para voltar ao terminal

# Adicione seu usuário ao grupo docker:
$ sudo usermod -aG docker ${USER}
```

Para aplicar a nova associação ao grupo, reinicie o SO.

Confirme que seu usuário está adicionado ao grupo docker
```shell
$ id -nG
user adm cdrom sudo dip plugdev lpadmin lxd sambashare docker
```
Teste o comando docker
```shell
$ docker
Usage:	docker [OPTIONS] COMMAND
```


### docker-compose
 * https://docs.docker.com/compose/install/

 * https://www.digitalocean.com/community/tutorials/how-to-install-docker-compose-on-ubuntu-18-04-pt

```shell
$ sudo curl -L https://github.com/docker/compose/releases/download/1.21.2/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose

$ sudo chmod +x /usr/local/bin/docker-compose

$ sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

# Teste a instalação.
$ docker-compose --version
docker-compose version 1.21.2, build a133471
```

## Imagens da aplicação
Banco de dados postgres
 *  https://hub.docker.com/_/postgres
```shell
$ docker pull postgres

$ docker images
REPOSITORY TAG IMAGE ID CREATED SIZE
postgres latest 73119b8892f9 2 weeks ago 314MB

```
## Node: Comandos Uteis
Se o processo do node ficar preso
```shell
sudo killall node
```

## Nodejs: Atualizar pacotes
Durante algum momento será necessário a autlização dos pacotes abaixo, então vamos atualizar agora. 
```shell
$ sudo npm install -g browser-sync
```

## SpringBoot e Nodejs
https://medium.com/@isurunuwanthilaka/docker-zero-to-hero-with-springboot-postgres-e0b8c3a4dccb
