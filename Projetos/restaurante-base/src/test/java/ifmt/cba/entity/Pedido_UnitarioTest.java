package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ifmt.cba.dto.EstadoPedidoDTO;

public class Pedido_UnitarioTest {
	
	private static List<ItemPedido> listaItemPedido;
	
	private static final LocalTime LOCALTIME_0_HORAS = LocalTime.MIN;
	private static final LocalTime LOCALTIME_0_HORAS_1_NANOSEGUNDO = LocalTime.of(0, 0, 0, 1);
	private static final LocalTime LOCALTIME_0_HORAS_2_NANOSEGUNDO = LocalTime.of(0, 0, 0, 2);
	private static final LocalTime LOCALTIME_0_HORAS_3_NANOSEGUNDO = LocalTime.of(0, 0, 0, 3);

	private Pedido pedido;
	
	@BeforeAll
	static void beforeAll() {
		listaItemPedido = new ArrayList<ItemPedido>();
		listaItemPedido.add(new ItemPedido());
	}
	
	@BeforeEach
	void beforeEach() {
		pedido = new Pedido();
		pedido.setDataPedido(LocalDate.now());
		pedido.setHoraPedido(LOCALTIME_0_HORAS);
		pedido.setCliente(new Cliente());
		pedido.setEstado(EstadoPedidoDTO.REGISTRADO);
		pedido.setListaItens(listaItemPedido);
	}
	
    @Test
    void testPedido_Validar() {
    	assertEquals("", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_DataPedidoInvalido() {
    	pedido.setDataPedido(null);
    	assertEquals("Data do pedido invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_ClienteInvalido() {
    	pedido.setCliente(null);
    	assertEquals("Cliente do pedido invalido", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_HoraPedidoInvalida() {
    	pedido.setHoraPedido(null);
    	assertEquals("Hora do pedido invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_HoraProducaoInvalida() {
    	pedido.setHoraPedido(LOCALTIME_0_HORAS_2_NANOSEGUNDO);
    	pedido.setHoraProducao(LOCALTIME_0_HORAS_1_NANOSEGUNDO);
    	assertEquals("Hora de producao invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_HoraProntoInvalida() {
    	pedido.setHoraPedido(LOCALTIME_0_HORAS);
    	pedido.setHoraProducao(LOCALTIME_0_HORAS_2_NANOSEGUNDO);
    	pedido.setHoraPronto(LOCALTIME_0_HORAS_1_NANOSEGUNDO);
    	assertEquals("Hora de conclusao da producao invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_HoraEntregaInvalida() {
    	pedido.setHoraPedido(LOCALTIME_0_HORAS);
    	pedido.setHoraProducao(LOCALTIME_0_HORAS_1_NANOSEGUNDO);
    	pedido.setHoraPronto(LOCALTIME_0_HORAS_2_NANOSEGUNDO);
    	pedido.setHoraEntrega(LOCALTIME_0_HORAS_1_NANOSEGUNDO);
    	assertEquals("Hora de inicio de entrega invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_HoraFinalizadoInvalida() {
    	pedido.setHoraPedido(LOCALTIME_0_HORAS);
    	pedido.setHoraProducao(LOCALTIME_0_HORAS_1_NANOSEGUNDO);
    	pedido.setHoraPronto(LOCALTIME_0_HORAS_2_NANOSEGUNDO);
    	pedido.setHoraEntrega(LOCALTIME_0_HORAS_3_NANOSEGUNDO);
    	pedido.setHoraFinalizado(LOCALTIME_0_HORAS_2_NANOSEGUNDO);
    	assertEquals("Hora de conclusao entrega invalida", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_EstadoInvalido() {
    	pedido.setEstado(null);
    	assertEquals("Estado do pedido invalido", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_EntregadorEntregaInvalido() {
    	pedido.setEstado(EstadoPedidoDTO.ENTREGA);
    	pedido.setEntregador(null);
    	assertEquals("Quando o pedido estiver nos estados de ENTREGA OU CONCLUIDO, deve existir um entregador associado", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_EntregadorConcluidoInvalido() {
    	pedido.setEstado(EstadoPedidoDTO.CONCLUIDO);
    	pedido.setEntregador(null);
    	assertEquals("Quando o pedido estiver nos estados de ENTREGA OU CONCLUIDO, deve existir um entregador associado", pedido.validar());
    }
    
    @Test
    void testPedido_Validar_ListaItensInvalida() {
    	pedido.setListaItens(null);
    	assertEquals("Pedido sem itens", pedido.validar());
    }
    
}
