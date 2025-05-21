package com.example;

public class Fila {
    private Object[] elementos;
    private int inicio;
    private int fim;
    private int tamanho;

    public Fila(int capacidade) {
        this.elementos = new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;
        this.tamanho = 0;
    }

    public void enfileirar(Object dado) {
        if (estaCheia()) {
            throw new IllegalStateException("Fila cheia");
        }
        fim = (fim + 1) % elementos.length;
        elementos[fim] = dado;
        tamanho++;
    }

    public Object frente() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        return elementos[inicio];
    }

    public void atualizarlnicio(Object dado) {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        elementos[inicio] = dado;
    }

    public void atualizarFim(Object dado) {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia");
        }
        elementos[fim] = dado;
    }

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

    public boolean estaCheia() {
        return tamanho == elementos.length;
    }

    public boolean estaVazia() {
        return tamanho == 0;
    }

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

    public int getTamanho() {
        return tamanho;
    }
}