package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class BasicLoadSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080") // Replace with your application's base URL
    .acceptHeader("application/json")

  val basicLoadScenario = scenario("Basic Load Test")
    .exec(http("Get All Jobs")
      .get("/api/jobs/all")
      .check(status.is(200))
    )

  setUp(
    basicLoadScenario.inject(
      constantUsersPerSec(20) during (5 minutes) // Simulates 20 users per second for 5 minutes
    ).protocols(httpProtocol)
  )
}
