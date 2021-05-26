import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.UUID;

public class CompleteTest {

    private static final Log log = LogFactory.getLog(CompleteTest.class);

    private static final int ORDERS_PORT = 8080;
    private static final int KAFKA_CONNECT_PORT = 8083;
    private static final int ELASTICSEARCH_PORT = 9200;

    private static final Duration STARTUP_TIMEOUT = Duration.ofSeconds(120);

    @ClassRule
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("../docker-compose.yml"))
                    .withExposedService("orders_1", ORDERS_PORT, Wait.forListeningPort().withStartupTimeout(STARTUP_TIMEOUT))
                    .withExposedService("kafka-connect_1", KAFKA_CONNECT_PORT, Wait.forListeningPort().withStartupTimeout(STARTUP_TIMEOUT))
                    .withExposedService("elasticsearch_1", ELASTICSEARCH_PORT, Wait.forListeningPort().withStartupTimeout(STARTUP_TIMEOUT));

    @Test
    public void alltest() {
        log.info("starting test...");

        log.info("starting orders request...");
        try (final CloseableHttpClient client = HttpClientBuilder.create().build()) {

            final HttpPost post = newOrderRequest(UUID.randomUUID().toString(), "Customer");
            final HttpResponse response = client.execute(post);
            Assert.assertEquals(HttpStatus.SC_ACCEPTED, response.getStatusLine().getStatusCode());


        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail(ex.getMessage());
        }

        log.info("orders requested ...");
    }

    private HttpPost newOrderRequest(final String identifier, final String customer) throws UnsupportedEncodingException {
        final HttpPost post = new HttpPost("http://localhost:" + ORDERS_PORT + "/orders");
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
        post.setEntity(new StringEntity(String.format("{\"identifier\": \"%s\",\"customer\": \"%s\", \"value\": 100,\"type\": \"SALE\"}", identifier, customer)));
        return post;
    }

}
