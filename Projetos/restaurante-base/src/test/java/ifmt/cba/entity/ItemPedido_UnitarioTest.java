package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemPedido_UnitarioTest {
	
	private ItemPedido itemPedido;
	
	@BeforeEach
	void beforeEach() {
		itemPedido = new ItemPedido();
		itemPedido.setQuantidadePorcao(1);
		itemPedido.setPreparoProduto(new PreparoProduto());
	}
	
    @Test
    void testItemPedido_Validar() {
    	assertEquals("", itemPedido.validar());
    }
    
    @Test
    void testItemPedido_Validar_PreparoProdutoInvalido() {
    	itemPedido.setPreparoProduto(null);
    	assertEquals("Item de Pedido nao relacionado a um preparo de produto", itemPedido.validar());
    }
    
    @Test
    void testItemPedido_Validar_QuantidadePorcaoInvalida() {
    	itemPedido.setQuantidadePorcao(0);
    	assertEquals("Quantidade de porcoes deve ser maior que zero", itemPedido.validar());
    }
}
