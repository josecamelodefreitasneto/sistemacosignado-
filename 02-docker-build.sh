#!/bin/bash



docker build -f tcc-back/src/main/docker/Dockerfile.jvm -t tcc/tcc-back  tcc-back/.

docker build -f tcc-extra/Dockerfile -t tcc/tcc-extra tcc-extra/.

docker build -f tcc-pgadmin4/Dockerfile -t tcc/tcc-pgadmin4 tcc-pgadmin4/.

npm install --prefix tcc-front/

#npm run build --prefix tcc-front/
REACT_APP_STAGE=selfhost npm run build --prefix tcc-front/

docker build -f tcc-front/Dockerfile -t tcc/tcc-front tcc-front/.