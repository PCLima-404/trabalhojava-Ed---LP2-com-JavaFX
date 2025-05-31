package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa um evento que pode conter várias palestras.
 * Cada evento possui um identificador, nome, descrição,
 * data de início, data de fim.
 * Permite adicionar, remover e buscar palestras, além de verificar conflitos de horário.
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
 * 
 * @version 1.1
 * @since 2025-05-25
 */
public class Evento {
    private static int contadorIds = 0;
    private String id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Lista palestras;

    /**
     * Construtor principal do evento com período definido.
     *
     * @param id         Identificador único do evento.
     * @param nome       Nome do evento.
     * @param descricao  Descrição do evento.
     * @param dataInicio Data de início do evento.
     * @param dataFim    Data de término do evento.
     */
    public Evento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim) {
        id = "EV" + contadorIds++;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.palestras = new Lista(50);
    }

    /**
     * Construtor para eventos com apenas uma data (início e fim iguais).
     *
     * @param id        Identificador do evento.
     * @param nome      Nome do evento.
     * @param descricao Descrição do evento.
     * @param data      Data única do evento.
     */
    public Evento(String nome, String descricao, LocalDate data) {
        this(nome, descricao, data, data);
        id = "EV" + contadorIds++;
        this.palestras = new Lista(50);
    }

    /**
     * Construtor que aceita datas no formato texto "dd/MM/yyyy".
     *
     * @param id            Identificador do evento.
     * @param nome          Nome do evento.
     * @param descricao     Descrição do evento.
     * @param dataInicioStr Data de início (formato dd/MM/yyyy).
     * @param dataFimStr    Data de fim (formato dd/MM/yyyy).
     */
    public Evento(String nome, String descricao, String dataInicioStr, String dataFimStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        id = "EV" + contadorIds++;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = LocalDate.parse(dataInicioStr, formatter);
        this.dataFim = LocalDate.parse(dataFimStr, formatter);
        this.palestras = new Lista(50);
    }

    /**
     * Adiciona uma palestra ao evento, se não houver conflito de horário.
     *
     * @param palestra A palestra a ser adicionada.
     * @return true se a palestra for adicionada com sucesso, false caso contrário.
     */
    public boolean adicionarPalestra(Palestra palestra) {
        if (palestra == null || verificarConflitoHorario(palestra)) {
            return false;
        }
        try {
            palestras.anexar(palestra);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Remove uma palestra do evento com base no ID informado.
     *
     * @param id ID da palestra a ser removida.
     * @return true se a palestra for removida com sucesso, false caso não encontrada.
     */
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

    /**
     * Busca uma palestra específica pelo seu ID.
     *
     * @param id ID da palestra.
     * @return A palestra correspondente, ou null se não encontrada.
     */
    public Palestra buscarPalestra(String id) {
        for (int i = 0; i < palestras.getTamanho(); i++) {
            Palestra p = (Palestra) palestras.selecionar(i);
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * verifica se tem conflito entre palestras
     *
     * @param novaPalestra Nova palestra a ser verificada.
     * @return true se houver conflito de horário e local, false caso contrário.
     */
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

    /**
     * Lista todas as palestras associadas ao evento.
     *
     * @return Um array com todas as palestras.
     */
    public Palestra[] listarPalestras() {
        Object[] objetosPalestras = palestras.selecionarTodos();
        Palestra[] arrayPalestras = new Palestra[objetosPalestras.length];

        for (int i = 0; i < objetosPalestras.length; i++) {
            arrayPalestras[i] = (Palestra) objetosPalestras[i];
        }

        return arrayPalestras;
    }

    // Getters

    /**
     * @return o nome do evento.
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return o ID do evento.
     */
    public String getId() {
        return id;
    }

    /**
     *  @return a data de início do evento.
     */
    public LocalDate getDataInicio() {
        return dataInicio;
    }

    /**
     * @return a data de fim do evento.
     */
    public LocalDate getDataFim() {
        return dataFim;
    }

    /**
     * @return a descrição do evento.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * @return uma string representando os detalhes do evento.
     */
    @Override
    public String toString() {
        return "Evento: " + getNome() + "\n" +
               "ID: " + getId() + "\n" +
               "Descrição: " + getDescricao() + "\n" +
               "Data Início: " + getDataInicio() + "\n" +
               "Data Fim: " + getDataFim() + "\n" +
               "Número de Palestras: " + (listarPalestras() != null ? listarPalestras().length : 0);
    }
}
