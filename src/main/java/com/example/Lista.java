package com.example;

import java.util.Arrays;

public class Lista {
    private Object[] elementos;
    private int tamanho;

    public Lista(int capacidade) {
        this.elementos = new Object[capacidade];
        this.tamanho = 0;
    }

    public void inserir(Object dado, int posicao) {
        if (posicao < 0 || posicao > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        if (estaCheia()) {
            throw new IllegalStateException("Lista cheia");
        }
        
        for (int i = tamanho; i > posicao; i--) {
            elementos[i] = elementos[i-1];
        }
        
        elementos[posicao] = dado;
        tamanho++;
    }

    public void anexar(Object dado) {
        if (estaCheia()) {
            throw new IllegalStateException("Lista cheia");
        }
        elementos[tamanho++] = dado;
    }

    public Object selecionar(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        return elementos[posicao];
    }

    public Object[] selecionarTodos() {
        return Arrays.copyOf(elementos, tamanho);
    }

    public void atualizar(Object dado, int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        elementos[posicao] = dado;
    }

    public Object apagar(int posicao) {
        if (posicao < 0 || posicao >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }
        
        Object removido = elementos[posicao];
        
        for (int i = posicao; i < tamanho-1; i++) {
            elementos[i] = elementos[i+1];
        }
        
        elementos[--tamanho] = null;
        return removido;
    }

    public boolean contem(Object dado) {
        for (int i = 0; i < tamanho; i++) {
            if (elementos[i] != null && elementos[i].equals(dado)) {
                return true;
            }
        }
        return false;
    }

    public void limpar() {
        for (int i = 0; i < tamanho; i++) {
            elementos[i] = null;
        }
        tamanho = 0;
    }

    public boolean estaCheia() {
        return tamanho == elementos.length;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

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

    public int getTamanho() {
        return tamanho;
    }
    public Object buscar(int indice) {
        if (indice < 0 || indice >= tamanho) {
            throw new IndexOutOfBoundsException("Índice fora do limite da lista.");
        }
        return elementos[indice];
    }

}