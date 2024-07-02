package ifmt.cba.negocio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.entity.Cardapio;
import ifmt.cba.entity.GrupoAlimentar;
import ifmt.cba.entity.ItemOrdemProducao;
import ifmt.cba.entity.OrdemProducao;
import ifmt.cba.entity.PreparoProduto;
import ifmt.cba.entity.Produto;
import ifmt.cba.persistencia.CardapioDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.GrupoAlimentarDAO;
import ifmt.cba.persistencia.OrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.ProdutoDAO;

public class OrdemProducaoNegocio_OrdemProducaoDAO_IntegracaoTest {
	
	private ModelMapper ordemProducaoModelMapper = new ModelMapper();

	private static final LocalDate LOCAL_DATE_TESTE = LocalDate.now();
	
	private static GrupoAlimentarDAO grupoAlimentarDAO;
	private static ProdutoDAO produtoDAO;
	private static PreparoProdutoDAO preparoProdutoDAO;
	private static ProdutoNegocio produtoNegocio;
	
	private static CardapioDAO cardapioDAO;
	private static OrdemProducaoDAO ordemProducaoDAO;
	private static OrdemProducaoNegocio  ordemProducaoNegocio;
	
	@BeforeAll()
	static void beforeAll() {
		grupoAlimentarDAO = assertDoesNotThrow(() -> new GrupoAlimentarDAO(FabricaEntityManager.getEntityManagerTeste()));
		produtoDAO = assertDoesNotThrow(() -> new ProdutoDAO(FabricaEntityManager.getEntityManagerTeste()));
		preparoProdutoDAO = assertDoesNotThrow(() -> new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerTeste()));
		produtoNegocio = new ProdutoNegocio(produtoDAO);
		
		cardapioDAO = assertDoesNotThrow(() -> new CardapioDAO(FabricaEntityManager.getEntityManagerTeste()));
		ordemProducaoDAO = assertDoesNotThrow(() -> new OrdemProducaoDAO(FabricaEntityManager.getEntityManagerTeste()));
		ordemProducaoNegocio = new OrdemProducaoNegocio(ordemProducaoDAO, produtoNegocio);
	}
	

	@BeforeEach()
	void beforeEach() {
		List<OrdemProducao> listaOrdemProducao = assertDoesNotThrow(() -> ordemProducaoDAO.buscarPorDataProducao(LOCAL_DATE_TESTE, LOCAL_DATE_TESTE));
		for (OrdemProducao ordemProducao : listaOrdemProducao) {
			ordemProducaoDAO.beginTransaction();
			assertDoesNotThrow(() -> ordemProducaoDAO.excluir(ordemProducao));
			ordemProducaoDAO.commitTransaction();
		}
	}

	@Test
	void testOrdemProducaoNegocio_Inserir() {
		assertDoesNotThrow(() -> ordemProducaoNegocio.inserir(ordemProducaoToDTO(novaOrdemProducao())));
	}
	
	
	@Test
	void testOrdemProducaoNegocio_Alterar() {
		ordemProducaoDAO.beginTransaction();
		assertDoesNotThrow(() -> ordemProducaoDAO.incluir(novaOrdemProducao()));
		ordemProducaoDAO.commitTransaction();

		OrdemProducao ordemProducaoLocalizada = assertDoesNotThrow(() -> ordemProducaoDAO.buscarPorDataProducao(LOCAL_DATE_TESTE, LOCAL_DATE_TESTE).get(0));
		ordemProducaoLocalizada.setEstado(EstadoOrdemProducaoDTO.PROCESSADA);
		
		assertDoesNotThrow(() -> ordemProducaoNegocio.alterar(ordemProducaoToDTO(ordemProducaoLocalizada)));
	}
	
	@Test
	void testOrdemProducaoNegocio_PesquisaPorDataProducao() {
		ordemProducaoDAO.beginTransaction();
		assertDoesNotThrow(() -> ordemProducaoDAO.incluir(novaOrdemProducao()));
		ordemProducaoDAO.commitTransaction();
		
		List<OrdemProducaoDTO> listaOrdemProducaoDTO = assertDoesNotThrow(() -> ordemProducaoNegocio.pesquisaPorDataProducao(LOCAL_DATE_TESTE, LOCAL_DATE_TESTE));
		assertFalse(listaOrdemProducaoDTO.isEmpty());
		
	}
	
	@Test
	void testOrdemProducaoNegocio_PesquisaPorEstado() {
		ordemProducaoDAO.beginTransaction();
		assertDoesNotThrow(() -> ordemProducaoDAO.incluir(novaOrdemProducao()));
		ordemProducaoDAO.commitTransaction();
		
		List<OrdemProducaoDTO> listaOrdemProducaoDTO = assertDoesNotThrow(() -> ordemProducaoNegocio.pesquisaPorEstado(EstadoOrdemProducaoDTO.REGISTRADA));
		assertFalse(listaOrdemProducaoDTO.isEmpty());
	}
	
	@Test
	void testOrdemProducaoNegocio_ProcessarOrdemProducao() {
		ordemProducaoDAO.beginTransaction();
		assertDoesNotThrow(() -> ordemProducaoDAO.incluir(novaOrdemProducao()));
		ordemProducaoDAO.commitTransaction();
		
		OrdemProducao ordemProducaoLocalizada = assertDoesNotThrow(() -> ordemProducaoDAO.buscarPorDataProducao(LOCAL_DATE_TESTE, LOCAL_DATE_TESTE).get(0));
		
		assertDoesNotThrow(() -> ordemProducaoNegocio.processarOrdemProducao(ordemProducaoToDTO(ordemProducaoLocalizada)));
	}
	

	private OrdemProducao novaOrdemProducao() {
		List<ItemOrdemProducao> listaItemOrdemProducao = new ArrayList<ItemOrdemProducao>();
		ItemOrdemProducao itemOrdemProducao = new ItemOrdemProducao();
		itemOrdemProducao.setPreparoProduto(resolveDependenciaPreparoProduto());
		itemOrdemProducao.setQuantidadePorcao(5);
		
		listaItemOrdemProducao.add(itemOrdemProducao);
		
		OrdemProducao ordemProducao = new OrdemProducao();
		ordemProducao.setListaItens(listaItemOrdemProducao);
		ordemProducao.setEstado(EstadoOrdemProducaoDTO.REGISTRADA);
		ordemProducao.setDataProducao(LOCAL_DATE_TESTE );
		
		ordemProducao.setCardapio(resolveDependenciaCardapio());
		
		return ordemProducao;
	}
	
	private Cardapio resolveDependenciaCardapio() {
		final String NOME_CARDAPIO = "NOME CARDAPIO";
		
		Cardapio cardapioRetorno;
		try {
			cardapioRetorno = cardapioDAO.buscarPorNome(NOME_CARDAPIO);
		} catch (PersistenciaException e) {
			Cardapio cardapio = new Cardapio();
	    	cardapio.setNome(NOME_CARDAPIO);
	    	cardapio.setDescricao("DESCRICAO CARDAPIO");
			
	    	cardapioDAO.beginTransaction();
			assertDoesNotThrow(() -> cardapioDAO.incluir(cardapio));
			cardapioDAO.commitTransaction();
			
			cardapioRetorno = assertDoesNotThrow(() -> cardapioDAO.buscarPorNome(NOME_CARDAPIO));
		}
		return cardapioRetorno;
	}
	
	private PreparoProduto resolveDependenciaPreparoProduto() {
		final String NOME_PREPARO_PRODUTO = "NOME PREPARO PRODUTO";
		
		PreparoProduto preparoProdutoRetorno;
		try {
			preparoProdutoRetorno = preparoProdutoDAO.buscarPorParteNome(NOME_PREPARO_PRODUTO).get(0);
		} catch (Exception e) {
			PreparoProduto preparoProduto = new PreparoProduto();
			preparoProduto.setNome(NOME_PREPARO_PRODUTO);
			preparoProduto.setTempoPreparo(10);
			preparoProduto.setValorPreparo(1);
			preparoProduto.setProduto(resolveDependenciaProduto());
			
			preparoProdutoDAO.beginTransaction();
			assertDoesNotThrow(() -> preparoProdutoDAO.incluir(preparoProduto));
			preparoProdutoDAO.commitTransaction();
			
			preparoProdutoRetorno = assertDoesNotThrow(() -> preparoProdutoDAO.buscarPorParteNome(NOME_PREPARO_PRODUTO)).get(0);
		}
		return preparoProdutoRetorno;
	}
	
	private Produto resolveDependenciaProduto() {
		final String NOME_PRODUTO = "NOME PRODUTO";
		
		Produto produtoRetorno;
		try {
			produtoRetorno = produtoDAO.buscarPorParteNome(NOME_PRODUTO).get(0);
		} catch (Exception e) {
			Produto produto = new Produto();
			produto.setNome(NOME_PRODUTO);
			produto.setCustoUnidade(10f);
			produto.setEstoque(100);
			produto.setEstoqueMinimo(0);
			produto.setGrupoAlimentar(resolveDependenciaGrupoAlimentar());
			produto.setValorEnergetico(50);
			
			produtoDAO.beginTransaction();
			assertDoesNotThrow(() -> produtoDAO.incluir(produto));
			produtoDAO.commitTransaction();
			
			produtoRetorno = assertDoesNotThrow(() -> produtoDAO.buscarPorParteNome(NOME_PRODUTO)).get(0);
		}
		return produtoRetorno;
	}
	
	private GrupoAlimentar resolveDependenciaGrupoAlimentar() {
		final String NOME_GRUPO = "NOME GRUPO";
		
		GrupoAlimentar grupoAlimentaroRetorno;
		try {
			grupoAlimentaroRetorno = grupoAlimentarDAO.buscarPorParteNome(NOME_GRUPO).get(0);
		} catch (Exception e) {
			GrupoAlimentar grupoAlimentar = new GrupoAlimentar();
			grupoAlimentar.setNome(NOME_GRUPO);
			
			grupoAlimentarDAO.beginTransaction();
			assertDoesNotThrow(() -> grupoAlimentarDAO.incluir(grupoAlimentar));
			grupoAlimentarDAO.commitTransaction();
			
			grupoAlimentaroRetorno = assertDoesNotThrow(() -> grupoAlimentarDAO.buscarPorParteNome(NOME_GRUPO)).get(0);
		}
		return grupoAlimentaroRetorno;
	}
	
	private OrdemProducaoDTO ordemProducaoToDTO(OrdemProducao ordemProducao) {
		return ordemProducaoModelMapper.map(ordemProducao, OrdemProducaoDTO.class);
	}

}
