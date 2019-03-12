[![Build Status](https://travis-ci.org/AlexandrKaleganov/servletusersdata.svg?branch=master)](https://travis-ci.org/AlexandrKaleganov/servletusersdata)
[![codecov](https://codecov.io/gh/AlexandrKaleganov/servletusersdata/branch/master/graph/badge.svg)](https://codecov.io/gh/AlexandrKaleganov/servletusersdata)
# servletusersdata
для запуска проекта просто необходимо импортировать проект с мейвеном, 
также необходима база данных имя usersdata пароль к ней можно указать свой но надо будет отредактировать 
**в файле gradle.properties
db.password=444444**
остальные настройки бд находятся тамже
**для тестов настрокий регулируются в pom.xml** 
стартануть чере TomCat сервер
приложение будет доступно по адресу http://localhost:8080/
написал простое приложение для добавления, редактирования и удаления пользователей.
Базу данных адресов брал из интернета со страницы 
https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json
в формате json  получал HashMap<String, ArrayList<String>> адресов и добавлял их в базу данных
для инициализации использовал класс architecture.dbmanagment.DbinitAdres стартовый сервлет UserServlet, там же 
в методе init инициализируются адреса, логин и пароль по умолчанияю используется root root 
автоматом добавляется в базу если отсутствует в классе DbStore 
при добавлении и редактировании пользователя используется база адресов городов и стран, страны динамически подгружаются
на страницу, после выбора страны с помощью ajax делается постзапрос и также подгружются города выбранной страны.
пользователь root  не выводится в общий список, чтобы нельзя было отредактиовать или удалить.
Также стал использовать диспетчер класс **_DispatchDiapason_**  очень удобная задумка, создаётся хешкарта с функциональными методами
и инициализируются методы по ключам в зависимости от ключа выполняется то или иное действие 
Также стал использовать liquibase  для изменения схем баз данных, 
**_db.script_**
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/1.png"Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/2.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/3.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/4.png "Optional Title")