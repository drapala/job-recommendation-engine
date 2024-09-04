package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class DegradedPerformanceSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val degradedPerformanceScenario = scenario("Degraded Performance Test")
    .exec(http("Simulate Degraded Network")
      .get("/api/jobs/by-location?location=Remote")
      .check(status.is(200))
    )

  setUp(
    degradedPerformanceScenario.inject(
      atOnceUsers(50) // Simulates 50 users at once under degraded conditions
    ).protocols(httpProtocol)
  )
}
