package ifmt.cba.negocio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.modelmapper.ModelMapper;

import ifmt.cba.dto.EstadoOrdemProducaoDTO;
import ifmt.cba.dto.OrdemProducaoDTO;
import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.entity.ItemOrdemProducao;
import ifmt.cba.entity.OrdemProducao;
import ifmt.cba.persistencia.OrdemProducaoDAO;
import ifmt.cba.persistencia.PersistenciaException;

public class OrdemProducaoNegocio {

	private ModelMapper modelMapper;
	private OrdemProducaoDAO ordemProducaoDAO;
	private ProdutoNegocio produtoNegocio;

	/*
	 * DA MANEIRA QUE FOI IMPLEMENTADO N"AO SERIA POSSIVEL MOCKAR O OBJETO PRODUTONEGOCIO 
	 * PENSAMOS EM DUAS ALTERNATIVAS, ENTENDER QUE FOI UMA NECESSIDADE IMPLEMENTAÇÃO E CONTINUAR OS TESTES ASSIM MESMO
	 * OU TRAZER O OJBETO PARA O CONSTRUTOR PARA MOCKA-LO, OPTAMOS POR MUDAR A IMPLEMENTAÇÃO
	 */
	public OrdemProducaoNegocio(OrdemProducaoDAO ordemProducaoDAO, ProdutoNegocio produtoNegocio) {
		this.ordemProducaoDAO = ordemProducaoDAO;
		this.produtoNegocio = produtoNegocio;
		this.modelMapper = new ModelMapper();
	}

	public void inserir(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {

		OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);
		String mensagemErros = ordemProducao.validar();

		if (!mensagemErros.isEmpty()) {
			throw new NegocioException(mensagemErros);
		}

		try {
			ordemProducaoDAO.beginTransaction();
			ordemProducaoDAO.incluir(ordemProducao);
			ordemProducaoDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			ordemProducaoDAO.rollbackTransaction();
			throw new NegocioException("Erro ao incluir ordem de producao - " + ex.getMessage());
		}
	}

	public void alterar(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {

		OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);
		String mensagemErros = ordemProducao.validar();
		if (!mensagemErros.isEmpty()) {
			throw new NegocioException(mensagemErros);
		}
		try {
			if (ordemProducaoDAO.buscarPorCodigo(ordemProducao.getCodigo()) == null) {
				throw new NegocioException("Nao existe essa ordem de producao");
			}
			ordemProducaoDAO.beginTransaction();
			ordemProducaoDAO.alterar(ordemProducao);
			ordemProducaoDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			ordemProducaoDAO.rollbackTransaction();
			throw new NegocioException("Erro ao alterar ordem de producao - " + ex.getMessage());
		}
	}

	public void excluir(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {

		OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);
		try {
			if (ordemProducaoDAO.buscarPorCodigo(ordemProducao.getCodigo()) == null) {
				throw new NegocioException("Nao existe essa ordem de producao");
			}
			ordemProducaoDAO.beginTransaction();
			ordemProducaoDAO.excluir(ordemProducao);
			ordemProducaoDAO.commitTransaction();
		} catch (PersistenciaException ex) {
			ordemProducaoDAO.rollbackTransaction();
			throw new NegocioException("Erro ao excluir ordem de producao - " + ex.getMessage());
		}
	}

	public List<OrdemProducaoDTO> pesquisaPorDataProducao(LocalDate dataInicial, LocalDate dataFinal)
			throws NegocioException {
		try {
			return this.toDTOAll(ordemProducaoDAO.buscarPorDataProducao(dataInicial, dataFinal));
		} catch (PersistenciaException ex) {
			throw new NegocioException("Erro ao pesquisar ordem de producao por data de producao - " + ex.getMessage());
		}
	}

	public List<OrdemProducaoDTO> pesquisaPorEstado(EstadoOrdemProducaoDTO estado) throws NegocioException {
		try {
			return this.toDTOAll(ordemProducaoDAO.buscarPorEstado(estado));
		} catch (PersistenciaException ex) {
			throw new NegocioException("Erro ao pesquisar ordem de producao pelo estado - " + ex.getMessage());
		}
	}

	public OrdemProducaoDTO pesquisaCodigo(int codigo) throws NegocioException {
		try {
			return this.toDTO(ordemProducaoDAO.buscarPorCodigo(codigo));
		} catch (PersistenciaException ex) {
			throw new NegocioException("Erro ao pesquisar ordem de producao pelo codigo - " + ex.getMessage());
		}
	}

	public List<OrdemProducaoDTO> toDTOAll(List<OrdemProducao> listaOrdemProducao) {
		List<OrdemProducaoDTO> listaDTO = new ArrayList<OrdemProducaoDTO>();

		for (OrdemProducao ordemProducao : listaOrdemProducao) {
			listaDTO.add(this.toDTO(ordemProducao));
		}
		return listaDTO;
	}

	public void processarOrdemProducao(OrdemProducaoDTO ordemProducaoDTO) throws NegocioException {

		OrdemProducao ordemProducao = this.toEntity(ordemProducaoDTO);

		if (ordemProducao.getEstado() == EstadoOrdemProducaoDTO.PROCESSADA) {
			throw new NegocioException("Ordem ja processada");
		}

		String mensagemErros = ordemProducao.validar();
		if (!mensagemErros.isEmpty()) {
			throw new NegocioException(mensagemErros);
		}

		ProdutoDTO produto;
		boolean processouItens = true;
		ordemProducaoDAO.beginTransaction();
		Iterator<ItemOrdemProducao> itensOrdem = ordemProducao.getListaItens().iterator();
		while (itensOrdem.hasNext() && processouItens) {
			ItemOrdemProducao itemOrdem = (ItemOrdemProducao) itensOrdem.next();
			produto = produtoNegocio.pesquisaCodigo(itemOrdem.getPreparoProduto().getProduto().getCodigo());
			if (produto.getEstoque() >= itemOrdem.getQuantidadePorcao()) {
				produto.setEstoque(produto.getEstoque() - itemOrdem.getQuantidadePorcao());
				produtoNegocio.alterar(produto);
			} else {
				processouItens = false;
			}
		}
		if (processouItens){
			ordemProducaoDTO.setEstado(EstadoOrdemProducaoDTO.PROCESSADA);
			//this.alterar(ordemProducaoDTO);
			try {
				ordemProducaoDAO.alterar(toEntity(ordemProducaoDTO));
				ordemProducaoDAO.commitTransaction();
			} catch (PersistenciaException ex) {
				ordemProducaoDAO.rollbackTransaction();
				throw new NegocioException("Erro ao processar a ordem de producao - " + ex.getMessage());
			}
		}else{
			ordemProducaoDAO.rollbackTransaction();
			throw new NegocioException("Existem produtos sem estoque suficiente para o processamento");
		}
	}

	public OrdemProducaoDTO toDTO(OrdemProducao ordemProducao) {
		return this.modelMapper.map(ordemProducao, OrdemProducaoDTO.class);
	}

	public OrdemProducao toEntity(OrdemProducaoDTO ordemProducaoDTO) {
		return this.modelMapper.map(ordemProducaoDTO, OrdemProducao.class);
	}
}
