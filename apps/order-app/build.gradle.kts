import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.microservice.playground"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2021.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-cassandra")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.google.cloud:spring-cloud-gcp-starter-pubsub")
	implementation("com.google.cloud:google-cloud-bigtable")
	implementation("com.google.cloud:spring-cloud-gcp-starter-data-datastore")
	implementation("com.spotify:futures-extra:4.3.1")
	implementation("org.springframework.cloud:spring-cloud-gcp-starter-pubsub")
	implementation("org.springframework.integration:spring-integration-core")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

dependencyManagement {
	imports {
		mavenBom("com.google.cloud:libraries-bom:23.0.0")
		mavenBom("com.google.cloud:spring-cloud-gcp-dependencies:2.0.7")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
