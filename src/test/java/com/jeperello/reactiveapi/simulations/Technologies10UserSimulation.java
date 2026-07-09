package com.jeperello.reactiveapi.simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class Technologies10UserSimulation extends Simulation {
    private static final int CONCURRENT_USERS = 10;
    private static final String BASE_URL = System.getProperty(
            "gatling.baseUrl",
            System.getenv().getOrDefault("GATLING_BASE_URL", "http://localhost:8080")
    );
    private static final String TECHNOLOGIES_PATH = "/api/technologies";
    private static final int MAX_STREAM_DURATION_SECONDS = 45;
    private static final String STREAM_CONTENT_TYPE = "text/event-stream";

    HttpProtocolBuilder httpProtocol = http
            .baseUrl(BASE_URL)
            .acceptHeader(STREAM_CONTENT_TYPE)
            .contentTypeHeader("application/json");

    ScenarioBuilder scn = scenario("Stream Technologies With Concurrent Users")
            .exec(http("Get Technologies Stream")
                    .get(TECHNOLOGIES_PATH)
                    .check(status().is(200))
                    .check(headerRegex("Content-Type", STREAM_CONTENT_TYPE + ".*")));

    {
        setUp(
                scn.injectOpen(atOnceUsers(CONCURRENT_USERS))
        ).protocols(httpProtocol)
                .maxDuration(MAX_STREAM_DURATION_SECONDS)
                .assertions(
                        global().failedRequests().count().is(0L),
                        global().successfulRequests().percent().gte(100.0),
                        global().responseTime().max().lt(MAX_STREAM_DURATION_SECONDS * 1000)
                );
    }
}
