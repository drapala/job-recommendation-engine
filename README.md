# Job Recommendation Engine - Java Service

This microservice is part of the "Multistack Real-Time Job Recommendation Engine" and is responsible for processing job recommendations in real time by consuming events from Kafka, performing analyses using Presto, and managing structured data with MySQL.

## Requirements

- Java 17+
- Docker and Docker Compose
- Apache Kafka
- MySQL Database
- Gradle

## Configuration

### Local Setup Steps

1. **Clone the repository:**

   ```bash
   git clone https://github.com/drapala/job-recommendation-engine.git
   cd job-recommendation-engine
   ```

2. **Configure the properties file:**

   Edit the `src/main/resources/application.properties` file to add your credentials and connection settings for MySQL, Kafka, and Presto.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/job_db
   spring.datasource.username=root
   spring.datasource.password=password
   spring.kafka.bootstrap-servers=localhost:9092
   presto.server.url=http://localhost:8080
   ```

3. **Build and run the project:**

   Use Gradle to build and run the project:

   ```bash
   ./gradlew build
   ./gradlew bootRun
   ```

4. **Testing:**

   To run the unit and integration tests:

   ```bash
   ./gradlew test
   ```

## Usage

- **API REST Endpoints:**
  - `GET /api/jobs/all`: List all jobs with pagination.
  - `POST /api/jobs/add`: Add a new job.
  - `GET /api/jobs/recommendations`: Generate job recommendations based on user preferences.

## Docker

### Building and Running with Docker

1. **Build the Docker image:**

   ```bash
   docker build -t job-recommendation-engine .
   ```

2. **Run the container:**

   ```bash
   docker run -p 8080:8080 job-recommendation-engine
   ```

## Contributing

Feel free to contribute with improvements or fixes. Please fork the repository and open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
