package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TipoPreparo_UnitarioTest {
	
	private TipoPreparo tipoPreparo;
	
	@BeforeEach
	void beforeEach() {
		tipoPreparo = new TipoPreparo();
    	tipoPreparo.setDescricao("DESCRICAO");
	}
	
    @Test
    void testTipoPreparo_Validar() {
    	assertEquals("", tipoPreparo.validar());
    }
    
    @Test
    void testTipoPreparo_Validar_DescricaoInvalida() {
    	tipoPreparo.setDescricao("DE");
    	assertEquals("Descricao invalida", tipoPreparo.validar());
    }
    
}
