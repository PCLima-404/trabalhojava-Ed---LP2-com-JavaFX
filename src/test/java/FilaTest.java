import com.example.Fila;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe que contem testes unitarios para monitoramento das funções da estrutura de dados
 * Fila
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

public class FilaTest {
private Fila filaTeste;
private String elemento0, elemento1, elemento2;

     /**
     * Execução antes de cada teste
     */
    @Before
    public void setUp() {
        filaTeste = new Fila(3);
        elemento0 = "0";
        elemento1 = "1";
        elemento2 = "2";

    }

    /**
     * Testa o metodo que retira o proximo da fila
     * Assegura que não retire de uma fila vazia
     */

    @Test
    public void testeDesenfileirar() {
        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        assertEquals(elemento0, filaTeste.desenfileirar());
        assertEquals(elemento1, filaTeste.desenfileirar());
        assertEquals(elemento2, filaTeste.desenfileirar());

        try {
            filaTeste.desenfileirar();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Fila vazia", e.getMessage());
        }

    }

    /**
     * Testa o metodo que mostra o proximo da fila
     * Assegura que não mostre nada de uma fila vazia
     */
    @Test
    public void testeFrente(){
        try {
            filaTeste.frente();
            fail();
        } catch (IllegalStateException e) {
            assertEquals("Fila vazia", e.getMessage());
        }

        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        assertEquals(elemento0, filaTeste.frente());
        filaTeste.desenfileirar();
        assertEquals(elemento1, filaTeste.frente());
        filaTeste.desenfileirar();
        assertEquals(elemento2, filaTeste.frente());
        filaTeste.desenfileirar();

    }

    /**
     * Testa o metodo que atualiza o proximo da fila (Inicio)
     * Assegura que não atualize fila vazia
     */
    @Test
    public void testeAtualizarInicio() {

        try {
            filaTeste.atualizarInicio("ATT");
            fail();
        } catch (IllegalStateException e){
            assertEquals("Fila vazia", e.getMessage());
        }

        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        filaTeste.atualizarInicio("ATT");
        assertEquals("ATT", filaTeste.desenfileirar());

        filaTeste.atualizarInicio("ATT");
        assertEquals("ATT", filaTeste.desenfileirar());

        filaTeste.atualizarInicio("ATT");
        assertEquals("ATT", filaTeste.desenfileirar());
    }

    /**
     * Testa o metodo que atualiza o ultimo da fila (Fim)
     * Assegura que não atualize fila vazia
     */

    @Test
    public void testAtualizarIFim() {
        try {
            filaTeste.atualizarFim("ATT");
            fail();
        } catch (IllegalStateException e){
            assertEquals("Fila vazia", e.getMessage());
        }

        filaTeste.enfileirar(elemento0);
        filaTeste.atualizarFim("ATT");
        assertEquals("ATT", filaTeste.desenfileirar());

        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        filaTeste.atualizarFim("ATT");

        assertEquals(elemento0, filaTeste.desenfileirar());
        assertEquals(elemento1, filaTeste.desenfileirar());
        assertEquals("ATT", filaTeste.desenfileirar());

    }

    /**
     * Testa metodo que verifica se a fila esta cheia
     */
    @Test
    public void testeEstaCheia() {
        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        assertTrue(filaTeste.estaCheia());
    }
    /**
     * Testa metodo que verifica se a fila esta vazia
     */
    @Test
    public void testeEstaVazia() {
        assertTrue(filaTeste.estaVazia());
    }

    /**
     * Testa metodo que concatena todos os elementos da fila em uma string
     */
    @Test
    public void testeImprimir() {

        String criterio = "[0, 1, 2]";
        filaTeste.enfileirar(elemento0);
        filaTeste.enfileirar(elemento1);
        filaTeste.enfileirar(elemento2);

        assertEquals(criterio, filaTeste.imprimir());
    }
}
