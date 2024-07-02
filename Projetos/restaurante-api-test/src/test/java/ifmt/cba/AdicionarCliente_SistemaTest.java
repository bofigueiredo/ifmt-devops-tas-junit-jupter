package ifmt.cba;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import ifmt.cba.dto.ClienteDTO;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class AdicionarCliente_SistemaTest {
    
	@Test
    public void testClienteServico_Adicionar(){
		
		final String CPF = "99999999999";

        RestAssured
        .given()
            .log().all()
            .contentType("application/json")
            .body(novoCliente(CPF))
        .when()
            .post("http://localhost:8080/cliente")
        .then()
            .log().all()
            .statusCode(200)
            .body("codigo",Matchers.is(Matchers.notNullValue()));
        
        
        removerDadoDeTeste(CPF);
    }
    
    private ClienteDTO novoCliente(String cpf) {
    	ClienteDTO clienteDTO = new ClienteDTO();
    	clienteDTO.setNome("TESTESISTEMA" + cpf);
    	clienteDTO.setRG("123456789");
    	clienteDTO.setCPF(cpf);
    	clienteDTO.setTelefone("65981112222");
    	clienteDTO.setLogradouro("LOGRADOURO DO CLIENTE");
    	clienteDTO.setNumero("123");
    	clienteDTO.setBairro("CENTRO");
    	clienteDTO.setPontoReferencia("AO LADO DO SHOPPING");
		return clienteDTO;
    }
    
    private void removerDadoDeTeste(String cpf) {
    	Response response = RestAssured.request(Method.GET, "http://localhost:8080/cliente/nome/TESTESISTEMA"+cpf ) ;
    	
    	if (response.statusCode() == 200) {
    		List<ClienteDTO> clienteDTOList = response.jsonPath().getList("", ClienteDTO.class);
    		if (clienteDTOList.size() > 0) {
    			RestAssured.request(Method.DELETE, "http://localhost:8080/cliente/" + clienteDTOList.get(0).getCodigo()) ;
    		}
    	}
    }

   
}
