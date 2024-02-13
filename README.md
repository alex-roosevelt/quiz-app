# Quiz Application

This is a simple Quiz Application backend developed using Spring Boot framework. It provides APIs to manage quiz questions and check user answers.

## Prerequisites

- Java 17
- Maven

## Running the Application

1. Clone this repository:
   git clone https://github.com/your_username/quiz-application.git
2. Navigate to the project directory:
  cd quiz-application
3. Build the project:
  mvn clean install
4. Run the application:
  mvn spring-boot:run


The application will start running on http://localhost:8080.

## APIs

### Get All Questions

- **URL:** `/api/questions`
- **Method:** `GET`
- **Description:** Get a list of all quiz questions.
- **Response:** Array of quiz questions.

### Add Question

- **URL:** `/api/questions`
- **Method:** `POST`
- **Description:** Add a new quiz question.
- **Request Body:** Request params with `question` and `answer` fields.
- **Response:** JSON object with the added question, including its generated `id`.

### Get Question by ID

- **URL:** `/api/questions/{id}`
- **Method:** `GET`
- **Description:** Get a quiz question by its ID.
- **Path Parameter:** ID of the question.
- **Response:** JSON object with the question.

### Update Question

- **URL:** `/api/questions/{id}`
- **Method:** `PUT`
- **Description:** Update an existing quiz question.
- **Path Parameter:** ID of the question to update.
- **Request Body:** Request params with `question` and `answer` fields.
- **Response:** JSON object with the updated question.

### Delete Question

- **URL:** `/api/questions/{id}`
- **Method:** `DELETE`
- **Description:** Delete a quiz question by its ID.
- **Path Parameter:** ID of the question to delete.
- **Response:** Success message.

## Swagger UI

You can explore and test the APIs using Swagger UI. Open the following URL in your web browser:

[Swagger UI](http://localhost:8080/swagger-ui/index.html)


