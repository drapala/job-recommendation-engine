package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class SLAComplianceSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .acceptHeader("application/json")

  val slaComplianceScenario = scenario("SLA Compliance Test")
    .exec(http("Get Job Recommendations")
      .get("/api/jobs/recommendations?userPreferences=test")
      .check(status.is(200))
    )

  setUp(
    slaComplianceScenario.inject(
      constantUsersPerSec(10) during (15 minutes)
    ).protocols(httpProtocol)
  ).assertions(
    global.responseTime.mean.lte(200), // Average response time must be <= 200 ms
    global.successfulRequests.percent.gte(99) // At least 99% of the requests must succeed
  )
}
