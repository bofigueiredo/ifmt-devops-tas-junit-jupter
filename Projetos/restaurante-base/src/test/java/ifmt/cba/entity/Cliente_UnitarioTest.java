package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Cliente_UnitarioTest {
	
	private Cliente cliente;
	
	@BeforeEach
	void beforeEach() {
		cliente = new Cliente();
		cliente.setNome("CLIENTE DE TESTE");
		cliente.setRG("123456789");
		cliente.setCPF("12345678909");
		cliente.setTelefone("65981112222");
		cliente.setLogradouro("LOGRADOURO DO CLIENTE");
		cliente.setNumero("123");
		cliente.setBairro("CENTRO");
		cliente.setPontoReferencia("AO LADO DA FARMACIA");
	}
	
    @Test
    void testCliente_Validar() {
    	assertEquals("", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_LogradouroInvalido() {
    	cliente.setLogradouro(null);
    	assertEquals("Logradouro invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_RGInvalido() {
    	cliente.setRG(null);
    	assertEquals("RG invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_NomeInvalido() {
    	cliente.setNome("");
    	assertEquals("Nome invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_CpfInvalido() {
    	cliente.setCPF(null);
    	assertEquals("CPF invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_TelefoneInvalido() {
    	cliente.setTelefone(null);
    	assertEquals("Telefone invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_NumeroInvalido() {
    	cliente.setNumero(null);
    	assertEquals("Numero invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_BairroInvalido() {
    	cliente.setBairro(null);
    	assertEquals("Bairro invalido", cliente.validar());
    }
    
    @Test
    void testCliente_Validar_PontoReferenciaInvalido() {
    	cliente.setPontoReferencia(null);
    	assertEquals("Ponto de referencia invalido", cliente.validar());
    }
    
}
