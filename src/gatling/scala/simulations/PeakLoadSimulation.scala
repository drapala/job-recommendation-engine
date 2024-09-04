package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class PeakLoadSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val peakLoadScenario = scenario("Peak Load Test")
    .exec(http("Get Job Recommendations")
      .get("/api/jobs/recommendations?userPreferences=test")
      .check(status.is(200))
    )

  setUp(
    peakLoadScenario.inject(
      rampUsers(1000) during (30 seconds) // Simulates 1000 users over 30 seconds
    ).protocols(httpProtocol)
  )
}
