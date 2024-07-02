package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;

public class OrdemProducao_UnitarioTest {

	private static List<ItemOrdemProducao> listaItemOrdemProducao;
	private static Cardapio cardapio;
	
	private OrdemProducao ordemProducao;
	
	@BeforeAll
	static void beforeAll() {
		listaItemOrdemProducao = new ArrayList<ItemOrdemProducao>();
		listaItemOrdemProducao.add(new ItemOrdemProducao());
		
    	cardapio = new Cardapio();
    	cardapio.setNome("NOME CARDAPIO");
    	cardapio.setDescricao("DESCRICAO CARDAPIO");
	}
	
	@BeforeEach
	void beforeEach() {
		ordemProducao = new OrdemProducao();
		ordemProducao.setListaItens(listaItemOrdemProducao);
		ordemProducao.setCardapio(cardapio);
		ordemProducao.setEstado(EstadoOrdemProducaoDTO.REGISTRADA);
		
		// ALTERAMOS POR ACREDITAR SER ERRO DE NA IMPLEMENTACAO
			// VALIDO NA VALIDACAO ANTIGA
			ordemProducao.setDataProducao(LocalDate.of(1984, 05, 25)); 
				                     
		
			// VALIDO NA VALIDACAO CORRIGIDA
			ordemProducao.setDataProducao(LocalDate.now()); 
	}
	
    @Test
    void testOrdemProducao_Validar() {
    	assertEquals("", ordemProducao.validar());
    }
    
    @Test
    void testOrdemProducao_Validar_ListaItensInvalidos() {
    	ordemProducao.setListaItens(null);
    	assertEquals("Ordem de Producao deve ter pelo menos um item", ordemProducao.validar());
    }
    
    @Test
    void testOrdemProducao_Validar_DataProducaoInvalida() {
    	ordemProducao.setDataProducao(null);
    	assertEquals("Data de producao invalida", ordemProducao.validar());
    }
    
    @Test
    void testOrdemProducao_Validar_CardapioInvalido() {
    	ordemProducao.setCardapio(null);
    	assertEquals("Cardapio invalido", ordemProducao.validar());
    }
    
    @Test
    void testOrdemProducao_Validar_EstadoInvalido() {
    	ordemProducao.setEstado(null);
    	assertEquals("Estado da ordem invalido", ordemProducao.validar());
    }
}
