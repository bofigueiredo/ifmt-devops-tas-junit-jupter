package ifmt.cba.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.dto.TipoPreparoDTO;
import ifmt.cba.entity.PreparoProduto;
import ifmt.cba.persistencia.PreparoProdutoDAO;

public class PreparoProdutoNegocio_UnitarioTest {
	
	private PreparoProdutoDAO preparoProdutoDAO = Mockito.mock(PreparoProdutoDAO.class);
	private PreparoProdutoNegocio preparoProdutoNegocio = new PreparoProdutoNegocio(preparoProdutoDAO);
	
	private PreparoProdutoDTO preparoProdutoDTO; 
	
	@BeforeEach
	void beforeEach() {
		preparoProdutoDTO = new PreparoProdutoDTO();
		preparoProdutoDTO.setTipoPreparo(new TipoPreparoDTO());
		preparoProdutoDTO.setProduto(new ProdutoDTO());
		preparoProdutoDTO.setTempoPreparo(30);
		preparoProdutoDTO.setValorPreparo(50.32f);
	}
	
	
    @Test
    void testPreparoProdutoNegocio_Inserir() {
    	assertDoesNotThrow(() -> preparoProdutoNegocio.inserir(preparoProdutoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(preparoProdutoDAO).incluir(any()));
    }

    @Test
    void testPreparoProdutoNegocio_Alterar() {
    	assertDoesNotThrow(() -> Mockito.when(preparoProdutoDAO.buscarPorCodigo(anyInt())).thenReturn(new PreparoProduto()));
    	
    	assertDoesNotThrow(() -> preparoProdutoNegocio.alterar(preparoProdutoDTO));
    	assertDoesNotThrow(() -> Mockito.verify(preparoProdutoDAO).alterar(any()));
    }

    @Test
    void testPreparoProdutoNegocio_Excluir() {
    	assertDoesNotThrow(() -> Mockito.when(preparoProdutoDAO.buscarPorCodigo(anyInt())).thenReturn(new PreparoProduto()));
    	
    	assertDoesNotThrow(() -> preparoProdutoNegocio.excluir(preparoProdutoDTO.getCodigo()));
    	assertDoesNotThrow(() -> Mockito.verify(preparoProdutoDAO).excluir(any()));
    }
}
