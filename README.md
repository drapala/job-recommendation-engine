# Job Recommendation Engine - Java Service

This microservice is part of the "Multistack Real-Time Job Recommendation Engine" and is responsible for processing job recommendations in real-time by consuming events from Kafka, performing analyses using Presto, and managing structured data with MySQL.

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

### Scripts to Initialize Presto and Dependencies

To make it easier to initialize Presto and its dependencies, four scripts have been created:

1. **Run Presto (Bash):**

   Use the `run-presto.sh` script to start the Presto container on Unix environments:

   ```bash
   ./run-presto.sh
   ```

2. **Run Presto (PowerShell):**

   Use the `run-presto.ps1` script to start the Presto container on Windows environments using PowerShell:

   ```powershell
   .\run-presto.ps1
   ```

3. **Run Presto Dependencies (Bash):**

   Use the `run-presto-dependencies.sh` script to start the required dependencies for Presto on Unix environments:

   ```bash
   ./run-presto-dependencies.sh
   ```

4. **Run Presto Dependencies (PowerShell):**

   Use the `run-presto-dependencies.ps1` script to start the required dependencies for Presto on Windows environments using PowerShell:

   ```powershell
   .\run-presto-dependencies.ps1
   ```

These scripts simplify the initialization and configuration of the environment needed to run Presto along with its dependencies.

## Contributing

Feel free to contribute with improvements or fixes. Please fork the repository and open a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.
