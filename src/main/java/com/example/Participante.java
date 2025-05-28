package com.example;
/**
 * Representa um participante de uma palestra.
 * Cada participante possui um ID, nome, e-mail e uma lista de palestras
 * inscritas.
 * 
 * @author Grupo 1: Eduardo Berlink, Mary Nicole, João Lucas, Marco Antonio,
 *         Arthur Sousa,
 *         Henrique Rezende, Ana Gomes Souza e Pedro Cezar.
 * 
 * @since 28-05-2025
 * @version 1.0
 */
public class Participante {
    private String id;
    private String nome;
    private Lista palestrasInscritas;
    private String email;

    /**
     * Retorna o e-mail do participante.
     * 
     * @return e-mail do participante
     */
    public String getEmail() {
        return email;
    }

    /**
     * Construtor da classe Participante.
     * 
     * @param id    Identificador único do participante
     * @param nome  Nome do participante
     * @param email E-mail do participante
     */
    public Participante(String id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.palestrasInscritas = new Lista(20); // limite de palestras inscritas por participante
    }

    /**
     * Inscreve o participante em uma palestra.
     * 
     * @param palestra Palestra em que deseja se inscrever
     * @return true se a inscrição for bem-sucedida, false caso contrário
     */
    public boolean inscreverEmPalestra(Palestra palestra) {
        if (palestra == null || palestrasInscritas.contem(palestra)) {
            return false;
        }

        boolean sucesso = palestra.inscreverParticipante(this);
        if (sucesso) {
            if (!palestrasInscritas.contem(palestra)) {
                palestrasInscritas.anexar(palestra);
            }
        }
        return sucesso;
    }

    /**
     * Cancela a inscrição do participante em uma palestra.
     * 
     * @param palestra Palestra da qual deseja se desinscrever
     * @return true se a inscrição foi cancelada com sucesso, false caso contrário
     */
    public boolean cancelarInscricao(Palestra palestra) {
        if (palestra == null || !palestrasInscritas.contem(palestra)) {
            return false;
        }

        boolean sucesso = palestra.cancelarInscricao(this.id);
        if (sucesso) {
            removerPalestra(palestra);
        }
        return sucesso;
    }

    /**
     * Remove uma palestra da lista de palestras inscritas do participante.
     * 
     * @param palestra Palestra a ser removida
     * @return true se a palestra foi removida com sucesso, false caso contrário
     */
    public boolean removerPalestra(Palestra palestra) {
        for (int i = 0; i < palestrasInscritas.getTamanho(); i++) {
            Palestra p = (Palestra) palestrasInscritas.selecionar(i);
            if (p.equals(palestra)) {
                palestrasInscritas.apagar(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Lista todas as palestras em que o participante está inscrito.
     * 
     * @return Array de objetos do tipo Palestra
     */
    public Palestra[] listarPalestrasInscritas() {
        Object[] palestras = palestrasInscritas.selecionarTodos();
        return java.util.Arrays.copyOf(palestras, palestras.length, Palestra[].class);
    }

    /**
     * Recebe uma notificação com uma mensagem.
     * 
     * @param mensagem Conteúdo da notificação
     */
    public void receberNotificacao(String mensagem) {
        System.out.println("Notificação para " + nome + ": " + mensagem);
    }

    // Getters

    /**
     * Retorna o ID do participante.
     * 
     * @return ID do participante
     */
    public String getId() {
        return id;
    }

    /**
     * Retorna o nome do participante.
     * 
     * @return Nome do participante
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a lista de palestras em que o participante está inscrito.
     * 
     * @return Lista de palestras
     */
    public Lista getPalestrasInscritas() {
        return palestrasInscritas;
    }
}
