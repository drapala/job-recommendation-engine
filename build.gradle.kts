plugins {
	java
	id("org.springframework.boot") version "3.3.3"
	id("io.spring.dependency-management") version "1.1.6"
	id("io.gatling.gradle") version "3.11.5.2"
}

group = "com.drapala"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot dependencies
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.kafka:spring-kafka") {
		// Remover dependências que possam causar conflitos
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
		exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
		exclude(group = "org.apache.logging.log4j", module = "log4j-api")
	}
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-validation:3.3.0")

	// Ensure only Logback is used as the SLF4J provider
	implementation("org.springframework.boot:spring-boot-starter-logging") {
		exclude(group = "org.apache.logging.log4j")
		exclude(group = "log4j")
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
	}

	// Exclude Log4j from Spark Core and use Log4j-over-SLF4J bridge
	implementation("org.apache.spark:spark-core_2.12:3.4.0") {
		exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j2-impl")
		exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
		exclude(group = "org.apache.logging.log4j", module = "log4j-api")
		exclude(group = "org.apache.logging.log4j", module = "log4j-core")
	}
	implementation("org.apache.spark:spark-sql_2.12:3.4.0") {
		exclude(group = "org.apache.logging.log4j", module = "log4j-slf4j2-impl")
		exclude(group = "org.apache.logging.log4j", module = "log4j-to-slf4j")
		exclude(group = "org.apache.logging.log4j", module = "log4j-api")
		exclude(group = "org.apache.logging.log4j", module = "log4j-core")
	}

	// Add only Log4j-over-SLF4J bridge
	implementation("org.slf4j:log4j-over-slf4j:1.7.36")

	// Outras dependências do projeto
	implementation("javax.validation:validation-api:1.0.0.GA")
	implementation("com.facebook.presto:presto-jdbc:0.288.1")
	implementation("org.apache.commons:commons-text:1.10.0")

	// Gatling dependencies with exclusion of conflicting dependencies
	testImplementation("io.gatling.highcharts:gatling-charts-highcharts:3.11.5") {
		exclude(group = "org.apache.logging.log4j")
		exclude(group = "log4j")
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
	}
	testImplementation("io.gatling:gatling-app:3.11.5") {
		exclude(group = "org.apache.logging.log4j")
		exclude(group = "log4j")
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
	}
	testImplementation("io.gatling:gatling-recorder:3.11.5") {
		exclude(group = "org.apache.logging.log4j")
		exclude(group = "log4j")
		exclude(group = "org.slf4j", module = "slf4j-log4j12")
	}

	// H2 Database for testing
	runtimeOnly("com.h2database:h2:2.1.214")
	testImplementation("com.h2database:h2")

	// Spring Boot testing dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.kafka:spring-kafka-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
