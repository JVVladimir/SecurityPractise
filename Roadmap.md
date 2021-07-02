Basics of Spring Security
--

1. [CSRF](https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/csrf.html)
2. JWT
3. OAuth
4. [OAuth2](https://www.marcobehler.com/guides/spring-security-oauth2)

`Content-Disposition: attachment; filename="filename.jpg"`
Первая часть говорит браузеру, что документ должен быть скачан, а вторая указывает оригинальное название

```
POST /test.html HTTP/1.1
Host: example.org
Content-Type: multipart/form-data;boundary="boundary"
--boundary
Content-Disposition: form-data; name="field1"
value1
--boundary
Content-Disposition: form-data; name="field2"; filename="example.txt"
value2
--boundary--
```
Следующие хедеры не вызывают preflight запросов:
```
Accept
Accept-Language
Content-Language
Content-Type whose value, once parsed, has a MIME type (ignoring parameters) that is application/x-www-form-urlencoded, multipart/form-data, or text/plain
```

Полезные статьи по устройству Spring Security:
<https://www.marcobehler.com/guides/spring-security>
<https://habr.com/ru/post/346628/>

Концепция фильтров в Spring Security:

![img.png](img.png)

Процесс аутентификации:  
1. BasicAuthFilter вынимает из хедера логин и пароль
2. Передает логин в UserDetailsService (есть разные реализации) для получения UserDetails (логин и хеш-пароля)
3. Хеширует пароль из запроса с помощью PasswordEncoder бина (есть разные реализации) и сравнивает с хешом из UserDetails

Структура хедера BasicAuth: Authorization -> Basic user:pass

Если пароли в БД захешированы разными алгоритмами, то можно использовать PasswordEncoderFactories (по префексу хеша подбирает алгоритм дешифровки).

Если данные для проверки аутентификации находятся не на бэке (например, это сторонний сервис с REST), то нужно реализовать свой AuthenticationProvider.
principal = login
credentials = password

https://www.baeldung.com/security-spring
https://medium.com/javarevisited/all-you-need-to-know-about-spring-security-basics-aea98c680d01
https://www.javainuse.com/webseries/spring-security-jwt/chap3