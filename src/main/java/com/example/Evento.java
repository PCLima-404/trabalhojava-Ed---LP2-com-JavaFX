    package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Evento {
    private String id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Lista palestras;

    // Construtor principal para evento com período definido
    public Evento(String id, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.palestras = new Lista(50);
    }

    // Construtor para evento com apenas uma data (dataInicio = dataFim)
    public Evento(String id, String nome, String descricao, LocalDate data) {
        this(id, nome, descricao, data, data);
    }

    // Construtor que recebe datas no formato String (dd/MM/yyyy)
    public Evento(String id, String nome, String descricao, String dataInicioStr, String dataFimStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = LocalDate.parse(dataInicioStr, formatter);
        this.dataFim = LocalDate.parse(dataFimStr, formatter);
        this.palestras = new Lista(50);
    }

    // Método para adicionar palestra, evitando conflito de horário
    public boolean adicionarPalestra(Palestra palestra) {
        if (palestra == null) {
            return false;
        }
        if (verificarConflitoHorario(palestra)) {
            return false;
        }
        try {
            palestras.anexar(palestra);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Remove palestra pelo ID
    public boolean removerPalestra(String id) {
        for (int i = 0; i < palestras.getTamanho(); i++) {
            Palestra p = (Palestra) palestras.selecionar(i);
            if (p.getId().equals(id)) {
                palestras.apagar(i);
                return true;
            }
        }
        return false;
    }

    // Busca palestra pelo ID
    public Palestra buscarPalestra(String id) {
        for (int i = 0; i < palestras.getTamanho(); i++) {
            Palestra p = (Palestra) palestras.selecionar(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    

    // Verifica conflito de horário e local entre palestras
    public boolean verificarConflitoHorario(Palestra novaPalestra) {
        for (int i = 0; i < palestras.getTamanho(); i++) {
            Palestra p = (Palestra) palestras.selecionar(i);
            if (p.getLocal().equals(novaPalestra.getLocal()) && 
                p.verificarConflitoHorario(novaPalestra)) {
                return true;
            }
        }
        return false;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getDescricao() {
        return descricao;
    }
@Override
public String toString() {
    return "Evento: " + getNome() + "\n" +
           "ID: " + getId() + "\n" +
           "Descrição: " + getDescricao() + "\n" +
           "Data Início: " + getDataInicio() + "\n" +
           "Data Fim: " + getDataFim() + "\n" +
           "Número de Palestras: " + (listarPalestras() != null ? listarPalestras().length : 0);
}
public Palestra[] listarPalestras() {
    Object[] objetosPalestras = palestras.selecionarTodos();
    Palestra[] arrayPalestras = new Palestra[objetosPalestras.length];

    for (int i = 0; i < objetosPalestras.length; i++) {
        arrayPalestras[i] = (Palestra) objetosPalestras[i];
    }

    return arrayPalestras;
}
}
