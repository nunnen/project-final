## [REST API](http://localhost:8080/doc)

## Концепция:

- Spring Modulith
    - [Spring Modulith: достигли ли мы зрелости модульности](https://habr.com/ru/post/701984/)
    - [Introducing Spring Modulith](https://spring.io/blog/2022/10/21/introducing-spring-modulith)
    - [Spring Modulith - Reference documentation](https://docs.spring.io/spring-modulith/docs/current-SNAPSHOT/reference/html/)

```
  url: jdbc:postgresql://localhost:5432/jira
  username: jira
  password: JiraRush
```

- Есть 2 общие таблицы, на которых не fk
    - _Reference_ - справочник. Связь делаем по _code_ (по id нельзя, тк id привязано к окружению-конкретной базе)
    - _UserBelong_ - привязка юзеров с типом (owner, lead, ...) к объекту (таска, проект, спринт, ...). FK вручную будем
      проверять

## Аналоги

- https://java-source.net/open-source/issue-trackers

## Тестирование

- https://habr.com/ru/articles/259055/

Список выполненных задач:
- [x] Разобраться со структурой проекта (onboarding).
- [x] Удалить социальные сети: vk, yandex.
  - социальные сети были удалены из view, а также в коде 
- [x] Вынести чувствительную информацию в отдельный проперти файл
  - был добавлен application-secrets.yaml с чувствительной информацией
- [x] Переделать тесты так, чтоб во время тестов использовалась in memory БД (H2), а не PostgreSQL.
  - была добавлена зависимость H2, а также переделан changelog и data под работу в H2. Для работы скриптов накатывания БД была отредактирована аннотация @Sql в AbstractControllerTest
- [x] Написать тесты для всех публичных методов контроллера ProfileRestController.
  - были добавлены тесты для получения различных профилей (админ, юзер, неавторизованный пользователь) + обновление профиля (тесты с валидным и неверным json)
- [x] Сделать рефакторинг метода com.javarush.jira.bugtracking.attachment.FileUtil#upload чтоб он использовал современный подход для работы с файловой системой.
- [x] Написать Dockerfile для основного сервера
  - конфиг сервера переписан для работы в контейнере (подключение к БД)
  - собирается и запускается сервер в контейнере, работает на 8080 порте