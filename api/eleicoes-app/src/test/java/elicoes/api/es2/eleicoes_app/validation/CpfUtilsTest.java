package elicoes.api.es2.eleicoes_app.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CpfUtilsTest {

    @Test
    void aceitaCpfComDigitosVerificadoresCorretos() {
        assertTrue(CpfUtils.valido("529.982.247-25"));
        assertEquals("52998224725", CpfUtils.somenteDigitos("529.982.247-25"));
    }

    @Test
    void rejeitaCpfInvalido() {
        assertFalse(CpfUtils.valido("52998224726"));
        assertFalse(CpfUtils.valido("00000000000"));
        assertFalse(CpfUtils.valido(null));
        assertFalse(CpfUtils.valido("123"));
    }

}
