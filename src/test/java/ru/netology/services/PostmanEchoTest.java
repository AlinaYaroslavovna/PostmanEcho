package ru.netology.services;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PostmanEchoTest {

    private static final String TEST_BODY_CONTENT = "Hello from Java Test!";

    @BeforeAll
    public static void setup() {
        // Установка базового URI для всех тестов
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    @DisplayName("Проверка успешной отправки простого тела и его возврата в поле 'data'")
    public void testPostRequestReturnsSentDataInBody() {
        // Given: Подготовка
        given()
                .body(TEST_BODY_CONTENT)
                // When: Выполнение POST-запроса к /post
                .when()
                .post("/post")
                // Then: Проверки
                .then()
                .statusCode(200)
                // JSONPath-выражение: так как ответ { "data": "..." }, мы обращаемся напрямую к полю 'data'
                .body("data", equalTo(TEST_BODY_CONTENT));
    }

    @Test
    @DisplayName("Тест, намеренно созданный для падения (Fail)")
    public void testFailingValidation() {
        String wrongExpectedValue = "This value will never match the response.";

        given()
                .body(TEST_BODY_CONTENT)
                .when()
                .post("/post")
                .then()
                .statusCode(200)
                // Проверяем поле 'data' на совпадение с заведомо неверной строкой
                .body("data", equalTo(wrongExpectedValue));
    }
}
