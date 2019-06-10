# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket na linguagem de programacao _Java_ que faz o uso do protocolo da camada de transporte TCP ou UDP.  

- [Requisitos](#requisitos) - Requisitos de software utilizado no projeto
- [Execução](#execução) - Executando o projeto

## Requisitos    

Faz-se necessario que haja o Java Development Kit (**JDK**) instalado na maquina a ser utilizada. Porem, caso nao haja previamente instalada, acesse esse [link] para download. Em seguida, dentro dessa pagina web, faca o download e instale o instalador de acordo com o seu sistema operacional.

[link]:https://www.oracle.com/technetwork/java/javase/downloads/index.html

## Compilação e execução  

execute na linha de terminal para a compilação e criação do objetos

```
$ make
```  
Ou, caso deseje apagar os objetos e os executáveis, digite  

```
$ make clean
```  
Em seguida, serão criados os seguintes arquivos binários (executáveis):

| Nome do executável: | Descrição: | 
| ---------- | ------------- |
|`udpClient` 	|Programa que implementa socket cliente que faz uso do protocolo UDP.  
|`udpServer` 	|Programa que implementa socket servidor que faz uso do protocolo UDP.  
|`tcpClient` 	|Programa que implementa socket cliente que faz uso do protocolo TCP.  
|`tcpServer` 	|Programa que implementa socket servidor que faz uso do protocolo TCP.

Diante disso, são necessários dois passos para realzar a execução.
No primeiro passo, basta digitar make run mais o procotocolo que a aplicação faz uso. Logo, por exemplo, caso seja o `udp`

```
$ make runUdp
```
Para o segundo passo basta digitar java mais o nome do executável, por exemplo:

```
$ java tcpClient
```
