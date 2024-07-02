package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PreparoProduto_UnitarioTest {
	
	private PreparoProduto preparoProduto;

	@BeforeEach
	void beforeEach() {
		preparoProduto = new PreparoProduto();
		preparoProduto.setTipoPreparo(new TipoPreparo());
		preparoProduto.setProduto(new Produto());
    	preparoProduto.setTempoPreparo(30);
    	preparoProduto.setValorPreparo(50.32f);
	}
	
    @Test
    void testPreparoProduto_Validar () {
    	assertEquals("", preparoProduto.validar());
    }
    
    @Test
    void testPreparoProduto_Validar_ProdutoInvalido() {
    	preparoProduto.setProduto(null);
    	assertEquals("Deve existir um produto relacionado", preparoProduto.validar());
    }
    
    @Test
    void testPreparoProduto_Validar_TempoPreparoInvalido() {
    	preparoProduto.setTempoPreparo(0);
    	assertEquals("Tempo de preparo invalido", preparoProduto.validar());
    }
    
    @Test
    void testPreparoProduto_Validar_TipoPreparoInvalido() {
    	preparoProduto.setTipoPreparo(null);
    	assertEquals("Deve existir um tipo de preparo relacionado", preparoProduto.validar());
    }
    
    @Test
    void testPreparoProduto_Validar_ValorPreparoInvalido() {
    	preparoProduto.setValorPreparo(-50.00f);
    	assertEquals("Valor de preparo invalido", preparoProduto.validar());
    }
}
