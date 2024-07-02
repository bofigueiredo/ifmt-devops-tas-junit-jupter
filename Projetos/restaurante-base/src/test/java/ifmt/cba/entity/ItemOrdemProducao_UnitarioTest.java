package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ItemOrdemProducao_UnitarioTest {

	private ItemOrdemProducao itemOrdemProducao;
	
	@BeforeEach
	void beforeEach() {
		itemOrdemProducao = new ItemOrdemProducao();
		itemOrdemProducao.setPreparoProduto(new PreparoProduto());
		itemOrdemProducao.setQuantidadePorcao(2);
	}
	
    @Test
    void testItemOrdemProducao_Validar() {
    	assertEquals("", itemOrdemProducao.validar());
    }
    
    @Test
    void testItemOrdemProducao_Validar_PreparoProdutoInvalido() {
    	itemOrdemProducao.setPreparoProduto(null);
    	assertEquals("Item de Ordem de Producao nao relacionado a um preparo de produto", itemOrdemProducao.validar());
    }
    
    @Test
    void testItemOrdemProducao_Validar_QuantidadePorcaoInvalida() {
    	itemOrdemProducao.setQuantidadePorcao(0);
    	assertEquals("Quantidade de porcoes deve ser maior que zero", itemOrdemProducao.validar());
    }
}
