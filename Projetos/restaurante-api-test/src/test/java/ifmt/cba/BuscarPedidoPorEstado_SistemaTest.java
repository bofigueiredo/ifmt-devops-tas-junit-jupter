package ifmt.cba;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class BuscarPedidoPorEstado_SistemaTest {
    
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
            .statusCode(200)
        	.body("size()", is(1));

    }
       
}
