package com.example;

import java.time.LocalDateTime;

/**
 * Representa uma palestra em um evento. Contém informações como título,
 * horário,
 * local, palestrante e controle de participantes e fila de espera.
 * 
 * @author Grupo 1: Eduardo Berlink, Mary Nicole, João Lucas, Marco Antonio,
 *         Arthur Sousa,
 *         Henrique Rezende, Ana Gomes Souza e Pedro Cezar.
 * 
 * @since 28-05-2025
 * @version 1.0
 */
public class Palestra {
    private String id;
    private String titulo;
    private String descricao;
    private LocalDateTime horario;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFinal;
    private int duracao;
    private String local;
    private String palestrante;
    private int limiteParticipantes;

    private Lista participantes;
    private Fila filaEspera;

    // Getters de horário de início e fim
    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }

    /**
     * Construtor da palestra.
     *
     * @param id                  Identificador da palestra.
     * @param titulo              Título da palestra.
     * @param descricao           Descrição da palestra.
     * @param horarioInicio       Início da palestra.
     * @param horarioFinal        Fim da palestra.
     * @param duracao             Duração em minutos.
     * @param local               Local onde será realizada.
     * @param palestrante         Nome do palestrante.
     * @param limiteParticipantes Número máximo de participantes.
     */
    public Palestra(String id, String titulo, String descricao, LocalDateTime horarioInicio, LocalDateTime horarioFinal,
            int duracao, String local, String palestrante, int limiteParticipantes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.duracao = duracao;
        this.local = local;
        this.palestrante = palestrante;
        this.limiteParticipantes = limiteParticipantes;
        this.participantes = new Lista(limiteParticipantes);
        this.filaEspera = new Fila(50);
    }

    /**
     * Inscreve um participante, se houver vaga ou adiciona à fila de espera.
     *
     * @param p Participante a ser inscrito.
     * @return true se a inscrição for realizada, false caso contrário.
     */
    public boolean inscreverParticipante(Participante p) {
        if (p == null || participantes.contem(p)) {
            return false;
        }

        if (verificarDisponibilidade()) {
            participantes.anexar(p);
            if (!p.getPalestrasInscritas().contem(this)) {
                p.getPalestrasInscritas().anexar(this);
            }
            return true;
        } else {
            return adicionarFilaEspera(p);
        }
    }

    /**
     * Cancela a inscrição de um participante. Caso haja fila de espera,
     * o próximo é inscrito automaticamente.
     *
     * @param idParticipante ID do participante a ser removido.
     * @return true se a inscrição foi cancelada, false caso contrário.
     */
    public boolean cancelarInscricao(String idParticipante) {
        for (int i = 0; i < participantes.getTamanho(); i++) {
            Participante p = (Participante) participantes.selecionar(i);
            if (p.getId().equals(idParticipante)) {
                participantes.apagar(i);
                p.removerPalestra(this);

                if (!filaEspera.estaVazia()) {
                    Participante proximo = (Participante) filaEspera.desenfileirar();
                    inscreverParticipante(proximo);
                    String mensagem = "Sua inscrição na palestra '" + titulo + "' foi confirmada.\n" +
                            "Descrição: " + descricao + "\n" +
                            "Local: " + local + "\n" +
                            "Horário: " + horario.toString();
                    proximo.receberNotificacao(mensagem);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Adiciona participante à fila de espera e envia notificação.
     *
     * @param p Participante.
     * @return true se adicionado, false se fila cheia.
     */
    public boolean adicionarFilaEspera(Participante p) {
        if (filaEspera.estaCheia()) {
            return false;
        }

        filaEspera.enfileirar(p);
        String mensagem = "Você foi adicionado à fila de espera da palestra:\n" +
                "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Será notificado caso uma vaga seja liberada.";
        p.receberNotificacao(mensagem);
        return true;
    }

    /**
     * Verifica se ainda há vagas para a palestra.
     *
     * @return true se há vaga, false se lotada.
     */
    public boolean verificarDisponibilidade() {
        return participantes.getTamanho() < limiteParticipantes;
    }

    /**
     * Altera o horário da palestra e notifica os participantes.
     *
     * @param novoHorario Novo horário.
     * @return true se alterado, false se inválido.
     */
    public boolean alterarHorario(LocalDateTime novoHorario) {
        if (novoHorario == null || novoHorario.isBefore(LocalDateTime.now())) {
            return false;
        }

        this.horario = novoHorario;
        notificarParticipantes("O horário da palestra foi alterado:\n" +
                "Título: " + titulo + "\n" +
                "Novo Horário: " + novoHorario.toString() + "\n" +
                "Descrição: " + descricao);
        return true;
    }

    /**
     * Envia uma mensagem a todos os participantes inscritos.
     *
     * @param mensagem Texto da notificação.
     */
    public void notificarParticipantes(String mensagem) {
        String notificacaoCompleta = mensagem + "\n" +
                "Local: " + local + "\n" +
                "Palestrante: " + palestrante;

        for (int i = 0; i < participantes.getTamanho(); i++) {
            Participante p = (Participante) participantes.selecionar(i);
            p.receberNotificacao(notificacaoCompleta);
        }
    }

    /**
     * Verifica se há conflito de horário com outra palestra.
     *
     * @param outra Outra palestra.
     * @return true se houver conflito, false caso contrário.
     */
    public boolean verificarConflitoHorario(Palestra outra) {
        LocalDateTime fimEsta = this.horario.plusMinutes(this.duracao);
        LocalDateTime fimOutra = outra.getHorario().plusMinutes(outra.getDuracao());

        return this.horario.isBefore(fimOutra) && fimEsta.isAfter(outra.getHorario());
    }

    /**
     * Retorna os detalhes da palestra em formato de texto.
     *
     * @return String com os detalhes.
     */
    public String exibirDetalhes() {
        return "Título: " + titulo + "\n" +
                "Descrição: " + descricao + "\n" +
                "Horário: " + horario + "\n" +
                "Duração: " + duracao + " minutos\n" +
                "Local: " + local + "\n" +
                "Palestrante: " + palestrante + "\n" +
                "Limite: " + limiteParticipantes + "\n" +
                "Inscritos: " + participantes.getTamanho();
    }

    // === Getters ===

    public String getId() {
        return id;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getLocal() {
        return local;
    }

    public Lista getParticipantes() {
        return participantes;
    }

    public Fila getFilaEspera() {
        return filaEspera;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getPalestrante() {
        return palestrante;
    }

    public int getLimiteParticipantes() {
        return limiteParticipantes;
    }

    /**
     * Representação textual da palestra.
     *
     * @return String com informações básicas.
     */
    @Override
    public String toString() {
        return "Palestra: " + getTitulo() + "\n" +
                "ID: " + getId() + "\n" +
                "Palestrante: " + getPalestrante() + "\n" +
                "Local: " + getLocal() + "\n" +
                "Horário: " + getHorarioInicio() + " - " + getHorarioFinal() + "\n" +
                "Descrição: " + getDescricao() + "\n" +
                "Inscritos: " + (listarParticipantes() != null ? listarParticipantes().length : 0);
    }

    /**
     * Lista todos os participantes da palestra.
     *
     * @return Array de participantes.
     */
    public Participante[] listarParticipantes() {
        Object[] objetosParticipantes = participantes.selecionarTodos();
        Participante[] arrayParticipantes = new Participante[objetosParticipantes.length];

        for (int i = 0; i < objetosParticipantes.length; i++) {
            arrayParticipantes[i] = (Participante) objetosParticipantes[i];
        }

        return arrayParticipantes;
    }
}
