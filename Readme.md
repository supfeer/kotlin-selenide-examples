### Environment:
#### Linux(the same or older):
 - openjdk version "11.0.11" 2021-04-20
 - Docker version 20.10.6, build 370c289
 - docker-compose version 1.24.1, build 4667896b
 - GNU Make 4.2.1

### How to run:
Most of available commands are defined in `./Makefile`, so you can run a complex command like
`make up test_ui_remote` or another way
#### Here is quick start
 - make test_ui - Runs tests on your local environment
 - make up - Runs docker containers with browsers
 - make down - shutdowns containers
 - make test_ui_remote - Runs tests vi remote browsers. In this case - containerized chrome 
 - make report - Will show an Allure's report

### Contributing
You need follow current code style. 
To make it simpler you can run a command `make format`,
which will reformat your code's differences according to current code style.
All the rules are defined in `./config/detekt/detekt.yml`
