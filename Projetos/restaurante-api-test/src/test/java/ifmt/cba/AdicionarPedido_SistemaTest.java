package ifmt.cba;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.dto.PreparoProdutoDTO;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class AdicionarPedido_SistemaTest {

	@Test
	public void testPedidoServico_Adicionar() {
		
		RestAssured.given()
			.log().all()
			.contentType("application/json")
			.body(novoPedido())
		.when()
			.post("http://localhost:8080/pedido")
		.then()
			.log().all()
			.statusCode(200)
			.body("codigo", Matchers.is(Matchers.notNullValue()));

	}

	private PedidoDTO novoPedido() {
		PedidoDTO novoPedido = new PedidoDTO();
		novoPedido.setDataPedido(LocalDate.now());
		novoPedido.setHoraPedido(LocalTime.MIN);
		novoPedido.setEstado(EstadoPedidoDTO.REGISTRADO);
		novoPedido.setClienteDTO(resolveDependenciaCliente(1));

		List<ItemPedidoDTO> listaItemPedido = new ArrayList<ItemPedidoDTO>();

		ItemPedidoDTO itemPedidoDTO1 = new ItemPedidoDTO();
		itemPedidoDTO1.setPreparoProdutoDTO(resolveDependenciaPreparoProduto(1)); // ARROZ COZIDO
		itemPedidoDTO1.setQuantidadePorcao(1);

		listaItemPedido.add(itemPedidoDTO1);

		ItemPedidoDTO itemPedidoDTO2 = new ItemPedidoDTO();
		itemPedidoDTO2.setPreparoProdutoDTO(resolveDependenciaPreparoProduto(2)); // ALCATRA ASSADA
		itemPedidoDTO2.setQuantidadePorcao(1);

		listaItemPedido.add(itemPedidoDTO2);

		novoPedido.setListaItens(listaItemPedido);

		return novoPedido;
	}

	private PreparoProdutoDTO resolveDependenciaPreparoProduto(int codigo) {
		Response response = RestAssured.request(Method.GET, "http://localhost:8080/preparo/codigo/" + codigo);
		PreparoProdutoDTO preparoProdutoDTO = null;

		if (response.statusCode() == 200) {
			preparoProdutoDTO = response.getBody().as(PreparoProdutoDTO.class);
		}

		return preparoProdutoDTO;
	}

	private ClienteDTO resolveDependenciaCliente(int codigo) {
		Response response = RestAssured.request(Method.GET, "http://localhost:8080/cliente/codigo/" + codigo);
		ClienteDTO clienteDTO = null;

		if (response.statusCode() == 200) {
			clienteDTO = response.getBody().as(ClienteDTO.class);
		}

		return clienteDTO;
	}

}
