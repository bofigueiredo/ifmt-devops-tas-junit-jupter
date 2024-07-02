package ifmt.cba.negocio;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EntregadorDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.entity.Pedido;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.PedidoDAO;

public class PedidoNegocio_UnitarioTest {
	
	private static List<ItemPedidoDTO> listaItemPedido;
	
	private PedidoDAO pedidoDAO = Mockito.mock(PedidoDAO.class);
	private ClienteDAO clienteDAO = Mockito.mock(ClienteDAO.class);
	
	private PedidoNegocio pedidoNegocio = new PedidoNegocio(pedidoDAO, clienteDAO);
	
	private PedidoDTO pedidoDTOMockado = Mockito.mock(PedidoDTO.class);

	@BeforeAll
	static void beforeAll() {
		listaItemPedido = new ArrayList<ItemPedidoDTO>();
		listaItemPedido.add(new ItemPedidoDTO());
	}
	
	@BeforeEach
	void beforeEach() {
		pedidoDTOMockado = Mockito.mock(PedidoDTO.class);
    	Mockito.when(pedidoDTOMockado.getEstado()).thenReturn(EstadoPedidoDTO.REGISTRADO);
    	Mockito.when(pedidoDTOMockado.getDataPedido()).thenReturn(LocalDate.now());
    	Mockito.when(pedidoDTOMockado.getHoraPedido()).thenReturn(LocalTime.MIN);
    	Mockito.when(pedidoDTOMockado.getCliente()).thenReturn(new ClienteDTO());
    	Mockito.when(pedidoDTOMockado.getListaItens()).thenReturn(listaItemPedido);
	}

    @Test
    void testPedidoNegocio_Inserir() {
    	assertDoesNotThrow(() -> pedidoNegocio.inserir(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).incluir(any()));
    }
    
    @Test
    void testPedidoNegocio_Alterar() {
    	assertDoesNotThrow(() -> Mockito.when(pedidoDAO.buscarPorCodigo(anyInt())).thenReturn(new Pedido()));
    	
    	assertDoesNotThrow(() -> pedidoNegocio.alterar(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).alterar(any()));
    }
    
    
    @Test
    void testPedidoNegocio_PesquisaPorCliente_ClienteInexsistente() {
    	assertDoesNotThrow(() -> Mockito.when(clienteDAO.buscarPorCodigo(anyInt())).thenReturn(null));
    	assertThrows(NegocioException.class, () -> pedidoNegocio.pesquisaPorCliente(new ClienteDTO()));
    }

    @Test
    void testPedidoNegocio_MudarPedidoParaProducao() {
    	assertDoesNotThrow(() -> Mockito.when(pedidoDAO.buscarPorCodigo(anyInt())).thenReturn(new Pedido()));
    	Mockito.when(pedidoDTOMockado.getEstado()).thenReturn(EstadoPedidoDTO.REGISTRADO);
    	
    	assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaProducao(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDTOMockado).setEstado(EstadoPedidoDTO.PRODUCAO));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).alterar(any()));
    }

    @Test
    void testPedidoNegocio_MudarPedidoParaPronto() {
    	assertDoesNotThrow(() -> Mockito.when(pedidoDAO.buscarPorCodigo(anyInt())).thenReturn(new Pedido()));
    	Mockito.when(pedidoDTOMockado.getEstado()).thenReturn(EstadoPedidoDTO.PRODUCAO);
    	
    	assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaPronto(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDTOMockado).setEstado(EstadoPedidoDTO.PRONTO));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).alterar(any()));
    }

    @Test
    void testPedidoNegocio_MudarPedidoParaEntrega() {
    	assertDoesNotThrow(() -> Mockito.when(pedidoDAO.buscarPorCodigo(anyInt())).thenReturn(new Pedido()));
    	Mockito.when(pedidoDTOMockado.getEstado()).thenReturn(EstadoPedidoDTO.PRONTO);
    	Mockito.when(pedidoDTOMockado.getEntregador()).thenReturn(new EntregadorDTO());
    	
    	assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaEntrega(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDTOMockado).setEstado(EstadoPedidoDTO.ENTREGA));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).alterar(any()));
    }

    @Test
    void testPedidoNegocio_MudarPedidoParaConcluido() {
    	assertDoesNotThrow(() -> Mockito.when(pedidoDAO.buscarPorCodigo(anyInt())).thenReturn(new Pedido()));
    	Mockito.when(pedidoDTOMockado.getEstado()).thenReturn(EstadoPedidoDTO.ENTREGA);
    	Mockito.when(pedidoDTOMockado.getEntregador()).thenReturn(new EntregadorDTO());
    	
    	assertDoesNotThrow(() -> pedidoNegocio.mudarPedidoParaConcluido(pedidoDTOMockado));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDTOMockado).setEstado(EstadoPedidoDTO.CONCLUIDO));
    	assertDoesNotThrow(() -> Mockito.verify(pedidoDAO).alterar(any()));
    }

}
