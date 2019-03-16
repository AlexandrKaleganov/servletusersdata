[![Build Status](https://travis-ci.org/AlexandrKaleganov/servletusersdata.svg?branch=master)](https://travis-ci.org/AlexandrKaleganov/servletusersdata)
[![codecov](https://codecov.io/gh/AlexandrKaleganov/servletusersdata/branch/master/graph/badge.svg)](https://codecov.io/gh/AlexandrKaleganov/servletusersdata)
# servletusersdata
Написал простое приложение для добавления, редактирования и удаления пользователей.
Администратор видит всех пользователей, добавляет, удаляет, редактирует
Пользоваель видит всех пользователей и редактирует только себя, добавлят пользователей прав нету
Базу данных адресов брал из интернета со страницы 
https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json
в формате json  получал HashMap<String, ArrayList<String>> адресов и добавлял их в базу данных
для инициализации использовал класс architecture.dbmanagment.DbinitAdres ,
Запуск инициализации адресов происходит  ru.job4j.architecture.listener; 
public void contextInitialized(ServletContextEvent sce)

стартовый сервлет UserServlet. Логин и пароль по умолчанияю используется root root 
автоматом добавляется в базу если отсутствует в классе DbStore 
при добавлении и редактировании пользователя используется база адресов городов и стран, страны динамически подгружаются
на страницу с помощью ajax, который делает пост запрос на сервлет StringListServlet
этот сервлет возвращает из базы данные в виде стринг листов, адреса, роли.
, после выбора страны с помощью ajax делается постзапрос и также подгружются города выбранной страны.
 
 Фильтр AddUserFilter  - проверяет кто хочет залезть на страницу добавления пользователя,
 если это админ то пропустет, а если нет то выкинет на страницу с текстом:
 недостаточно прав.
 
Также стал использовать диспетчер класс **_DispatchDiapason_**  очень удобная задумка, создаётся хешкарта 
с функциональными методами
и инициализируются методы по ключам в зависимости от ключа выполняется то или иное действие 
Также стал использовать liquibase  для изменения схем баз данных, 
**_db.script_**

для запуска проекта просто необходимо импортировать проект с мейвеном, 
также необходима база данных имя usersdata пароль к ней можно указать свой но надо будет отредактировать 
**в файле gradle.properties
db.password=444444**
остальные настройки бд находятся тамже
**для тестов настроки регулируются в pom.xml** 
стартануть через TomCat сервер
приложение будет доступно по адресу http://localhost:8080/

![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/1.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/2.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/3.png "Optional Title")
![Alt text](https://github.com/AlexandrKaleganov/servletusersdata/blob/master/img/4.png "Optional Title")