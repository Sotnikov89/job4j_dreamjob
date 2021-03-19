# Цель проекта
Данный проект является учебным и создавался для лучшего понимания работы сервлетов и способов их тестирования, 
jsp страниц, передачи между ними параметров, базового представления о работе с JavaScript 
и Ajax, регистрации и авторизации, работы с сессией, а также работы с БД при помощи JSTL. 

В проекте применен MVC паттерн проектирования, а также иные паттерны, например Билдер.

# Описание проекта
Проект представляет из себя упрощенное подобие биржы работы, где можно добавлять вакансии и кандидатов, 
которые в свою очередь буду сохраняться в БД. Вход на портал осуществляется по логину и паролю, 
а все страницы заблокированы через фильтры. В разделе кандидаты или вакансии можно увидеть актуальные данные, 
а также именить их. При замене фото на новое, старая фотография будет удалена из хранилища, а новое добавлено.

Для создания вакансии используется только Имя, а для создания кандидата 
добавляется город. При редактировании существующего кандидата, выпадающий список городов будет автоматически установлен в наименование города
присвоенного ранее. Список загружается без перегрузки страницы, налету при помощи Ajax.

Если поле имя, останется пустым, появится напоминание о необходимости его заполнения, и форма не уйдет на обработку.

# Запуск проекта
В случае запуска проекта на PostgreSQL потребуется:
1. Указать в db.properties корректные данные для подключения к БД. Проверить путь к properties указанные в PsqlStore. 
   Для создания таблиц в БД можно воспользоваться скриптами из папки db.scripts, а для заполнение таблицы городов, 
   подготовленным файлом cities в resources.
2. Отредактировать путь для сохранения фото кандидатов в Download и Upload сервлетах, а также в методе deleteCandidate 
   класса PsqlStore.
3. Сконфигурировать Tomcat по url http://localhost:8080/dreamjob/
4. Зарегистрироваться в системе и войти.

Проект также можно запустить без PostgreSQL, при помощи имитации БД - классе MemStore:
1. Провести рефакторинг, заменив все поля PsqlStore.instOf() на MemStore.instOf(). 
   Также в файлах edit.jsp в post и в candidate.
2. В этом случае регистрация не требуется, достаточно войти по логину - root@local и паролю - root.