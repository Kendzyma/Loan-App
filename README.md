
# Project Title
Loan Application Service


## Run Locally

```bash
mvn clean package -DskipTests
docker compose up -d 
```


## Documentation

[swagger-ui](http://localhost:8083/swagger-ui/index.html)



# Loan Application Service

## Design Decisions and Trade-offs

### Key Design Choices

- **Validation Logic:** Inline validation ensures that operations like loan creation and status updates respect business rules (e.g., prohibiting approved loans for inactive products).
- **Efficient Status Updates:** The use of `switch` statements for loan status changes centralizes logic and minimizes oversight in status-dependent actions.
- **Logging with AOP:** Logging is implemented using AOP, allowing for fine-grained control over log statements and their output.
- **Transactional Management:** Critical methods are annotated with `@Transactional` to guarantee atomic updates of loan and transaction records, preventing partial updates.
- **Calculations:** Loan and interest calculations are encapsulated in a utility class (`LoanCalculator`), promoting reuse and simplifying the core service logic.
- **Domain Entity Usage:** Business logic embedded in entities (e.g., maturity date computation) ensures that domain objects maintain integrity and encapsulate relevant behavior.
- **Error Handling:** Specific exceptions (`BadRequestException`, `ForbiddenException`) provide clear, context-aware error feedback to API consumers without cluttering business logic.
- **Mapping:** ModelMapper facilitates DTO transformation, enabling seamless conversion between entity and API layers to maintain a clean separation of concerns.
- **Database Migrations with Flyway:** Flyway is utilized for managing database migrations, ensuring that database changes are applied consistently across environments while maintaining schema version control.
