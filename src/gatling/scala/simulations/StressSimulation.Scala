package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class StressSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val stressScenario = scenario("Stress Test")
    .exec(http("Get Jobs By Salary Range")
      .get("/api/jobs/by-salary-range?minSalary=3000&maxSalary=5000")
      .check(status.is(200))
    )

  setUp(
    stressScenario.inject(
      rampUsers(100) during (1 minute),
      rampUsers(500) during (5 minutes),
      rampUsers(1000) during (10 minutes)
    ).protocols(httpProtocol)
  )
}
