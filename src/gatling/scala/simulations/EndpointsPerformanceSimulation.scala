package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class EndpointsPerformanceSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val endpointsPerformanceScenario = scenario("Critical Endpoints Performance Test")
    .exec(http("Get All Jobs")
      .get("/api/jobs/all")
      .check(status.is(200))
    )
    .pause(1 second)
    .exec(http("Add New Job")
      .post("/api/jobs/add")
      .body(StringBody("""{"title": "Software Engineer", "description": "Develop software solutions."}""")).asJson
      .check(status.is(201))
    )
    .pause(1 second)
    .exec(http("Get Job Recommendations")
      .get("/api/jobs/recommendations?userPreferences=test")
      .check(status.is(200))
    )

  setUp(
    endpointsPerformanceScenario.inject(
      constantUsersPerSec(10) during (10 minutes)
    ).protocols(httpProtocol)
  )
}
