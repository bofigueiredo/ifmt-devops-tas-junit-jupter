package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Entregador_UnitarioTest {
	
	private Entregador entregador;
	
	@BeforeEach
	void beforeEach() {
		entregador = new Entregador();
		entregador.setNome("ENTREGADOR DE TESTE");
		entregador.setRG("123456789");
		entregador.setCPF("12345678909");
		entregador.setTelefone("65981112222");
	}
	
    @Test
    void testEntregador_Validar() {
    	assertEquals("", entregador.validar());
    }
    
    @Test
    void testEntregador_Validar_CpfInvalido() {
    	entregador.setCPF("123456");
    	assertEquals("CPF invalido", entregador.validar());
    }
    
    @Test
    void testEntregador_Validar_TelefoneInvalido() {
    	entregador.setTelefone(null);
    	assertEquals("Telefone invalido", entregador.validar());
    }
    
    @Test
    void testEntregador_Validar_NomeInvalido() {
    	entregador.setNome(null);
    	assertEquals("Nome invalido", entregador.validar());
    }
    
    @Test
    void testEntregador_Validar_RGInvalido() {
    	entregador.setRG(null);
    	assertEquals("RG invalido", entregador.validar());
    }
}
