import com.example.Palestra;
import com.example.Participante;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

/**
 * Classe que contem testes unitarios para monitoramento das funções da classe
 * Participante
 *
 * @author Grupo 1:
 * Ana Gomes Souza,
 * Arthur Sousa Costa,
 * Eduardo Miranda Berlink Santos,
 * Henrique Rezende Bandeira Chiachio,
 * João Lucas Fonseca Chagas,
 * Marco Antonio Barbosa Pereira,
 * Mary Nicole de Sousa Mendes,
 * Pedro César Padre Lima
 * @since 08-05-2025
 * @version 1.0
 */

public class ParticipanteTest {

    private Participante participante1, participante2, participante3;
    private Palestra palestra1, palestra2, palestra3, palestraNula;
    LocalDate inicio;

    /**
     * Execução antes de cada teste
     */
    @Before
    public void setUp() {
        inicio = LocalDate.of(2025, 1, 1);
        participante1 = new Participante("J", "J@");
        participante2 = new Participante("M", "M@");
        participante3 = new Participante("P", "P@");

        palestra1 = new Palestra("PALESTRA 1", "PALESTRA TESTE 1", "07:00", "08:00", inicio,
                "AUDITORIO", "H", 2);

        palestra2 = new Palestra("PALESTRA 2", "PALESTRA TESTE 2", "09:00", "10:00", inicio,
                "AUDITORIO", "Q", 2);

        palestraNula = null;
    }

    /**
     * Testa o metodo de inscrever o participante em uma palestra
     * Assegura que não possa inscrever em palestra nula, nem reinscrição
     * Assegura o limite de participantes, o posicionando na fila de espera
     */
    @Test
    public void testInscreverPalestra() {
        assertTrue(participante1.inscreverEmPalestra(palestra1));
        assertFalse(participante1.inscreverEmPalestra(palestra1));
        assertFalse(participante1.inscreverEmPalestra(palestraNula));

        participante2.inscreverEmPalestra(palestra1);

        assertTrue(participante3.inscreverEmPalestra(palestra1));

    }

    /**
     * Testa o metodo de cancelar a inscrição do participante em uma palestra,
     * removendo a da lista
     * Assegura que não possa cancelar inscrição de palestra nula
     * Assegura que não possa cancelar inscrição de palestra não inscrita
     */
    @Test
    public void testCancelarInscricao(){
        assertFalse(participante1.cancelarInscricao(palestra1));
        assertFalse(participante1.cancelarInscricao(palestraNula));

        participante1.inscreverEmPalestra(palestra1);

        assertTrue(participante1.cancelarInscricao(palestra1));

    }

    /**
     * Testa o metodo de remover palestra da lista de um participante
     * Assegura que não possa remover palestra nulas
     * Assegura que não possa remover palestra não listadas
     */
    @Test
    public void testRemoverPalestra() {
        assertFalse(participante1.removerPalestra(palestra1));

        participante1.inscreverEmPalestra(palestra1);

        assertTrue(participante1.removerPalestra(palestra1));
        assertFalse(participante1.removerPalestra(palestraNula));
        assertFalse(participante1.removerPalestra(palestra2));

   }

    /**
     * Testa o metodo de listar todas a palestras inscritas do participante
     */
   @Test
    public void testListarPalestrasInscritas() {
        Palestra[] criterio = new Palestra[2];
        criterio[0] = palestra1;
        criterio[1] = palestra2;

        participante1.inscreverEmPalestra(palestra1);
        participante1.inscreverEmPalestra(palestra2);
        assertEquals(criterio, participante1.listarPalestrasInscritas());
   }




}
