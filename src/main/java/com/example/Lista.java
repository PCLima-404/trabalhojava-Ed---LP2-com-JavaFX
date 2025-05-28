/**
 * Classe que representa uma lista.
 * 
 * Essa implementação gerencia o tamanho dinamicamente até a capacidade inicial
 * definida.
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

import java.util.Arrays;

public class Lista {
    private Object[] elementos;
    private int tamanho;

    /**
     * Construtor.
     * 
     * @param capacidade capacidade máxima da lista.
     */
    public Lista(int capacidade) {
        this.elementos = new Object[capacidade];
        this.tamanho = 0;
    }

    /**
     * Insere um dado em uma posição específica da lista.
     * 
     * @param dado o objeto a ser inserido.
     * @param posicao a posição onde o objeto será inserido.
     * @throws IndexOutOfBoundsException se a posição for inválida.
     * @throws IllegalStateException se a lista estiver cheia.
     */
    public void inserir(Object dado, int posicao) {
        if (posicao < 0 || posicao > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        if (estaCheia()) {
            throw new IllegalStateException("Lista cheia");
        }

        for (int i = tamanho; i > posicao; i--) {
            elementos[i] = elementos[i - 1];
        }

        elementos[posicao] = dado;
        tamanho++;
    }

    /**
     * Adiciona um elemento ao final da lista.
     * 
     * @param dado o objeto a ser adicionado.
     * @throws IllegalStateException se a lista estiver cheia.
     */
    public void anexar(Object dado) {
        if (estaCheia()) {
            throw new IllegalStateException("Lista cheia");
        }
        elementos[tamanho++] = dado;
    }

    /**
     * Retorna o elemento em uma determinada posição da lista.
     * 
     * @param posicao a posição desejada.
     * @return o objeto na posição especificada.
     * @throws IndexOutOfBoundsException se a posição for inválida.
     */
    public Object selecionar(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        return elementos[posicao];
    }

    /**
     * Retorna uma cópia de todos os elementos da lista até o tamanho atual.
     * 
     * @return um array contendo todos os elementos.
     */
    public Object[] selecionarTodos() {
        return Arrays.copyOf(elementos, tamanho);
    }

    /**
     * Atualiza o dado em uma posição da lista.
     * 
     * @param dado o novo valor a ser inserido.
     * @param posicao a posição a ser atualizada.
     * @throws IndexOutOfBoundsException se a posição for inválida.
     */
    public void atualizar(Object dado, int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        elementos[posicao] = dado;
    }

    /**
     * Remove o elemento de uma posição específica da lista.
     * 
     * @param posicao a posição do elemento a ser removido.
     * @return o objeto removido.
     * @throws IndexOutOfBoundsException se a posição for inválida.
     */
    public Object apagar(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        Object removido = elementos[posicao];

        for (int i = posicao; i < tamanho - 1; i++) {
            elementos[i] = elementos[i + 1];
        }

        elementos[--tamanho] = null;
        return removido;
    }

    /**
     * Verifica se a lista contém dado especifico.
     * 
     * @param dado o objeto a ser verificado.
     * @return true se o objeto estiver na lista, false caso contrário.
     */
    public boolean contem(Object dado) {
        for (int i = 0; i < tamanho; i++) {
            if (elementos[i] != null && elementos[i].equals(dado)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Limpa todos os elementos da lista
     */
    public void limpar() {
        for (int i = 0; i < tamanho; i++) {
            elementos[i] = null;
        }
        tamanho = 0;
    }

    /**
     * Verifica se a lista está cheia.
     * 
     * @return true se a lista estiver cheia, false caso contrário.
     */
    public boolean estaCheia() {
        return tamanho == elementos.length;
    }

    /**
     * Verifica se a lista está vazia.
     * 
     * @return true se a lista estiver vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return tamanho == 0;
    }

    /**
     * Retorna uma representação textual da lista.
     * 
     * @return uma string contendo os elementos da lista.
     */
    public String imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tamanho; i++) {
            sb.append(elementos[i]);
            if (i < tamanho - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Retorna o número atual de elementos na lista.
     * 
     * @return o tamanho da lista.
     */
    public int getTamanho() {
        return tamanho;
    }

    /**
     * Retorna o elemento na posição especificada.
     * 
     * @param indice a posição a ser acessada.
     * @return o objeto naquela posição.
     * @throws IndexOutOfBoundsException se a posição for inválida.
     */
    public Object buscar(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora do limite da lista.");
        }
        return elementos[indice];
    }

}
