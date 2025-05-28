/**
 * Classe que representa uma fila circular de objetos.
 * 
 * Essa implementação utiliza um vetor com índice circular para enfileirar e desenfileirar
 * elementos, respeitando a ordem FIFO (First In, First Out).
 * 
 * @author Grupo 5:
 * Ana Gomes Souza,
 * Arthur Sousa Costa,
 * Eduardo Miranda Berlink Santos,
 * Henrique Rezende Bandeira Chiachio,
 * João Lucas Fonseca Chagas,
 * Marco Antonio Barbosa Pereira,
 * Mary Nicole de Sousa Mendes,
 * Pedro César Padre Lima
 * 
 * @version 1.1
 * @since 2025-05-21
 */
package com.example;

public class Fila {
    private Object[] elementos;
    private int inicio;
    private int fim;
    private int tamanho;

    /**
     * Construtor.
     * 
     * @param capacidade Capacidade máxima da fila.
     */
    public Fila(int capacidade) {
        this.elementos = new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;
        this.tamanho = 0;
    }

    /**
     * Adiciona um elemento ao final da fila.
     * 
     * @param dado Elemento a ser enfileirado.
     * @throws IllegalStateException se a fila estiver cheia.
     */
    public void enfileirar(Object dado) {
        if (estaCheia()) {
            throw new IllegalStateException("Fila cheia");
        }
        fim = (fim + 1) % elementos.length;
        elementos[fim] = dado;
        tamanho++;
    }

    /**
     * Retorna o elemento no início da fila sem removê-lo.
     * 
     * @return Elemento no início da fila.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    public Object frente() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        return elementos[inicio];
    }

    /**
     * Atualiza o elemento que está no início da fila.
     * 
     * @param dado Novo dado a ser inserido na posição inicial.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    public void atualizarlnicio(Object dado) {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        elementos[inicio] = dado;
    }

    /**
     * Atualiza o elemento que está no final da fila.
     * 
     * @param dado Novo dado a ser inserido na posição final.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    public void atualizarFim(Object dado) {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        elementos[fim] = dado;
    }

    /**
     * Remove e retorna o elemento no início da fila.
     * 
     * @return Elemento removido.
     * @throws IllegalStateException se a fila estiver vazia.
     */
    public Object desenfileirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        Object removido = elementos[inicio];
        elementos[inicio] = null;
        inicio = (inicio + 1) % elementos.length;
        tamanho--;
        return removido;
    }

    /**
     * Verifica se a fila está cheia.
     * 
     * @return true se a fila estiver cheia, false caso contrário.
     */
    public boolean estaCheia() {
        return tamanho == elementos.length;
    }

    /**
     * Verifica se a fila está vazia.
     * 
     * @return true se a fila estiver vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Retorna uma representação em string dos elementos da fila.
     * 
     * @return String com os elementos da fila no formato "[elem1, elem2, ...]".
     */
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int count = 0;
        int i = inicio;
        
        while (count < tamanho) {
            sb.append(elementos[i]);
            if (count < tamanho - 1) {
                sb.append(", ");
            }
            i = (i + 1) % elementos.length;
            count++;
        }
        
        sb.append("]");
        return sb.toString();
    }

    /**
     * Retorna o número de elementos presentes na fila.
     * 
     * @return Quantidade de elementos na fila.
     */
    public int getTamanho() {
        return tamanho;
    }
}
