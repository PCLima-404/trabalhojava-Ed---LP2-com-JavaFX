package com.example;

import java.time.LocalDateTime;

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
    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }

    private Lista participantes;
    private Fila filaEspera;

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



    // Inscrever participante e atualizar lista do participante
    public boolean inscreverParticipante(Participante p) {
        if (p == null || participantes.contem(p)) {
            return false;
        }

        if (verificarDisponibilidade()) {
            participantes.anexar(p);
            // Só adiciona na lista do participante se ainda não estiver nela
            if (!p.getPalestrasInscritas().contem(this)) {
                p.getPalestrasInscritas().anexar(this);
            }
            return true;
        } else {
            return adicionarFilaEspera(p);
        }
    }

    // Cancelar inscrição e remover da lista do participante
    public boolean cancelarInscricao(String idParticipante) {
        for (int i = 0; i < participantes.getTamanho(); i++) {
            Participante p = (Participante) participantes.selecionar(i);
            if (p.getId().equals(idParticipante)) {
                participantes.apagar(i);
                // Remove esta palestra da lista do participante
                p.removerPalestra(this);

                // Se fila espera não vazia, matricula próximo e notifica
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

    public boolean verificarDisponibilidade() {
        return participantes.getTamanho() < limiteParticipantes;
    }

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

    public void notificarParticipantes(String mensagem) {
        String notificacaoCompleta = mensagem + "\n" +
                                   "Local: " + local + "\n" +
                                   "Palestrante: " + palestrante;

        for (int i = 0; i < participantes.getTamanho(); i++) {
            Participante p = (Participante) participantes.selecionar(i);
            p.receberNotificacao(notificacaoCompleta);
        }
    }

    public boolean verificarConflitoHorario(Palestra outra) {
        LocalDateTime fimEsta = this.horario.plusMinutes(this.duracao);
        LocalDateTime fimOutra = outra.getHorario().plusMinutes(outra.getDuracao());

        return this.horario.isBefore(fimOutra) && fimEsta.isAfter(outra.getHorario());
    }

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

    // Getters

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

           public Participante[] listarParticipantes() {
        Object[] objetosParticipantes = participantes.selecionarTodos();
    Participante[] arrayParticipantes = new Participante[objetosParticipantes.length];

    for (int i = 0; i < objetosParticipantes.length; i++) {
        arrayParticipantes[i] = (Participante) objetosParticipantes[i];
    }

    return arrayParticipantes;
}
    
}
