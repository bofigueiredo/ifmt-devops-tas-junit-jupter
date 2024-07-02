package ifmt.cba.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GrupoAlimentar_UnitarioTest {
	
	private GrupoAlimentar grupoAlimentar;
	
	@BeforeEach
	void beforeEach() {
		grupoAlimentar = new GrupoAlimentar();
		grupoAlimentar.setNome("NOME GRUPO ALIMENTAR");
	}
	
    @Test
    void testGrupoAlimentar_Validar() {
    	assertEquals("", grupoAlimentar.validar());
    }
    
    @Test
    void testGrupoAlimentar_Validar_NomeInvalido() {
    	grupoAlimentar.setNome("NO");
    	assertEquals("Nome nao valido", grupoAlimentar.validar());
    }
    
}
