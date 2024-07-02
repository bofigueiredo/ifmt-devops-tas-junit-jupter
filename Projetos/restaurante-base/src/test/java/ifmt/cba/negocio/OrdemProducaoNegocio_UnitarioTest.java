package ifmt.cba.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ifmt.cba.dto.CardapioDTO;
import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.dto.ItemOrdemProducaoDTO;
import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.entity.OrdemProducao;
import ifmt.cba.persistencia.OrdemProducaoDAO;

public class OrdemProducaoNegocio_UnitarioTest {
	
	private static CardapioDTO cardapioDTO;
	private static List<ItemOrdemProducaoDTO> listItemOrdemProducaoDTO;
	
	private OrdemProducaoDAO ordemProducaoDAO = Mockito.mock(OrdemProducaoDAO.class);
	private ProdutoNegocio produtoNegocio = Mockito.mock(ProdutoNegocio.class);
	
	private OrdemProducaoNegocio ordemProducaoNegocio = new OrdemProducaoNegocio(ordemProducaoDAO, produtoNegocio); 
	
	private OrdemProducaoDTO ordemProducaoDTO;
	
	@BeforeAll
	static void boforeAll() {
		PreparoProdutoDTO preparoProdutoDTO = new PreparoProdutoDTO();
		preparoProdutoDTO.setProduto(new ProdutoDTO());
		
	    ItemOrdemProducaoDTO itemOrdemProducaoDTO = new ItemOrdemProducaoDTO();
	    itemOrdemProducaoDTO.setPreparoProduto(preparoProdutoDTO);
	    itemOrdemProducaoDTO.setQuantidadePorcao(5);
	    
		listItemOrdemProducaoDTO = new ArrayList<ItemOrdemProducaoDTO>();
		listItemOrdemProducaoDTO.add(itemOrdemProducaoDTO);
		listItemOrdemProducaoDTO.add(itemOrdemProducaoDTO);
		
		cardapioDTO = new CardapioDTO();
		cardapioDTO.setNome("NOME CARDAPIO");
		cardapioDTO.setDescricao("DESCRICAO CARDAPIO");
	}
	
	@BeforeEach
	void beforeEach() {
		ordemProducaoDTO = new OrdemProducaoDTO();
		ordemProducaoDTO.setListaItens(listItemOrdemProducaoDTO);
		ordemProducaoDTO.setDataProducao(null);
		
		ordemProducaoDTO.setCardapio(cardapioDTO);
		ordemProducaoDTO.setEstado(EstadoOrdemProducaoDTO.REGISTRADA);
		ordemProducaoDTO.setDataProducao(LocalDate.now());
	}
	
    @Test
    void testOrdemProducaoNegocio_Inserir() {
    	assertDoesNotThrow(() -> ordemProducaoNegocio.inserir(ordemProducaoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(ordemProducaoDAO).incluir(any()));
    }

    @Test
    void testOrdemProducaoNegocio_Alterar() {
    	assertDoesNotThrow(() -> Mockito.when(ordemProducaoDAO.buscarPorCodigo(anyInt())).thenReturn(new OrdemProducao()));
    	
    	assertDoesNotThrow(() -> ordemProducaoNegocio.alterar(ordemProducaoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(ordemProducaoDAO).alterar(any()));
    }

    @Test
    void testOrdemProducaoNegocio_Excluir() {
    	assertDoesNotThrow(() -> Mockito.when(ordemProducaoDAO.buscarPorCodigo(anyInt())).thenReturn(new OrdemProducao()));
    	
    	assertDoesNotThrow(() -> ordemProducaoNegocio.excluir(ordemProducaoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(ordemProducaoDAO).excluir(any()));
    }

    @Test
    void testOrdemProducaoNegocio_ProcessarOrdemProducao() {
    	ProdutoDTO produtoDTO =  new ProdutoDTO();
		produtoDTO.setEstoque(10);
		
    	assertDoesNotThrow(() -> Mockito.when(produtoNegocio.pesquisaCodigo(anyInt())).thenReturn(produtoDTO));
    	assertDoesNotThrow(() -> Mockito.when(ordemProducaoDAO.buscarPorCodigo(anyInt())).thenReturn(new OrdemProducao()));
    	
    	assertDoesNotThrow(() -> ordemProducaoNegocio.processarOrdemProducao(ordemProducaoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(produtoNegocio, Mockito.times(2)).alterar(any()));
    	assertDoesNotThrow(() -> Mockito.verify(ordemProducaoDAO).alterar(any()));
    }
}
