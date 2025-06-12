import com.example.Palestra;
import com.example.Participante;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;


/**
 * Classe que contem testes unitarios para monitoramento das funções da classe
 * Palestra
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
public class PalestraTest {

    private Palestra palestraTeste1, palestraConflito, palestraTeste2;
    private Participante participante1, participante2, participante3, participanteNulo;
    LocalDate inicio;

    @Before
    public void setUp() {
        participante1 = new Participante("J", "J@");
        participante2 = new Participante("M", "M@");
        participante3 = new Participante("P", "P@");
        participanteNulo = null;

        inicio = LocalDate.of(2025, 1, 1);

        palestraTeste1 = new Palestra("PALESTRA 1", "PALESTRA TESTE 1", "07:00", "08:00", inicio,
                "AUDITORIO", "H", 2);

        palestraTeste2 = new Palestra("PALESTRA 2", "PALESTRA TESTE 2", "09:00", "10:00", inicio,
                "AUDITORIO", "Q", 2);
    }

    /**
     * Testa o metodo que verifica a disponibilidade de vaga de uma palestra
     *
     */
    @Test
    public void testVerificarDisponibildade() {

        assertTrue(palestraTeste1.verificarDisponibilidade());

        palestraTeste1.inscreverParticipante(participante1);
        palestraTeste1.inscreverParticipante(participante2);

        assertFalse(palestraTeste1.verificarDisponibilidade());
    }

    /**
     * Testa o metodo que adiciona participante a fila de espera e o notifica
     */
    @Test
    public void testAdicionarFilaDeEspera() {
        assertTrue(palestraTeste1.adicionarFilaEspera(participante1));
    }

    /**
     * Testa metodo que altera o horario de uma palestra
     * Assegura que não aceite horario nulo
     * Assegura que não aceite o horario atual
     */
    @Test
    public void testAlterarHorario() {
        LocalTime novoHorarioinicio, novoHorarioFim;

        assertFalse(palestraTeste1.alterarHorario(null, null));
        novoHorarioinicio = LocalTime.now();
        novoHorarioFim = LocalTime.now();
        assertFalse(palestraTeste1.alterarHorario(novoHorarioinicio, novoHorarioFim));

        novoHorarioinicio = LocalTime.now().plusHours(2);
        novoHorarioFim = LocalTime.now().plusHours(3);

        assertTrue(palestraTeste1.alterarHorario(novoHorarioinicio, novoHorarioFim));
    }



}
