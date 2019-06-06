RM = rm -rf
#Compilador
CC=g++

#Variaveis para os subdiretorios
#LIB_DIR=./lib Não haverá pois nenhuma biblioteca será usada
INC_DIR=./include
SRC_DIR=./src
OBJ_DIR=./build
BIN_DIR=./bin
DOC_DIR=./doc

# Opcoes de compilacao 
CFLAGS=	-Wall -pedantic -ansi -std=c++11 -pthread

# Garante que os alvos desta lista não sejam confundidos com arquivos de mesmo nome 
.PHONY:	all	clean doc debug

# Ao final da compilacão, remove os arquivos objetos
all: init udpClient udpServer tcpClient tcpServer 

debug:	CFLAGS += -g -O0
debug:	udpClient udpServer tcpClient tcpServer 

# Cria a pasta/diretório bin e a obj
init:
	@mkdir -p $(OBJ_DIR)/udp
	@mkdir -p $(OBJ_DIR)/tcp	
	@mkdir -p $(BIN_DIR)
	@mkdir -p $(DOC_DIR)

udpClient: CFLAGS+= -I$(INC_DIR)/udp
udpClient: $(OBJ_DIR)/udp/client.o
	@echo "============="
	@echo "Ligando o alvo $@"
	@echo "============="
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$@ $^
	@echo "+++ [Executavel udpClient criado em $(BIN_DIR)] +++"
	@echo "============="

# Alvo (target) para a construcao do objeto client.o
# Define o arquivo client.cpp como dependência.
$(OBJ_DIR)/udp/client.o:	$(SRC_DIR)/udp/client.cpp
	$(CC) -c $(CFLAGS) -o $@ $<


udpServer: CFLAGS+= -I$(INC_DIR)/udp
udpServer: $(OBJ_DIR)/udp/server.o
	@echo "============="
	@echo "Ligando o alvo $@"
	@echo "============="
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$@ $^
	@echo "+++ [Executavel udpServer criado em $(BIN_DIR)] +++"
	@echo "============="

# Alvo (target) para a construcao do objeto server.o
# Define o arquivo server.cpp como dependência.
$(OBJ_DIR)/udp/server.o:	$(SRC_DIR)/udp/server.cpp
	$(CC) -c $(CFLAGS) -o $@ $<


tcpClient: CFLAGS+= -I$(INC_DIR)/tcp
tcpClient: $(OBJ_DIR)/tcp/client.o
	@echo "============="
	@echo "Ligando o alvo $@"
	@echo "============="
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$@ $^
	@echo "+++ [Executavel tcpClient criado em $(BIN_DIR)] +++"
	@echo "============="

# Alvo (target) para a construcao do objeto client.o
# Define o arquivo client.cpp como dependência.
$(OBJ_DIR)/tcp/client.o:	$(SRC_DIR)/tcp/cplusplus/client.cpp
	$(CC) -c $(CFLAGS) -o $@ $<


tcpServer: CFLAGS+= -I$(INC_DIR)/tcp
tcpServer: $(OBJ_DIR)/tcp/server.o
	@echo "============="
	@echo "Ligando o alvo $@"
	@echo "============="
	$(CC) $(CFLAGS) -o $(BIN_DIR)/$@ $^
	@echo "+++ [Executavel tcpServer criado em $(BIN_DIR)] +++"
	@echo "============="

# Alvo (target) para a construcao do objeto tcpServer.o
# Define o arquivo tcpServer.cpp como dependência.
$(OBJ_DIR)/tcp/server.o:	$(SRC_DIR)/tcp/cplusplus/server.cpp
	$(CC) -c $(CFLAGS) -o $@ $<

doc:
	$(RM) $(DOC_DIR)/*
	doxygen Doxyfile

# Removendo os .o e os binários
clean:
	$(RM) $(BIN_DIR)/*
	$(RM) $(OBJ_DIR)/udp/*
	$(RM) $(OBJ_DIR)/tcp/*
	$(RM) $(DOC_DIR)/*
	
#FIM DO MAKEFILE
