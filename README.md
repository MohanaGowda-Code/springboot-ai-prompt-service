# AI Prompt Service - Spring Boot

A Spring Boot 3.5 application that demonstrates **asynchronous WebClient calls** to the OpenAI API with **error handling, retries, timeout management**, and a clean architecture.  

This project is built with **Java 17** and is production-ready for learning best practices in **API integrations, resilience, and microservice communication**.

---

## Features

- **Async WebClient Calls**: Makes non-blocking HTTP requests to OpenAI API.
- **Error Handling**: Handles client errors (4xx), server errors (5xx), and rate limits.
- **Retry Mechanism**: Uses `retryWhen` to automatically retry failed requests with backoff.
- **Global Exception Handling**: Centralized exception handling for clean API responses.
- **Timeout Management**: Requests automatically timeout to prevent hanging calls.
- **Environment Variable for API Key**: Securely store your OpenAI API key using environment variables.
- **Ready for Circuit Breaker**: Code can be extended with **Resilience4j Circuit Breaker** in future.

---

## Tech Stack

- **Java 17**
- **Spring Boot 3.5**
- **WebClient (Reactive, Async HTTP calls)**
- **Maven**
- **Resilience Patterns (Retry, Timeout, Future Circuit Breaker)**

---

## Getting Started

### Prerequisites

- Java 17
- Maven
- OpenAI API key

### Setup

1. Clone the repository:

```bash
git clone https://github.com/MohanaGowda-Code/springboot-ai-prompt-service.git
cd springboot-ai-prompt-service

#Test the endpoint (Postman or browser):
POST http://localhost:8080/api/ai/prompt
Content-Type: application/json

{
  "prompt": "Hello AI, write a short poem about Java."
}

#Response:

{
  "response": "AI generated text here..."
}

#Author
Mohana P – Senior Java & Spring Boot Developer
