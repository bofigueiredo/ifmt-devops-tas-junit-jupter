package ifmt.cba;

import static org.hamcrest.Matchers.lessThan;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class BuscarPedidoPorEstado_PerformanceTest {
    
	@Test
    public void testPedidoServico_BuscarPorEstado(){
        
		 RestAssured
         .given()
             .log().all()
             .contentType("application/json")
             .queryParam("estado", "REGISTRADO")
         .when()
             .get("http://localhost:8080/pedido/estado")
         .then()
             .log().all() 
             .time(lessThan(2000L));
		 
    }
       
}
