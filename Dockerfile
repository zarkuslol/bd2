FROM ubuntu:22.04

# Evita perguntas interativas
ENV DEBIAN_FRONTEND=noninteractive

# Atualiza e instala dependências
RUN apt update && apt install -y \
    aptitude \
    postgresql-14 \
    openjdk-8-jdk \
    libderby-java \
    ant \
    unzip \
    curl \
    gedit \
    sqlite3 \
    && rm -rf /var/lib/apt/lists/*

# Cria uma pasta de trabalho
WORKDIR /home/joselucas

# Define JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-1.8.0-openjdk-arm64
ENV PATH=$JAVA_HOME/bin:$PATH

# Derby-tools
ENV DERBY_HOME=/opt/derby/db-derby-10.14.2.0-bin
ENV PATH=$PATH:$DERBY_HOME/bin:/var/lib/postgresql/14/bin

# Copia o script de entrypoint
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

# Define como ponto de entrada
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]


