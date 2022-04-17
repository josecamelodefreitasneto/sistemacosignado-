 #!/bin/bash
 set -e 

./infra-down.sh \
    && echo "@34public56" | sudo -S rm -rf postgres_data/ \
    && ./pacotes.sh \
    && ./01-build.sh \
    && ./02-docker-build.sh \
    && ./infra-up.sh 