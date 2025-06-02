package com.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * Classe principal da aplicação JavaFX para gerenciamento de eventos, palestras
 * e participantes.
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
 * 
 * @since 28-05-2025
 * @version 1.0
 */
public class App extends Application {

    /** Lista personalizada para armazenar os eventos */
    private Lista eventos = new Lista(20);

    private ListView<Evento> lvEventos = new ListView<>();
    private ListView<Palestra> lvPalestras = new ListView<>();
    private TextArea taDetalhes = new TextArea();
    private Participante participanteCadastrado;

    /** Formato de data utilizado nas interfaces */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Referência global á tela principal */
    private Stage primaryStage;


    /**
     * Inicializa a interface gráfica da aplicação.
     *
     * @param primaryStage Janela principal do JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Título
        Label inicio = new Label("Você é:");
        inicio.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");

        // Botões de administrador e participante
        Button btnAdmin = new Button("Administrador");
        Button btnParticipante = new Button("Participante");

        // Ações dos botões
        btnAdmin.setOnAction(e -> abrirCenaAdministrador());
        btnParticipante.setOnAction(e -> abrirCenaCadastro());

        // Layout
        VBox layout = new VBox(15); 
        layout.getChildren().addAll(inicio, btnAdmin, btnParticipante);
        layout.setStyle("-fx-alignment: center; -fx-padding: 30px;");

        // Cena principal
        Scene cenaPrin = new Scene(layout, 300, 200);
        primaryStage.setTitle("EVENTRIX");
        primaryStage.setScene(cenaPrin);
        primaryStage.show();
    }
    
    /**
     * Exibe interface para o usuário administrador.
     */
    private void abrirCenaAdministrador() {
        System.out.println("Administrador selecionado");
        
        //Lista de eventos
        lvEventos.setPrefWidth(250);
        lvEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            mostrarPalestrasEvento(newVal);
        });

        // Lista de palestras do evento selecionado
        lvPalestras.setPrefWidth(300);
        lvPalestras.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            mostrarDetalhesPalestra(newVal);
        });

        // Área de exibição de detalhes
        taDetalhes.setEditable(false);
        taDetalhes.setPrefWidth(300);
        taDetalhes.setPrefHeight(150);

        // Botões de ação
        Button btnAddEvento = new Button("Adicionar Evento");
        btnAddEvento.setOnAction(e -> adicionarEvento());

        Button btnAddPalestra = new Button("Adicionar Palestra");
        btnAddPalestra.setOnAction(e -> adicionarPalestra());

        Button btnInicial = new Button("Voltar ao início");
        btnInicial.setOnAction(e -> start(primaryStage));

        HBox hBoxBotoes = new HBox(10, btnAddEvento, btnAddPalestra, btnInicial);
        hBoxBotoes.setPadding(new Insets(10));

        HBox hBoxListas = new HBox(10, lvEventos, lvPalestras, taDetalhes);
        hBoxListas.setPadding(new Insets(10));

        VBox vBoxPrincipal = new VBox(10, hBoxListas, hBoxBotoes);
        vBoxPrincipal.setPadding(new Insets(10));

        Scene cenaAdm = new Scene(vBoxPrincipal, 900, 400);
        primaryStage.setScene(cenaAdm);
        primaryStage.show();
    }

    /**
     * Exibe tela de cadastro para usuário participante
     * 
     * @param callback função consumidora que será passada quando o participante for criado
     */
    private void abrirCenaCadastro() {
        System.out.println("Participante selecionado");

        // Campos de entrada
        Label lblNome = new Label("Nome:");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome");

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu email");

        // Botão de cadastro
        Button btnCadastrar = new Button("Cadastrar");

        // Ação do botão
        btnCadastrar.setOnAction(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();

            if (nome.isEmpty() || email.isEmpty()) {
                Alert alerta = new Alert(Alert.AlertType.WARNING, "Por favor, preencha todos os campos.");
                alerta.showAndWait();
            } else {
                Participante participante = new Participante(nome, email);
                this.participanteCadastrado = participante;
                System.out.println("Participante cadastrado:");
                System.out.println("Nome: " + nome);
                System.out.println("Email: " + email);
                Alert sucesso = new Alert(Alert.AlertType.INFORMATION, "Cadastro realizado com sucesso!");
                sucesso.showAndWait();
                abrirCenaParticipante();
                // Limpa os campos após o cadastro
                txtNome.clear();
                txtEmail.clear();
            }
        });

        // Layout da tela
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");
        layout.getChildren().addAll(lblNome, txtNome, lblEmail, txtEmail, btnCadastrar);

        Scene cenaCadastro = new Scene(layout, 300, 250);
        primaryStage.setScene(cenaCadastro);
        primaryStage.show();
    }

    /**
     * Exibe interface para usuário participante.
     */
    private void abrirCenaParticipante(){
        // Mesma lista de eventos
        lvEventos.setPrefWidth(250);
        lvEventos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            mostrarPalestrasEvento(newVal);
        });

        // Mesma lista de palestras do evento selecionado
        lvPalestras.setPrefWidth(300);
        lvPalestras.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            mostrarDetalhesPalestra(newVal);
        });

        // Área de exibição de detalhes
        taDetalhes.setEditable(false);
        taDetalhes.setPrefWidth(300);
        taDetalhes.setPrefHeight(150);

        Button btnInscrever = new Button("Quero me inscrever!");
        btnInscrever.setOnAction(e -> inscreverParticipante());

        Button btnInicial = new Button("Voltar ao início");
        btnInicial.setOnAction(e -> start(primaryStage));

        HBox hBoxBotoes = new HBox(10, btnInscrever, btnInicial);
        hBoxBotoes.setPadding(new Insets(10));

        HBox hBoxListas = new HBox(10, lvEventos, lvPalestras, taDetalhes);
        hBoxListas.setPadding(new Insets(10));

        VBox vBoxPrincipal = new VBox(10, hBoxListas, hBoxBotoes);
        vBoxPrincipal.setPadding(new Insets(10));

        Scene cenaPtc = new Scene(vBoxPrincipal, 900, 400);
        primaryStage.setScene(cenaPtc);
        primaryStage.show();
    }

    /** 
     * Exibe palestras relacionadas ao evento selecionado.
     *
     * @param evento Evento selecionado.
     */
    private void mostrarPalestrasEvento(Evento evento) {
        lvPalestras.getItems().clear();
        taDetalhes.clear();
        if (evento != null) {
            taDetalhes.setText(evento.toString());
            Palestra[] palestras = evento.listarPalestras();
            lvPalestras.getItems().addAll(palestras);
        }
    }

    /**
     * Mostra os detalhes de uma palestra, incluindo participantes inscritos.
     *
     * @param palestra Palestra selecionada.
     */
    private void mostrarDetalhesPalestra(Palestra palestra) {
        taDetalhes.clear();
        if (palestra != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(palestra.toString()).append("\n\n");
            sb.append("Participantes Inscritos:\n");

            Participante[] participantes = palestra.listarParticipantes();
            if (participantes != null && participantes.length > 0) {
                for (Participante p : participantes) {
                    if (p != null) {
                        sb.append("- ").append(p.getNome()).append(" (").append(p.getEmail()).append(")\n");
                    }
                }
            } else {
                sb.append("Nenhum participante inscrito.\n");
            }

            taDetalhes.setText(sb.toString());
        }
    }

    /**
     * Abre uma caixa de diálogo para adicionar um novo evento.
     */
    private void adicionarEvento() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Evento");
        dialog.setHeaderText("Informe os dados do evento (nome, descrição, data início, data fim):");
        dialog.setContentText("Formato: nome;descrição;dd/MM/yyyy;dd/MM/yyyy");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] partes = result.get().split(";");
                if (partes.length == 4) {
                    String nome = partes[0].trim();
                    String descricao = partes[1].trim();
                    LocalDate dataInicio = LocalDate.parse(partes[2].trim(), formatter);
                    LocalDate dataFim = LocalDate.parse(partes[3].trim(), formatter);

                    Evento evento = new Evento(nome, descricao, dataInicio, dataFim);
                    eventos.anexar(evento);
                    atualizarListaEventos();
                } else {
                    mostrarAlerta("Erro", "Dados incompletos. Use o formato correto.");
                }
            } catch (Exception ex) {
                mostrarAlerta("Erro", "Formato de data inválido.");
            }
        }
    }

    /**
     * Adiciona uma nova palestra ao evento selecionado.
     */
    private void adicionarPalestra() {
        Evento eventoSelecionado = lvEventos.getSelectionModel().getSelectedItem();
        if (eventoSelecionado == null) {
            mostrarAlerta("Aviso", "Selecione um evento antes de adicionar palestra.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Palestra");
        dialog.setHeaderText(
                "Informe os dados da palestra (título;palestrante;local;hora início;hora fim;descrição;limite de participantes):");
        dialog.setContentText("Exemplo: Título;Palestrante;Sala 1;09:00;10:00;Descrição;40");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                String[] partes = result.get().split(";");
                if (partes.length == 7) {
                    String titulo = partes[0].trim();
                    String palestrante = partes[1].trim();
                    String local = partes[2].trim();
                    String horarioInicio = partes[3].trim();
                    String horarioFinal = partes[4].trim();
                    String descricao = partes[5].trim();
                    int limiteParticipantes = Integer.parseInt(partes[6].trim());

                    Palestra palestra = new Palestra(titulo, descricao, horarioInicio, horarioFinal, 0, local, palestrante, limiteParticipantes);

                    boolean adicionou = eventoSelecionado.adicionarPalestra(palestra);
                    if (adicionou) {
                        mostrarPalestrasEvento(eventoSelecionado);
                    } else {
                        mostrarAlerta("Erro", "Não foi possível adicionar a palestra (conflito ou erro).");
                    }
                } else {
                    mostrarAlerta("Erro", "Dados incompletos. Use o formato correto.");
                }
            } catch (Exception ex) {
                mostrarAlerta("Erro", "Erro ao adicionar palestra: " + ex.getMessage());
            }
        }
    }

    /**
     * Exibe uma janela de alerta com uma mensagem personalizada.
     *
     * @param titulo   Título da janela.
     * @param mensagem Conteúdo da mensagem.
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
    
    /**
     * Atualiza a lista visual de eventos exibida na interface.
     */
    private void atualizarListaEventos() {
        lvEventos.getItems().clear();
        Object[] eventosArray = eventos.selecionarTodos();
        for (Object obj : eventosArray) {
            lvEventos.getItems().add((Evento) obj);
        }
    }

    /**
     * Inscreve o participante na palestra selecionada.
     */
    private void inscreverParticipante() {
        Palestra palestraSelecionada = lvPalestras.getSelectionModel().getSelectedItem();
        if (palestraSelecionada == null) {
            mostrarAlerta("Aviso", "Selecione uma palestra para inscrever participante.");
            return;
        }

        try {
            boolean inscrito = palestraSelecionada.inscreverParticipante(participanteCadastrado);
            if (inscrito) {
                participanteCadastrado.inscreverEmPalestra(palestraSelecionada);
                mostrarAlerta("Sucesso", "Participante inscrito com sucesso.");
            } else {
                mostrarAlerta("Erro",
                            "Não foi possível inscrever participante (palestra cheia ou já inscrito).");
            }
        } catch (Exception ex) {
            mostrarAlerta("Erro", "Erro ao inscrever participante: " + ex.getMessage());
        }
    
    }

    /**
     * Método principal. Lança a aplicação JavaFX.
     *
     * @param args Argumentos da linha de comando.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
