import com.example.Lista;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Classe que contem testes unitarios para monitoramento das funções da estrutura de dados
 * Lista
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

public class ListaTest {
    private Lista listaTeste;
    private String elemento0, elemento1, elemento2;

    /**
     * Execução antes de cada teste
     */
    @Before
    public void setUp() {
        listaTeste = new Lista(3);
        elemento0 = "0";
        elemento1 = "1";
        elemento2 = "2";
    }

    /**
     * Teste o metodo de apagar um elemento de um indice da lista
     * Assegura que não apague em uma lista vazia
     * Assegura que não apague em um indice invalido
     */
    @Test
    public void testeApagar() {
        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        assertEquals(elemento0, listaTeste.apagar(0));
        assertEquals(elemento2, listaTeste.apagar(1));
        assertEquals(elemento1, listaTeste.apagar(0));

        try {
            listaTeste.apagar(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(listaTeste.estaVazia());
        }
    }

    /**
     * Testa o metodo que seleciona um elemento de um indice na lista
     * Assegura que não selecione um elemento não listado
     */
    @Test
    public void testSelecionar() {
        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        assertEquals(elemento0, listaTeste.selecionar(0));
        assertEquals(elemento1, listaTeste.selecionar(1));
        assertEquals(elemento2, listaTeste.selecionar(2));

        listaTeste.limpar();

        try {
          listaTeste.selecionar(0);
            fail();
        } catch (IndexOutOfBoundsException e) {
            assertEquals("Posição inválida", e.getMessage());
        }
    }

    /**
     * Testa o metodo que seleciona todo os itens listado
     */
    @Test
    public void testSelecionarTodos() {
        String[] criterio = new String[3];

        criterio[0] = elemento0;
        criterio[1] = elemento1;
        criterio[2] = elemento2;

        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        assertEquals(criterio, listaTeste.selecionarTodos());

    }

    /**
     * Testa o metodo que limpa toda a lista de elementos
     */
    @Test
    public void testLimpar() {
        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        listaTeste.limpar();

        assertTrue(listaTeste.estaVazia());

    }

    /**
     * Testa o metodo que atualiza um elemento em um indice da lista
     * Assegura
     */
    @Test
    public void testAtualizar() {
        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        listaTeste.atualizar("ATT", 2);
        listaTeste.atualizar("ATT", 0);
        listaTeste.atualizar("ATT", 1);

        assertEquals("ATT", listaTeste.selecionar(2));
        assertEquals("ATT", listaTeste.selecionar(0));
        assertEquals("ATT", listaTeste.selecionar(1));

        listaTeste.limpar();

        try {
            listaTeste.atualizar("ATT", 0);
            fail();
        }catch (IndexOutOfBoundsException e){
            assertEquals("Posição inválida", e.getMessage());
        }
    }

    /**
     * Testa o metodo que verifica se um elemento esta contido na lista
     */
    @Test
    public void testeContem() {

        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        assertTrue(listaTeste.contem(elemento0));
        assertTrue(listaTeste.contem(elemento1));
        assertTrue(listaTeste.contem(elemento2));

    }

    /**
     * Testa metodo que concatena todos os elementos da lista em uma string
     */
    @Test
    public void testeImprimir() {
        String criterio = "[0, 1, 2]";

        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);

        assertEquals(criterio, listaTeste.imprimir());
    }

    /**
     * Testa o metodo que anexa um elemento no final da lista
     * Assegura que não permita inserir um elemento na lista cheia
     */
    @Test
    public void testeAnexar(){
        listaTeste.anexar(elemento0);
        listaTeste.anexar(elemento1);
        listaTeste.anexar(elemento2);

        try {
            listaTeste.anexar(elemento0);
            fail();
        } catch (IllegalStateException e){
            assertEquals("Lista cheia", e.getMessage());
        }

        assertEquals(elemento0, listaTeste.apagar(0));
        assertEquals(elemento2, listaTeste.apagar(1));
        assertEquals(elemento1, listaTeste.apagar(0));



    }

    /**
     * Testa o metodo que verifica se a lista está cheia
     */
    @Test
    public void testEstaCheia() {
        listaTeste.inserir(elemento0, 0);
        listaTeste.inserir(elemento1, 1);
        listaTeste.inserir(elemento2, 2);
assertTrue(listaTeste.estaCheia());
    }

    /**
     * Testa o metodo que verifica se a lista está vazia
     */
    @Test
    public void testEstaVazia() {
        assertTrue(listaTeste.estaVazia());
    }


}
