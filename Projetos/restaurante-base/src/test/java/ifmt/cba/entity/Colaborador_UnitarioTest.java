package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Colaborador_UnitarioTest {
	
	private Colaborador colaborador;
	
	@BeforeEach
	void beforeEach() {
		colaborador = new Colaborador();
    	colaborador.setNome("COLABORADOR ENTREGADOR");
    	colaborador.setRG("8484181");
    	colaborador.setCPF("12345678909");
    	colaborador.setTelefone("65981111111");
    }

    @Test
    void testColaborador_Validar() {
    	assertEquals("", colaborador.validar());
    }
    
    @Test
    void testColaborador_Validar_CpfInvalido() {
    	colaborador.setCPF("123456789");
    	assertEquals("CPF invalido", colaborador.validar());
    }
    
    @Test
    void testColaborador_Validar_TelefoneInvalido() {
    	colaborador.setTelefone(null);
    	assertEquals("Telefone invalido", colaborador.validar());
    }
    
    @Test
    void testColaborador_Validar_NomeInvalido() {
    	colaborador.setNome(null);
    	assertEquals("Nome invalido", colaborador.validar());
    }
    
    @Test
    void testColaborador_Validar_RGInvalido() {
    	colaborador.setRG(null);
    	assertEquals("RG invalido", colaborador.validar());
    }
}
