# This is the docker file for the YAOMRPlayback project
FROM    ubuntu:latest

# Basic message
RUN echo "Building docker image for YAOMR"

# Install installer helper tools
RUN apt-get install -y software-properties-common

RUN rm /etc/resolv.conf
RUN echo 'nameserver 8.8.8.8' >> /etc/resolv.conf
RUN echo 'nameserver 8.8.4.4' >> /etc/resolv.conf

# Add global apt repos for dependencies
RUN add-apt-repository -y "deb http://archive.ubuntu.com/ubuntu precise universe" && \
    add-apt-repository -y "deb http://archive.ubuntu.com/ubuntu precise main restricted universe multiverse" && \
    add-apt-repository -y "deb http://archive.ubuntu.com/ubuntu precise-updates main restricted universe multiverse" && \
    add-apt-repository -y "deb http://archive.ubuntu.com/ubuntu precise-backports main restricted universe multiverse"

Update and upgrade the repositories once
RUN apt-get update && apt-get -y upgrade

# Install java
RUN     apt-get -y install openjdk-7-jdk git

# The installation of tessaract and audiveris libraries, necessary for its functioning
RUN     apt-get -y install tesseract-ocr liblept4 libtesseract3 tesseract-ocr-deu tesseract-ocr-eng tesseract-ocr-fra tesseract-ocr-ita
RUN     wget https://kenai.com/projects/audiveris/downloads/download/oldies/audiveris-4.2.3318-ubuntu-amd64.deb -O audiveris.deb
RUN     dpkg -i audiveris.deb

# Install the gradle version 2.5 for building modulo7, canonical's distribution for gradle is outdated so gradle is installed manually
RUN     apt-get -y install wget unzip
RUN     wget https://services.gradle.org/distributions/gradle-2.5-bin.zip
RUN     unzip gradle-2.5-bin.zip

# Build the YAOMRPlayback project
RUN     git clone https://github.com/Khalian/YAOMRPlayback
RUN     export PATH=$PATH:/gradle-2.5/bin && cd YAOMRPlayback && gradle build
