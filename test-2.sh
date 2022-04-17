#!/bin/bash


curl -s -L -D -- 'http://localhost/api/login/efetuar'  \
    -H 'Connection: keep-alive'   \
    -H 'User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36'   \
    -H 'credentials: include' -H 'content-type: application/json; charset=UTF-8'   \
    -H 'access-control-allow-origin: *'   \
    -H 'accept: */*'    \
    -H 'access-control-allow-credentials: true'   \
    -H 'Sec-Fetch-Dest: empty'   \
    -H 'Origin: http://localhost' -H 'Sec-Fetch-Site: same-origin' -H 'Sec-Fetch-Mode: cors'   \
    -H 'Referer: http://localhost/' -H 'Accept-Language: pt-BR,pt;q=0.9'    \
    --data-binary '{"login":"atendente1@tcc.com","pass":"123456"}' --compressed -o /dev/null -w '%{url_effective}'


