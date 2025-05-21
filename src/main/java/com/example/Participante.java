package com.example;

public class Participante {
    private String id;
    private String nome;
    private Lista palestrasInscritas;
    private String email;

    public String getEmail() {
        return email;
    }

    public Participante(String id, String nome,String email) {
        this.id = id;
        this.nome = nome;
        this.email =email;
        this.palestrasInscritas = new Lista(20); // limite de palestras inscritas por participante
    }

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

    public Palestra[] listarPalestrasInscritas() {
        Object[] palestras = palestrasInscritas.selecionarTodos();
        return java.util.Arrays.copyOf(palestras, palestras.length, Palestra[].class);
    }

    public void receberNotificacao(String mensagem) {
        System.out.println("Notificação para " + nome + ": " + mensagem);
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Lista getPalestrasInscritas() {
        return palestrasInscritas;
    }
}
