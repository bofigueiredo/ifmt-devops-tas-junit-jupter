package ifmt.cba.execucao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EntregadorDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.GrupoAlimentarDTO;
import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.dto.TipoPreparoDTO;
import ifmt.cba.negocio.ClienteNegocio;
import ifmt.cba.negocio.EntregadorNegocio;
import ifmt.cba.negocio.GrupoAlimentarNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.negocio.PreparoProdutoNegocio;
import ifmt.cba.negocio.ProdutoNegocio;
import ifmt.cba.negocio.TipoPreparoNegocio;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.EntregadorDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.GrupoAlimentarDAO;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.persistencia.PreparoProdutoDAO;
import ifmt.cba.persistencia.ProdutoDAO;
import ifmt.cba.persistencia.TipoPreparoDAO;

public class AppInserirCargaInicial {
    public static void main(String[] args) {

        try {
            
        	inserirEntregador();
        	inserirClienteCPF("12345678909");
        	inserirTipoPreparo();
        	inserirGrupoAlimentar();
        	inserirProduto();
        	inserirPreparoProduto();
        	
        	inserirPedido();
        	
        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }

	private static void inserirEntregador() throws PersistenciaException, NegocioException {
        EntregadorDAO entregadorDAO = new EntregadorDAO(FabricaEntityManager.getEntityManagerProducao());
        EntregadorNegocio entregadorNegocio = new EntregadorNegocio(entregadorDAO);

        EntregadorDTO entregadorDTO = new EntregadorDTO();
        entregadorDTO.setNome("Entregador 01");
        entregadorDTO.setTelefone("65 99999-7070");
        entregadorDTO.setRG("456789-1");
        entregadorDTO.setCPF("234.432.567-12");

        entregadorNegocio.inserir(entregadorDTO);
    }
    
    public static void inserirClienteCPF(String CPF) throws PersistenciaException, NegocioException {
        ClienteDAO clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
        PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO, pedidoDAO);

        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Cliente 01");
        clienteDTO.setCPF(CPF);
        clienteDTO.setRG("234567-9");
        clienteDTO.setTelefone("65 99999-7070");
        clienteDTO.setLogradouro("Rua das flores");
        clienteDTO.setNumero("123");
        clienteDTO.setPontoReferencia("Proximo a nada");
        clienteDTO.setBairro("Centro");
        clienteNegocio.inserir(clienteDTO);
    }
    
    private static void  inserirTipoPreparo() throws PersistenciaException, NegocioException {
    	TipoPreparoDAO tipoPreparoDAO = new TipoPreparoDAO(FabricaEntityManager.getEntityManagerProducao());
        TipoPreparoNegocio tipoPreparoNegocio = new TipoPreparoNegocio(tipoPreparoDAO);

        TipoPreparoDTO tipoPreparoDTO = new TipoPreparoDTO();
        tipoPreparoDTO.setDescricao("Cozimento em agua");
        tipoPreparoNegocio.inserir(tipoPreparoDTO);
        
        tipoPreparoDTO = new TipoPreparoDTO();
        tipoPreparoDTO.setDescricao("Assado");
        tipoPreparoNegocio.inserir(tipoPreparoDTO);
        
        tipoPreparoDTO = new TipoPreparoDTO();
        tipoPreparoDTO.setDescricao("Fritura Submersa em Óleo");
        tipoPreparoNegocio.inserir(tipoPreparoDTO);
    }
    
    private static void inserirGrupoAlimentar() throws PersistenciaException, NegocioException {
        GrupoAlimentarDAO grupoAlimentarDAO = new GrupoAlimentarDAO(FabricaEntityManager.getEntityManagerProducao());
        ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
        GrupoAlimentarNegocio grupoAlimentarNegocio = new GrupoAlimentarNegocio(grupoAlimentarDAO, produtoDAO);

        GrupoAlimentarDTO grupoDTO = new GrupoAlimentarDTO();
        grupoDTO.setNome("Carboidrato");
        grupoAlimentarNegocio.inserir(grupoDTO);

        grupoDTO = new GrupoAlimentarDTO();
        grupoDTO.setNome("Legumes");
        grupoAlimentarNegocio.inserir(grupoDTO);

        grupoDTO = new GrupoAlimentarDTO();
        grupoDTO.setNome("Proteinas");
        grupoAlimentarNegocio.inserir(grupoDTO);
        
        grupoDTO = new GrupoAlimentarDTO();
        grupoDTO.setNome("Verduras");
        grupoAlimentarNegocio.inserir(grupoDTO);
    }
    
    private static void inserirProduto() throws PersistenciaException, NegocioException  {
    	
        GrupoAlimentarDAO grupoAlimentarDAO = new GrupoAlimentarDAO(FabricaEntityManager.getEntityManagerProducao());
        ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
        GrupoAlimentarNegocio grupoAlimentarNegocio = new GrupoAlimentarNegocio(grupoAlimentarDAO, produtoDAO);
        ProdutoNegocio produtoNegocio = new ProdutoNegocio(produtoDAO);

        GrupoAlimentarDTO grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(3); // proteina

        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Alcatra bovina");
        produtoDTO.setEstoque(1000);
        produtoDTO.setEstoqueMinimo(100);
        produtoDTO.setCustoUnidade(2.0f);
        produtoDTO.setValorEnergetico(50);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Costela suina");
        produtoDTO.setEstoque(30);
        produtoDTO.setEstoqueMinimo(50);
        produtoDTO.setCustoUnidade(1.5f);
        produtoDTO.setValorEnergetico(60);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);

        grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(2); // Legumes

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Batata Inglesa");
        produtoDTO.setEstoque(2000);
        produtoDTO.setEstoqueMinimo(300);
        produtoDTO.setCustoUnidade(1.0f);
        produtoDTO.setValorEnergetico(80);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Batata Doce");
        produtoDTO.setEstoque(100);
        produtoDTO.setEstoqueMinimo(200);
        produtoDTO.setCustoUnidade(1.3f);
        produtoDTO.setValorEnergetico(70);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);


        grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(1); // Carboidrato

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Arroz Branco");
        produtoDTO.setEstoque(1000);
        produtoDTO.setEstoqueMinimo(500);
        produtoDTO.setCustoUnidade(1.7f);
        produtoDTO.setValorEnergetico(100);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);

        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Arroz Integral");
        produtoDTO.setEstoque(1000);
        produtoDTO.setEstoqueMinimo(500);
        produtoDTO.setCustoUnidade(1.9f);
        produtoDTO.setValorEnergetico(90);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);
        
        produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Fubá de Milho");
        produtoDTO.setEstoque(500);
        produtoDTO.setEstoqueMinimo(200);
        produtoDTO.setCustoUnidade(1.4f);
        produtoDTO.setValorEnergetico(75);
        produtoDTO.setGrupoAlimentar(grupoDTO);
        produtoNegocio.inserir(produtoDTO);

    }
    
    private static void inserirPreparoProduto() throws PersistenciaException, NegocioException {
        PreparoProdutoDAO preparoProdutoDAO = new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
        ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
        TipoPreparoDAO tipoPreparoDAO = new TipoPreparoDAO(FabricaEntityManager.getEntityManagerProducao());
        
        TipoPreparoNegocio tipoPreparoNegogio = new TipoPreparoNegocio(tipoPreparoDAO);
        PreparoProdutoNegocio preparoProdutoNegocio = new PreparoProdutoNegocio(preparoProdutoDAO);
        ProdutoNegocio produtoNegocio = new ProdutoNegocio(produtoDAO);

        ProdutoDTO produtoDTO = produtoNegocio.pesquisaParteNome("Arroz Branco").get(0);
        TipoPreparoDTO tipoPreparoDTO = tipoPreparoNegogio.pesquisaParteDescricao("Cozimento em agua").get(0);
        
        PreparoProdutoDTO preparoProdutoDTO = new PreparoProdutoDTO();
        preparoProdutoDTO.setNome("Arroz Cozido");
        preparoProdutoDTO.setProduto(produtoDTO);
        preparoProdutoDTO.setTipoPreparo(tipoPreparoDTO);
        preparoProdutoDTO.setTempoPreparo(25);
        preparoProdutoDTO.setValorPreparo(0.5f);

        preparoProdutoNegocio.inserir(preparoProdutoDTO);
        
        produtoDTO = produtoNegocio.pesquisaParteNome("Alcatra bovina").get(0);
        tipoPreparoDTO = tipoPreparoNegogio.pesquisaParteDescricao("Assado").get(0);
        
        preparoProdutoDTO = new PreparoProdutoDTO();
        preparoProdutoDTO.setNome("Alcatra Assada");
        preparoProdutoDTO.setProduto(produtoDTO);
        preparoProdutoDTO.setTipoPreparo(tipoPreparoDTO);
        preparoProdutoDTO.setTempoPreparo(10);
        preparoProdutoDTO.setValorPreparo(1.0f);

        preparoProdutoNegocio.inserir(preparoProdutoDTO);
    }
    
    private static void inserirPedido() throws PersistenciaException, NegocioException {
    	ClienteDAO clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
        PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
        PreparoProdutoDAO preparoProdutoDAO = new PreparoProdutoDAO(FabricaEntityManager.getEntityManagerProducao());

        PreparoProdutoNegocio preparoProdutoNegocio = new PreparoProdutoNegocio(preparoProdutoDAO);
        ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO, pedidoDAO);
        PedidoNegocio pedidoNegocio = new PedidoNegocio(pedidoDAO, clienteDAO);

        ClienteDTO clienteDTO = clienteNegocio.pesquisaParteNome("Cliente 01").get(0);

        PreparoProdutoDTO preparoProdutoDTO = preparoProdutoNegocio.pesquisaPorCodigo(1);

        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
        itemPedidoDTO.setPreparoProduto(preparoProdutoDTO);
        itemPedidoDTO.setQuantidadePorcao(50);
        List<ItemPedidoDTO> lista = new ArrayList<ItemPedidoDTO>();
        lista.add(itemPedidoDTO);

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setDataPedido(LocalDate.now());
        pedidoDTO.setHoraPedido(LocalTime.now());
        pedidoDTO.setCliente(clienteDTO);
        pedidoDTO.setEstado(EstadoPedidoDTO.REGISTRADO);
        pedidoDTO.setListaItens(lista);

        pedidoNegocio.inserir(pedidoDTO);
		
	}
}
