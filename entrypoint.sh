#!/bin/bash
set -e

DATA_DIR="/var/lib/postgresql/14/main"
BIN_DIR="/usr/lib/postgresql/14/bin"

echo "Verificando estrutura do diretório..."
mkdir -p "$DATA_DIR"
chown -R postgres:postgres /var/lib/postgresql

# Criação do cluster, se necessário
if [ ! -f "$DATA_DIR/postgresql.conf" ]; then
    echo "Cluster não encontrado. Inicializando com initdb..."
    su postgres -c "$BIN_DIR/initdb -D $DATA_DIR" || {
        echo "ERRO: initdb falhou!"
        exit 1
    }
else
    echo "Cluster já inicializado."
fi

# Inicia PostgreSQL em background
echo "Iniciando PostgreSQL..."
su postgres -c "$BIN_DIR/postgres -D $DATA_DIR" &
sleep 3

# Importa o dump se existir
DUMP_PATH="/home/joselucas/workspace/IB.dump"
if [ -f "$DUMP_PATH" ]; then
    echo "Importando base de dados..."
    su postgres -c "$BIN_DIR/createdb IB"
    su postgres -c "$BIN_DIR/psql IB < $DUMP_PATH"
else
    echo "Dump não encontrado em $DUMP_PATH"
fi

# Mantém o processo vivo
wait
