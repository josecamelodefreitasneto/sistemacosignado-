# FROM ubuntu:latest
# 
# RUN  apt-get update &&  \
#   apt-get install curl wget
# 
# CMD ["bash"]


FROM nginx

ARG DEBIAN_FRONTEND=noninteractive

RUN DEBIAN_FRONTEND=noninteractive  apt-get update &&  \ 
    apt-get install -y curl  &&   \
    apt-get install -y iputils-ping  &&  \
    apt-get clean

# install jq to parse json within bash scripts
RUN curl -o /usr/local/bin/jq http://stedolan.github.io/jq/download/linux64/jq && \
  chmod +x /usr/local/bin/jq    