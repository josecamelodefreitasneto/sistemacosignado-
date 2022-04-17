FROM nginx
#COPY — from=build  /usr/share/nginx/html

#RUN apt-get update && apt-get install -y 


COPY build/  /usr/share/nginx/html

COPY ./default.conf-otimizado /etc/nginx/conf.d/default.conf
COPY ./nginx.conf /etc/nginx/nginx.conf

#COPY ./default.conf /etc/nginx/conf.d/default.conf1
#COPY ./default.conf /default.conf1
#COPY default.conf /default.conf2
#ADD default.conf /etc/nginx/conf.d/default.conf

# Configure nginx
# ---------------
#ADD nginx.conf /etc/nginx/nginx.conf

# EXPOSE 80
# CMD [“nginx”, “-g”, “daemon off;”]