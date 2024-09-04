package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class RegressionPerformanceSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val regressionScenario = scenario("Regression Performance Test")
    .exec(http("Get Jobs By Salary Range")
      .get("/api/jobs/by-salary-range?minSalary=3000&maxSalary=5000")
      .check(status.is(200))
    )

  setUp(
    regressionScenario.inject(
      rampUsers(50) during (5 minutes)
    ).protocols(httpProtocol)
  )
}
