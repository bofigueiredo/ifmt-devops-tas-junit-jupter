package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class Produto_UnitarioTest {
	
	private static GrupoAlimentar grupoAlimentar;
	
	private Produto produto;
	
	@BeforeAll
	static void beforeAll() {
		grupoAlimentar = Mockito.mock(GrupoAlimentar.class);
		Mockito.when(grupoAlimentar.validar()).thenReturn("");
	}
	
	@BeforeEach
	void beforeEach() {
		produto = new Produto();
		produto.setGrupoAlimentar(grupoAlimentar);
		
		produto.setNome("PICANHA");
    	produto.setCustoUnidade(40f);
    	produto.setEstoque(20);
    	produto.setValorEnergetico(30);
	}
	
    @Test
    void testProduto_Validar() {
    	assertEquals("", produto.validar());
    }
    
    @Test
    void testProduto_Validar_EstoqueInvalido() {
    	produto.setEstoque(-1);
    	assertEquals("Estoque invalido", produto.validar());
    }
    
    @Test
    void testProduto_Validar_GrupoAlimentarInvalido() {
    	produto.setGrupoAlimentar(null);
    	assertEquals("Grupo alimentar invalido", produto.validar());
    }
    
    @Test
    void testProduto_Validar_NomeInvalido() {
    	produto.setNome(null);
    	assertEquals("Nome invalido", produto.validar());
    }
    
    @Test
    void testProduto_Validar_CustoUnidadeInvalido() {
    	produto.setCustoUnidade(0);
    	assertEquals("Custo por unidade invalido", produto.validar());
    }
    
    @Test
    void testProduto_Validar_ValorEnergeticoInvalido() {
    	produto.setValorEnergetico(-10);
    	assertEquals("Valor energetico invalido", produto.validar());
    }
}
