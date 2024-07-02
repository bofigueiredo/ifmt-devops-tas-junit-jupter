package ifmt.cba.execucao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.dto.PreparoProdutoDTO;
import ifmt.cba.negocio.ClienteNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.negocio.PreparoProdutoNegocio;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.persistencia.PreparoProdutoDAO;

public class AppInserirCargaDeEstresse {
    public static void main(String[] args) {
    	for (int i = 0; i < 1; i++) {
    		inserirPedido();
    	}
    }

	private static void inserirPedido() {
		try {
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
            itemPedidoDTO.setQuantidadePorcao(1);
            List<ItemPedidoDTO> lista = new ArrayList<ItemPedidoDTO>();
            lista.add(itemPedidoDTO);

            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setDataPedido(LocalDate.now());
            pedidoDTO.setHoraPedido(LocalTime.now());
            pedidoDTO.setCliente(clienteDTO);
            pedidoDTO.setEstado(EstadoPedidoDTO.REGISTRADO);
            pedidoDTO.setListaItens(lista);
            
            pedidoNegocio.inserir(pedidoDTO);

        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
	}
}
