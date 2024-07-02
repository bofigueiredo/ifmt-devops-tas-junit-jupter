package ifmt.cba.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.entity.Cliente;
import ifmt.cba.entity.Entregador;
import ifmt.cba.entity.ItemPedido;
import ifmt.cba.entity.Pedido;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.EntregadorDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PedidoDAO;

public class PedidoNegocio_PedidoDAO_IntegracaoTest {
	
	private ModelMapper clienteModelMapper = new ModelMapper();
	private ModelMapper pedidoModelMapper = new ModelMapper();
	
	private static EntregadorDAO entregadorDAO;
	private static ClienteDAO clienteDAO;
	private static PedidoDAO pedidoDAO;
	
	private static PedidoNegocio pedidoNegocio;
	
	@BeforeAll()
	static void beforeAll() {
		entregadorDAO = assertDoesNotThrow(() -> new EntregadorDAO(FabricaEntityManager.getEntityManagerTeste()));
		clienteDAO = assertDoesNotThrow(() -> new ClienteDAO(FabricaEntityManager.getEntityManagerTeste()));
		pedidoDAO = assertDoesNotThrow(() -> new PedidoDAO(FabricaEntityManager.getEntityManagerTeste()));
		pedidoNegocio = new PedidoNegocio(pedidoDAO, clienteDAO);
	}

	@BeforeEach()
	void beforeEach() {
		List<Pedido> listaPedido = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()));
		for (Pedido pedidoDoCliente : listaPedido) {
			pedidoDAO.beginTransaction();
			assertDoesNotThrow(() -> pedidoDAO.excluir(pedidoDoCliente));
			pedidoDAO.commitTransaction();
		}
	}
	
	@Test
	void testClienteNegocio_Inserir() {
		assertDoesNotThrow(() -> pedidoNegocio.inserir(pedidoToDTO(novoPedido())));
	}
	
	@Test
	void testClienteNegocio_Alterar() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();

		Pedido pedidoLocalizado = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()).get(0));
		pedidoLocalizado.setHoraPedido(LocalTime.now());
		
		assertDoesNotThrow(() -> pedidoNegocio.alterar(pedidoToDTO(pedidoLocalizado)));
	}
	
	@Test
	void testPedidoNegocio_PesquisaPorDataProducao() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		List<PedidoDTO> listaPedidoDTO = assertDoesNotThrow(() -> pedidoNegocio.pesquisaPorDataProducao(LocalDate.now(), LocalDate.now()));
		assertFalse(listaPedidoDTO.isEmpty());
		
	}
	
	@Test
	void testPedidoNegocio_PesquisaPorEstado() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		List<PedidoDTO> listaPedidoDTO = assertDoesNotThrow(() -> pedidoNegocio.pesquisaPorEstado(EstadoPedidoDTO.REGISTRADO));
		assertFalse(listaPedidoDTO.isEmpty());
	}
	
	@Test
	void testPedidoNegocio_PesquisaPorCliente() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		List<PedidoDTO> listaPedidoDTO = assertDoesNotThrow(() -> pedidoNegocio.pesquisaPorCliente(clienteToDTO(resolveDependenciaCliente())));
		assertFalse(listaPedidoDTO.isEmpty());
	}
	
	@Test
	void testPedidoNegocio_MudarPedidoParaProducao() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		Pedido pedidoLocalizado = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()).get(0));
		
		assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaProducao(pedidoToDTO(pedidoLocalizado)));
		assertEquals(EstadoPedidoDTO.PRODUCAO,  assertDoesNotThrow(() -> pedidoDAO.buscarPorCodigo(pedidoLocalizado.getCodigo())).getEstado());
	}
	
	@Test
	void testPedidoNegocio_MudarPedidoParaPronto() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		Pedido pedidoLocalizado = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()).get(0));
		pedidoLocalizado.setEstado(EstadoPedidoDTO.PRODUCAO);
		
		assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaPronto(pedidoToDTO(pedidoLocalizado)));
		assertEquals(EstadoPedidoDTO.PRONTO,  assertDoesNotThrow(() -> pedidoDAO.buscarPorCodigo(pedidoLocalizado.getCodigo())).getEstado());
	}
	
	@Test
	void testPedidoNegocio_MudarPedidoParaEntrega() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		Pedido pedidoLocalizado = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()).get(0));
		pedidoLocalizado.setEstado(EstadoPedidoDTO.PRONTO);
		pedidoLocalizado.setEntregador(resolveDependenciaEntregador());
		
		assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaEntrega(pedidoToDTO(pedidoLocalizado)));
		assertEquals(EstadoPedidoDTO.ENTREGA,  assertDoesNotThrow(() -> pedidoDAO.buscarPorCodigo(pedidoLocalizado.getCodigo())).getEstado());
	}
	
	@Test
	void testPedidoNegocio_MudarPedidoParaConcluido() {
		pedidoDAO.beginTransaction();
		assertDoesNotThrow(() -> pedidoDAO.incluir(novoPedido()));
		pedidoDAO.commitTransaction();
		
		Pedido pedidoLocalizado = assertDoesNotThrow(() -> pedidoDAO.buscarPorCliente(resolveDependenciaCliente()).get(0));
		pedidoLocalizado.setEstado(EstadoPedidoDTO.ENTREGA);
		pedidoLocalizado.setEntregador(resolveDependenciaEntregador());
		
		assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaConcluido(pedidoToDTO(pedidoLocalizado)));
		assertEquals(EstadoPedidoDTO.CONCLUIDO,  assertDoesNotThrow(() -> pedidoDAO.buscarPorCodigo(pedidoLocalizado.getCodigo())).getEstado());
	}
	

	private Pedido novoPedido() {
		List<ItemPedido> listaItemPedido = new ArrayList<ItemPedido>();
		listaItemPedido.add(new ItemPedido());
		
		Pedido novoPedido = new Pedido();
		novoPedido.setDataPedido(LocalDate.now());
		novoPedido.setHoraPedido(LocalTime.MIN);
		novoPedido.setEstado(EstadoPedidoDTO.REGISTRADO);
		novoPedido.setListaItens(listaItemPedido);
		
		novoPedido.setCliente(resolveDependenciaCliente());
		
		return novoPedido;
	}
	
	Cliente resolveDependenciaCliente() {
		String CPF = "99999999999";
		Cliente clienteRetorno = assertDoesNotThrow(() -> clienteDAO.buscarPorCPF(CPF));
		
		if (clienteRetorno == null) {
			Cliente cliente = new Cliente();
			cliente.setNome("CLIENTE TESTE_" + CPF);
			cliente.setRG("RG");
			cliente.setCPF(CPF);
			cliente.setTelefone("TELEFONE");
			cliente.setLogradouro("LOGRADOURO");
			cliente.setNumero("NUMERO");
			cliente.setBairro("BAIRRO");
			cliente.setPontoReferencia("PONTO REFERENCIA");
			
			clienteDAO.beginTransaction();
			assertDoesNotThrow(() -> clienteDAO.incluir(cliente));
			clienteDAO.commitTransaction();
			
			clienteRetorno = assertDoesNotThrow(() -> clienteDAO.buscarPorCPF(CPF));
		}
		return clienteRetorno;
	}
	

	Entregador resolveDependenciaEntregador() {
		final String CPF = "11111111111";
		Entregador entregadorRetorno = assertDoesNotThrow(() -> entregadorDAO.buscarPorCPF(CPF));
		
		if (entregadorRetorno == null) {
			Entregador entregador = new Entregador();
			entregador.setCPF("11111111111");
			entregador.setRG("2222222222");
			entregador.setNome("ENTREGADOR DE TESTE");
			entregador.setTelefone("3333333333");

			entregadorDAO.beginTransaction();
			assertDoesNotThrow(() -> entregadorDAO.incluir(entregador));
			entregadorDAO.commitTransaction();
			
			entregadorRetorno = assertDoesNotThrow(() -> entregadorDAO.buscarPorCPF(CPF));
		}
		
		return entregadorRetorno;
	}
	
	private ClienteDTO clienteToDTO(Cliente cliente) {
		return clienteModelMapper.map(cliente, ClienteDTO.class);
	}

	public PedidoDTO pedidoToDTO(Pedido pedido) {
		return pedidoModelMapper.map(pedido, PedidoDTO.class);
	}

	public Pedido pedidoToEntity(PedidoDTO pedidoDTO) {
		return pedidoModelMapper.map(pedidoDTO, Pedido.class);
	}

}
