# 🍎 Система управления поставками

Компактное REST API приложение на Spring Boot для управления поставками фруктов.

## 📦 Содержимое

- **30 Java классов** в пакете `com.supply`
- **11 REST API endpoints** для работы с поставками
- **Встроенная база данных H2** для разработки
- **Swagger UI** для тестирования API
- **Maven** для сборки проекта

## 🚀 Быстрый старт

### Требования
- Java 17+
- Maven 3.8+

### Сборка (или сразу скачать файл .jar по ссылке и запустить https://disk.yandex.ru/d/M-aqe3zaDTkBzg)

```bash
mvn clean package -DskipTests
```

### Запуск

```bash
java -jar target/supply-management-1.0.0.jar
```

Приложение будет доступно на **http://localhost:8080/api**

## 📡 API Endpoints

### Поставщики (4 endpoint)
- `POST /api/suppliers/prices` - установить цену поставщика
- `GET /api/suppliers/{id}/products/{id}/prices` - история цен
- `GET /api/suppliers/active` - активные поставщики
- `GET /api/suppliers/search` - поиск поставщиков

### Поставки (5 endpoints)
- `POST /api/deliveries` - создать поставку
- `GET /api/deliveries/{id}` - получить поставку
- `GET /api/deliveries/period` - поставки за период
- `GET /api/deliveries/supplier/{id}` - поставки от поставщика
- `PATCH /api/deliveries/{id}/status` - изменить статус

### Отчеты (2 endpoints)
- `POST /api/reports/deliveries` - сформировать отчет
- `GET /api/reports/deliveries/quick` - отчет за текущий месяц

## 🔗 Полезные ссылки

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **H2 Console**: http://localhost:8080/api/h2-console
- **Health**: http://localhost:8080/api/actuator/health

## 📁 Структура проекта

```
src/main/java/com/supply/
├── model/          # Сущности БД (7 классов)
│   ├── Product.java
│   ├── Supplier.java
│   ├── Delivery.java
│   ├── DeliveryItem.java
│   ├── SupplierPrice.java
│   ├── ProductType.java (enum)
│   └── DeliveryStatus.java (enum)
│
├── repository/     # Доступ к данным (5 repos)
│   ├── ProductRepository.java
│   ├── SupplierRepository.java
│   ├── DeliveryRepository.java
│   ├── DeliveryItemRepository.java
│   └── SupplierPriceRepository.java
│
├── service/        # Бизнес-логика (3 сервиса)
│   ├── DeliveryService.java
│   ├── ReportService.java
│   └── SupplierService.java
│
├── controller/     # REST контроллеры (3 контроллера)
│   ├── DeliveryController.java
│   ├── ReportController.java
│   └── SupplierController.java
│
├── dto/            # DTO для API (6 классов)
│   ├── DeliveryRequestDTO.java
│   ├── DeliveryResponseDTO.java
│   ├── ReportRequestDTO.java
│   ├── ReportResponseDTO.java
│   ├── ReportItemDTO.java
│   └── SupplierPriceRequestDTO.java
│
├── exception/      # Обработка ошибок (4 класса)
│   ├── GlobalExceptionHandler.java
│   ├── ErrorResponse.java
│   ├── ResourceNotFoundException.java
│   └── ValidationException.java
│
├── config/         # Конфигурация (1 класс)
│   └── SwaggerConfig.java
│
└── SupplyApplication.java  # Main класс
```

## 💡 Примеры использования

### Создать поставку

```bash
curl -X POST http://localhost:8080/api/deliveries \
  -H "Content-Type: application/json" \
  -d '{
    "supplierId": 1,
    "deliveryDate": "2026-02-05",
    "status": "PENDING",
    "items": [
      {
        "productId": 1,
        "weight": 100.5,
        "unitPrice": 50.0
      }
    ]
  }'
```

### Получить активных поставщиков

```bash
curl http://localhost:8080/api/suppliers/active
```

### Сформировать отчет

```bash
curl -X POST http://localhost:8080/api/reports/deliveries \
  -H "Content-Type: application/json" \
  -d '{
    "startDate": "2026-01-01",
    "endDate": "2026-12-31"
  }'
```

## ⚙️ Конфигурация

**База данных**: H2 (в памяти)
- URL: `jdbc:h2:mem:testdb`
- Console: http://localhost:8080/api/h2-console

Для использования PostgreSQL отредактируйте `src/main/resources/application.yml`

## 🛑 Остановка приложения

```bash
pkill -f "java -jar target/supply-management"
```

или используйте `Ctrl+C` в терминале

## 🏗️ Архитектура

```
REST Controller
    ↓
Service (бизнес-логика)
    ↓
Repository (работа с БД)
    ↓
Database (H2)
```

## 🛠️ Технологический стек

- **Java 17 LTS** - язык программирования
- **Spring Boot 3.1.5** - веб-фреймворк
- **Maven 3.8+** - сборщик проектов
- **H2 Database** - база данных для разработки
- **Spring Data JPA/Hibernate** - ORM
- **Swagger/OpenAPI 3.0** - документация API
- **Lombok** - уменьшение boilerplate кода
