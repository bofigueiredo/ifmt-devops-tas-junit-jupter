package ifmt.cba;

import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;

public class BuscarPedidoPorDataProducao_SistemaTest {
    
	@Test
    public void testPedidoServico_BuscarPorDataProducao(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        RestAssured
        .given()
            .log().all()
            .contentType("application/json")
            .queryParam("dataInicial", LocalDate.now().format(formato))
            .queryParam("dataFinal", LocalDate.now().format(formato))
        .when()
            .get("http://localhost:8080/pedido/dataproducao")
        .then()
            .log().all()
            .statusCode(200)
        	.body("size()", is(1));
    }
       
}
