package ifmt.cba.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PedidoDAO;

public class ClienteNegocio_ClienteDAO_IntegracaoTest {
	
	private ClienteDAO clienteDAO;
	private PedidoDAO pedidoDAO;
	
	private ClienteNegocio clienteNegocio;
	private ClienteDTO clienteDTO;
	
	@Test
	void testClienteNegocio_Inserir() {
		clienteDTO = new ClienteDTO();
		clienteDTO.setNome("CLIENTE DE TESTE");
		clienteDTO.setRG("123456789");
		clienteDTO.setCPF("99999999999");
		clienteDTO.setTelefone("65981112222");
		clienteDTO.setLogradouro("LOGRADOURO DO CLIENTE");
		clienteDTO.setNumero("123");
		clienteDTO.setBairro("CENTRO");
		clienteDTO.setPontoReferencia("AO LADO DA FARMACIA");
		
		clienteDAO = assertDoesNotThrow(() -> new ClienteDAO(FabricaEntityManager.getEntityManagerTeste()));
		pedidoDAO = assertDoesNotThrow(() -> new PedidoDAO(FabricaEntityManager.getEntityManagerTeste()));
		
		clienteNegocio = new ClienteNegocio(clienteDAO, pedidoDAO);
		
		Assertions.assertDoesNotThrow(() -> clienteNegocio.inserir(clienteDTO));
	}

}
