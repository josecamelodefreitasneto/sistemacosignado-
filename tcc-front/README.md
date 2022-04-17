REACT_APP_STAGE=docker npm start


REACT_APP_STAGE=docker npm start

REACT_APP_STAGE=docker npm run build





docker run -i --rm -p 80:80 --name tcc-front tcc/tcc-front



docker build -f tcc-extra/Dockerfile -t tcc/tcc-extra tcc-extra/.

docker run -i --rm --name tcc-extra tcc/tcc-extra

docker exec -i -t tcc-extra /bin/bash


docker run -i --rm --name tcc-extra ubuntu
docker run -i --rm --name tcc-extra ubuntu
docker exec -i -t tcc-extra /bin/bash