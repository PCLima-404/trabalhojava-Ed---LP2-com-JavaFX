import com.example.Evento;
import com.example.Palestra;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Classe que contem testes unitarios para monitoramento das funções da classe
 * Evento
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

public class EventoTest {

    private Evento eventoTeste;
    private Palestra palestra1, palestra2, palestra3;

    /**
     * Execução antes de cada teste
     */
    @Before
    public void setUp(){
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fim = inicio.plusDays(1);
        eventoTeste = new Evento("T1", "EVENTO TESTE", inicio, fim);
        palestra1 = new Palestra("P1", "PALESTRA TESTE 1", "07:00", "08:00", inicio,
                "AUDITORIO", "HENRIQUE", 5);
        palestra2 = new Palestra("P2", "PALESTRA TESTE 2", "09:00", "10:00", inicio,
                "AUDITORIO", "HENRIQUE", 5);
        palestra3 = new Palestra("P3", "PALESTRA TESTE 3", "10:00", "11:00", inicio,
                "AUDITORIO", "HENRIQUE", 5);

    }

    /**
     * Testa o metodo que busca uma palestra no evento
     */
    @Test
    public void testBuscarPalestra() {
        eventoTeste.adicionarPalestra(palestra1);
        eventoTeste.adicionarPalestra(palestra2);

        assertEquals(palestra1, eventoTeste.buscarPalestra(palestra1.getId()));
        assertEquals(palestra2, eventoTeste.buscarPalestra(palestra2.getId()));
    }

    /**
     * Testa o metodo que remove uma palestra do evento
     * Assegura que não remova palestra de um evento vazio
     */
    @Test
    public void testRemoverPalestra() {
        assertFalse(eventoTeste.removerPalestra(palestra1.getId()));

        eventoTeste.adicionarPalestra(palestra1);
        eventoTeste.adicionarPalestra(palestra2);

        assertTrue(eventoTeste.removerPalestra(palestra2.getId()));
        assertNull(eventoTeste.buscarPalestra(palestra2.getId()));

    }

    /**
     * Testa metodo que verifica conflito de horario de uma nova
     * palestra, com as palestras já cadastradas no evento
     */
    @Test
    public void testVerificarConflitoHorario() {
        eventoTeste.adicionarPalestra(palestra1);
        eventoTeste.adicionarPalestra(palestra2);

        assertTrue(eventoTeste.verificarConflitoHorario(palestra3));
    }

    /**
     * Testa o metodo que monta um array com todas as palestras
     * do evento
     */
    @Test
    public void testListarPalestras() {
        eventoTeste.adicionarPalestra(palestra1);
        eventoTeste.adicionarPalestra(palestra2);

        Palestra[] criterio = new Palestra[2];
        criterio[0] = palestra1;
        criterio[1] = palestra2;

        assertEquals(criterio, eventoTeste.listarPalestras());
    }
}
