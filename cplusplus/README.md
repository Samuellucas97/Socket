# Sockets

## Introdução  

Nesta pasta contém a implementação de um socket na linguagem de programacao C++ que faz o uso do protocolo da camada de transporte TCP ou UDP.

- [Compilação e execução](#compilação-e-execução) - Como compilar e executar o projeto

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
  
Diante disso, basta executa o respectivo código de acordo como o nome do executável. Logo, por exemplo, caso seja o `udpClient`  

```
$ ./bin/udpClient
```
  
Além disso, caso deseje ver a documentação desses codigos, execute  

```
$ make doc
```  
Em seguida, vá até a pasta **doc** e abra o arquivo `index.html` em um navegador qualquer.   



