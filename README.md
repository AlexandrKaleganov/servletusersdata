[![Build Status](https://travis-ci.org/AlexandrKaleganov/akaleganov_junior.svg?branch=master)](https://travis-ci.org/AlexandrKaleganov/akaleganov_junior)
[![codecov](https://codecov.io/gh/AlexandrKaleganov/akaleganov_junior/branch/master/graph/badge.svg)](https://codecov.io/gh/AlexandrKaleganov/akaleganov_junior)
# akaleganov_junior
в данном курсе я изучил тему многопоточности: 
**assthread**
более углублённо изучил коллекции, для большего понимания работы коллекций написал свои реализации:
**collection_pro**
получил базовые знания написания sql запросов, старался усложнить себе задачи чтобы писать как можно больше sql запросов,
использовал субд postgresql и  SQlite:
**sql**
написал программу парсер вакансий для сайта sql.ru  программа консольная скачивате вакансии с сайта 
в базу данных  postgresql
**sqlruparser**
рассмотрел работу слушателей сервлетов
**servltlistener**
изучил написание javascript:
**servletjs**
изучил работу сервлетов, jsp, скриплетов, фильтров, совместное использование bootstrap
написал простое приложение для добавления, редактирования и удаления пользователей
**servletjsp**
базу данных адресов брал из интернета со страницы 
https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json
в формате json  получал HashMap<String, ArrayList<String>> адресов и добавлял их в базу данных
для инициализации использовал ласс architecture.DbinitAdres стартовый сервлет UserServlet, там же 
в методе init инициализируются адреса, логин и пароль по умолчанияю используется root root 
автоматом добавляется в базу если отсутствует в классе DbStore 
при добавлении и редактировании пользователя используется база адресов городов и стран, страны динамически подгружаются
на страницу, после выбора страны с помощью ajax делается постзапрос и также подгружются города выбранной страны.
пользователь root  не выводится в общий список, чтобы нельзя было отредактиовать или удалить.
Также стал использовать диспетчер класс **_DispatchDiapason_**  очень удобная задумка, создаётся хешкарта с функциональными методами
и инициализируются методы по ключам в зависимости от ключа выполняется то или иное действие 
Также стал использовать liquibase  для изменения схем баз данных, 
**_servletjsp.db.script_**
![Alt text](https://github.com/AlexandrKaleganov/akaleganov_junior/blob/master/img/1.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/akaleganov_junior/blob/master/img/2.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/akaleganov_junior/blob/master/img/3.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/akaleganov_junior/blob/master/img/4.png "Optional Title")