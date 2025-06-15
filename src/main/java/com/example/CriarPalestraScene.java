package com.example;

import java.time.format.DateTimeFormatter;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Gerencia a cena para criação e cadastro de novas palestras.
 * Permite ao usuário inserir detalhes da palestra e associá-la a um evento existente.
 * Realiza validação de dados e verifica conflitos de horário/local.
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
 * @version 1.1
 * @since 2025-06-12
 */
public class CriarPalestraScene {

    /** Formato de data utilizado nas interfaces. */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Referência estática para a última palestra cadastrada. */
    public static Palestra palestraCadastrada;

    /**
     * Cria e retorna a cena de cadastro de palestra.
     * Inclui campos de formulário para detalhes da palestra, validação de entrada
     * e um botão para salvar a palestra, verificando conflitos.
     *
     * @param stage O palco principal da aplicação.
     * @param nomeUsuario O nome do usuário logado, exibido no cabeçalho.
     * @return A cena de cadastro de palestra configurada.
     */
    public static Scene criarPalestraScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Topo com navegação
        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);
        
        Label titulo = new Label("Cadastre suas palestras");
        titulo.setFont(Font.font("Arial", 22));
        titulo.setTextFill(Color.web("#333"));

        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        HBox mainContent = new HBox(40);
        mainContent.setPadding(new Insets(20));

        // Coluna esquerda do formulário
        VBox formBoxL = new VBox(15);
        TextField idEvento = new TextField();
        idEvento.setPromptText("Insira ID");

        TextField nomePalestrante = new TextField();
        nomePalestrante.setPromptText("Insira nome da palestrante");

        TextField nomePalestra = new TextField();
        nomePalestra.setPromptText("Insira nome da palestra");

        TextField local = new TextField();
        local.setPromptText("Insira nome do local");

        DatePicker data = new DatePicker(); // Campo de seleção de data

        // Coluna direita do formulário
        VBox formBoxR = new VBox(15);
        TextField horarioInicio = new TextField();
        horarioInicio.setPromptText("19:00");

        TextField vagas = new TextField();
        vagas.setPromptText("30");

        TextField horarioFim = new TextField();
        horarioFim.setPromptText("20:00");

        TextArea descricao = new TextArea();
        descricao.setPromptText("Insira descrição");
        descricao.setPrefRowCount(3);

        // Adiciona campos à coluna esquerda
        formBoxL.getChildren().addAll(
                criarLabelComCampo("ID do evento da palestra", idEvento),
                criarLabelComCampo("Palestrante", nomePalestrante),
                criarLabelComCampo("Título", nomePalestra),
                criarLabelComCampo("Local", local),
                criarLabelComCampo("Data", data)
        );

        // Adiciona campos à coluna direita
        formBoxR.getChildren().addAll(
                criarLabelComCampo("Horário de início", horarioInicio),
                criarLabelComCampo("Total vagas", vagas),
                criarLabelComCampo("Horário final", horarioFim),
                criarLabelComCampo("Descrição", descricao)
        );

        formBoxL.setAlignment(Pos.TOP_LEFT);
        formBoxR.setAlignment(Pos.TOP_LEFT);

        Label pltErroLabel = new Label(); // Label para mensagens de erro
        pltErroLabel.setTextFill(Color.RED);
        pltErroLabel.setVisible(false); // Inicialmente invisível

        mainContent.getChildren().addAll(formBoxL, formBoxR);
        mainContent.setAlignment(Pos.TOP_CENTER);
        conteudo.getChildren().addAll(titulo, mainContent, pltErroLabel);
        conteudo.setAlignment(Pos.TOP_CENTER);

        root.setCenter(conteudo);

        // Botão para salvar a palestra
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #3B4EFF; -fx-text-fill: white; -fx-font-size: 14px;");
        btnSalvar.setOnAction(e -> {
            try {
                // Extrai o ID do evento e busca na lista
                int id = Integer.parseInt(idEvento.getText().substring(2)); // Supondo formato "EV123"
                Evento evento = (Evento) TelaInicial.eventos.selecionar(id);
                
                if (evento == null) {
                    pltErroLabel.setText("Evento não encontrado!");
                    pltErroLabel.setVisible(true);
                    return;
                }
                
                // Cria uma nova palestra com os dados do formulário
                Palestra palestra = new Palestra(
                    nomePalestra.getText(), descricao.getText(), horarioInicio.getText(),
                    horarioFim.getText(), data.getValue(), local.getText(),
                    nomePalestrante.getText(), Integer.parseInt(vagas.getText())
                );
                
                // Verifica conflito de horário/local no evento
                boolean livre = evento.verificarConflitoHorario(palestra);
                if (!livre) { // Se não houver conflito (ou seja, está livre)
                    pltErroLabel.setVisible(false);
                    palestraCadastrada = palestra; // Armazena a palestra cadastrada
                    evento.adicionarPalestra(palestraCadastrada); // Adiciona ao evento
                    System.out.println("Palestra salva: " + nomePalestra.getText());
                    
                    // Navega para a cena do menu após o sucesso
                    Scene menuScene = MenuScene.menuScene(stage, nomeUsuario);
                    stage.setScene(menuScene);
                } else {
                    pltErroLabel.setText("Conflito de horário ou sala."); // Mensagem de conflito
                    pltErroLabel.setVisible(true);
                }
            } catch (NumberFormatException ex) {
                pltErroLabel.setText("ID do evento inválido!! Use o formato correto (ex: EV0).");
                pltErroLabel.setVisible(true);
            } catch (Exception ex) {
                pltErroLabel.setText("Erro ao cadastrar palestra. Verifique os dados.");
                pltErroLabel.setVisible(true);
                ex.printStackTrace(); // Para depuração, remover em produção
            }
        });

        HBox salvarBox = new HBox(btnSalvar);
        salvarBox.setAlignment(Pos.CENTER);
        salvarBox.setPadding(new Insets(20, 0, 0, 0));

        root.setBottom(salvarBox);

        return new Scene(root, 950, 650);
    }

    /**
     * Cria um VBox contendo um Label e um Control, para organizar campos de formulário.
     *
     * @param labelTexto O texto do Label.
     * @param campo O campo de controle (TextField, DatePicker, etc.).
     * @return Um VBox com o Label e o Control.
     */
    private static VBox criarLabelComCampo(String labelTexto, Control campo) {
        Label label = new Label(labelTexto);
        label.setStyle("-fx-text-fill: #5f5f5f; -fx-font-weight: bold;");
        VBox box = new VBox(5);
        box.getChildren().addAll(label, campo);
        return box;
    }
}