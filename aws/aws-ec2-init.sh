#!/bin/bash

# Update yum and install nc
sudo yum update -y
sudo yum install nc -y

# Install docker
sudo yum install docker -y
sudo service docker start
sudo docker -v

#
# Add ec2-user to the docker group
#
sudo usermod -aG docker $USER

# Install docker-compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose

# Set execute permission to docker-compose
sudo chmod +x /usr/local/bin/docker-compose

#
# Enable docker to start up automatically after reboot
#
sudo systemctl enable docker.service
sudo systemctl enable containerd.service

# Install git 
sudo yum install git -y

# Clone Kafka project into directory
git clone https://github.com/digitalnus/kafka-docker.git

#
# Start up Kafka and Zookeeper
#
cd kafka-docker/docker
sudo docker-compose up -d 
