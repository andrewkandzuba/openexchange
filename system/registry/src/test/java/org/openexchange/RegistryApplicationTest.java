package org.openexchange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RegistryApplication.class, webEnvironment = RANDOM_PORT)
public class RegistryApplicationTest {

    @LocalServerPort
    private int port;

    @Test
    public void testCatalogLoads() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/eureka/apps", Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void testAdminLoads() {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = new TestRestTemplate().getForEntity("http://localhost:" + port + "/env", Map.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}