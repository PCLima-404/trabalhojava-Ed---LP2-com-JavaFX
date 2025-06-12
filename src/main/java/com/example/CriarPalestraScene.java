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

public class CriarPalestraScene {
    /** Formato de data utilizado nas interfaces */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Palestra palestraCadastrada;

    /**
     * Método para cadastro de palestra em determinado evento.
     * 
     * @param stage Tela em que a cena será exibida.
     * @param nomeUsuario Nome do usuário no momento em que a cena é chamada.
     * @return Scene Cena de cadastro de palestra.
     */
    public static Scene criarPalestraScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Topo com navegação
        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);
        
        // Título fora dos VBoxes
        Label titulo = new Label("Cadastre suas palestras");
        titulo.setFont(Font.font("Arial", 22));
        titulo.setTextFill(Color.web("#333"));

        // VBox do conteúdo da tela
        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));


        HBox mainContent = new HBox(40);
        mainContent.setPadding(new Insets(20));

        // Coluna esquerda: formulário de cadastro
        VBox formBoxL = new VBox(15);

        TextField idEvento = new TextField();
        idEvento.setPromptText("Insira ID");

        TextField nomePalestrante = new TextField();
        nomePalestrante.setPromptText("Insira nome da palestrante");

        TextField nomePalestra = new TextField();
        nomePalestra.setPromptText("Insira nome da palestra");

        TextField local = new TextField();
        local.setPromptText("Insira nome do local");

        VBox formBoxR = new VBox(15);

        DatePicker data = new DatePicker();
        TextField horarioInicio = new TextField();
        horarioInicio.setPromptText("19:00");

        TextField vagas = new TextField();
        vagas.setPromptText("30");
        TextField horarioFim = new TextField();
        horarioFim.setPromptText("20:00");

        TextArea descricao = new TextArea();
        descricao.setPromptText("Insira descrição");
        descricao.setPrefRowCount(3);

        formBoxL.getChildren().addAll(
                criarLabelComCampo("ID do evento da palestra", idEvento),
                criarLabelComCampo("Palestrante", nomePalestrante),
                criarLabelComCampo("Título", nomePalestra),
                criarLabelComCampo("Local", local),
                criarLabelComCampo("Data", data)
                
        );

        formBoxR.getChildren().addAll(
                criarLabelComCampo("Horário de início", horarioInicio),
                criarLabelComCampo("Total vagas", vagas),
                criarLabelComCampo("Horário final", horarioFim),
                criarLabelComCampo("Descrição", descricao)
        );

        formBoxL.setAlignment(Pos.TOP_LEFT);
        formBoxR.setAlignment(Pos.TOP_LEFT);

        Label pltErroLabel = new Label();
        pltErroLabel.setTextFill(Color.RED);
        pltErroLabel.setVisible(false); // Inicialmente invisível

        mainContent.getChildren().addAll(formBoxL, formBoxR);
        mainContent.setAlignment(Pos.TOP_CENTER);
        conteudo.getChildren().addAll(titulo, mainContent, pltErroLabel);
        conteudo.setAlignment(Pos.TOP_CENTER);

        root.setCenter(conteudo);

        // Botão salvar
        Button btnSalvar = new Button("Salvar");
        btnSalvar.setStyle("-fx-background-color: #3B4EFF; -fx-text-fill: white; -fx-font-size: 14px;");
        btnSalvar.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idEvento.getText().substring(2));
                Evento evento = (Evento) TelaInicial.eventos.selecionar(id);
                if (evento == null) {
                    pltErroLabel.setText("Evento não encontrado!");
                    pltErroLabel.setVisible(true);
                    return;
                }
                Palestra palestra = new Palestra(
                    nomePalestra.getText(), descricao.getText(), horarioInicio.getText(),
                    horarioFim.getText(), data.getValue(), local.getText(),
                    nomePalestrante.getText(), Integer.parseInt(vagas.getText())
                );
                boolean livre = evento.verificarConflitoHorario(palestra);
                if (!livre) {
                    pltErroLabel.setVisible(false);
                    palestraCadastrada = palestra;
                    evento.adicionarPalestra(palestraCadastrada);
                    System.out.println("Palestra salva: " + nomePalestra.getText());
                    Scene menuScene = MenuScene.menuScene(stage, nomeUsuario);
                    stage.setScene(menuScene);
                }
            } catch (NumberFormatException ex) {
                pltErroLabel.setText("ID do evento inválido!");
                pltErroLabel.setVisible(true);
            } catch (Exception ex) {
                pltErroLabel.setText("Conflito de horário ou sala.");
                pltErroLabel.setVisible(true);
            }
        });

        HBox salvarBox = new HBox(btnSalvar);
        salvarBox.setAlignment(Pos.CENTER);
        salvarBox.setPadding(new Insets(20, 0, 0, 0));

        root.setBottom(salvarBox);

        return new Scene(root, 950, 650);
    }

    private static VBox criarLabelComCampo(String labelTexto, Control campo) {
        Label label = new Label(labelTexto);
        label.setStyle("-fx-text-fill: #5f5f5f; -fx-font-weight: bold;");
        VBox box = new VBox(5);
        box.getChildren().addAll(label, campo);
        return box;
    }

}

