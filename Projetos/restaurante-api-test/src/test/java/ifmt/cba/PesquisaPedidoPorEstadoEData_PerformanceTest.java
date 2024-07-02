package ifmt.cba;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;

public class PesquisaPedidoPorEstadoEData_PerformanceTest {
    
	@Test
    public void testPedidoServico_PesquisaPorEstadoEData(){
		
		final int TIMEOUT_IN_MILISECONDS = 2000;
		
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.socket.timeout", TIMEOUT_IN_MILISECONDS)
                        .setParam("http.connection.timeout", TIMEOUT_IN_MILISECONDS));
        
        RestAssured
        .given()
            .log().all()
            .contentType("application/json")
            .queryParam("estado", "REGISTRADO")
            .queryParam("data", LocalDate.now().format(formato))
            
        .when()
            .get("http://localhost:8080/pedido/estadodata")
        .then()
        	.log().all() 
        	.statusCode(200)
        	.body("size()", greaterThan(90))
        	.time(lessThan(Long.valueOf(TIMEOUT_IN_MILISECONDS)));
    }
       
}
