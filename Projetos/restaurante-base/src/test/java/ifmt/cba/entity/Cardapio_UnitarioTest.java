package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Cardapio_UnitarioTest {
	
	private static List<PreparoProduto> listaPreparoProduto;
	
	private Cardapio cardapio;
	
	@BeforeAll
	static void beforeAll() {
		listaPreparoProduto = new ArrayList<PreparoProduto>();
    	listaPreparoProduto.add(new PreparoProduto());
	}
	
	@BeforeEach
	void beforeEach() {
    	cardapio = new Cardapio();
    	cardapio.setNome("NOME CARDAPIO");
    	cardapio.setDescricao("DESCRICAO CARDAPIO");
    	cardapio.setListaPreparoProduto(listaPreparoProduto);
	}

    @Test
    void testCardapio_Validar() {
    	assertEquals("", cardapio.validar());
    }
    
    @Test
    void testCardapio_Validar_NomeInvalido() {
    	cardapio.setNome("NO");
    	assertEquals("Nome invalido", cardapio.validar());
    }
    
    @Test
    void testCardadio_Validar_ItensInvalido() {
    	cardapio.setListaPreparoProduto(null);
    	assertEquals("Cardapio sem itens", cardapio.validar());
    }
    
    @Test
    void testCardadio_Validar_DescricaoInvalido() {
    	cardapio.setDescricao(null);
    	assertEquals("Descricao invalida", cardapio.validar());
    }
}
