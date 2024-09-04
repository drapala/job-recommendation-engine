package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class LongevitySimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val longevityScenario = scenario("Longevity Test")
    .exec(http("Get Jobs By Location")
      .get("/api/jobs/by-location?location=Remote")
      .check(status.is(200))
    )

  setUp(
    longevityScenario.inject(
      constantUsersPerSec(5) during (24 hours) // Simulates 5 users per second for 24 hours
    ).protocols(httpProtocol)
  )
}
