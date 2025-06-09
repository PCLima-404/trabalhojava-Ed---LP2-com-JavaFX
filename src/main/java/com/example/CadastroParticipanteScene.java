package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CadastroParticipanteScene {
    
    /** Variável para participante usuário */
    public static Participante participanteCadastrado;
    
    /** Lista de participantes */
    public static Lista participantes = new Lista(20);


    public static Scene cadastroScene(Stage stage) {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Topo com menu
        HBox topMenu = TopoSemMenu.createTopBar(stage, "Participante"); // Reaproveitando barra superior
        root.setTop(topMenu);


        // Título
        Label title = new Label("Cadastro do participante");
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.web("#2d2d2d"));

        // Formulário
        VBox form = new VBox(20);
        form.setAlignment(Pos.TOP_LEFT);

        // Campo nome
        Label nomeLabel = new Label("Nome");
        nomeLabel.setTextFill(Color.web("#7a7adb"));
        TextField nomeField = new TextField();
        nomeField.setPromptText("Insira nome do participante");
        nomeField.setPrefWidth(400);

        // Campo E-mail
        Label emailLabel = new Label("E-mail");
        emailLabel.setTextFill(Color.web("#7a7adb"));
        TextField emailField = new TextField();
        emailField.setPromptText("Insira o e-mail do participante");
        emailField.setPrefWidth(400);

        Label emailErroLabel = new Label("Este e-mail já está cadastrado.");
        emailErroLabel.setTextFill(Color.RED);
        emailErroLabel.setVisible(false); // Inicialmente invisível

        //Adição dos campos ao formulário
        form.getChildren().addAll(nomeLabel, nomeField, emailLabel, emailField, emailErroLabel);


        // Botão Salvar
        Button salvarBtn = new Button("Salvar");
        salvarBtn.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        salvarBtn.setOnAction(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            boolean emailJaExiste = false;

            for ( int i = 0; i < participantes.getTamanho(); i++) {
                if(email.equals(((Participante) participantes.selecionar(i)).getEmail())){
                    emailJaExiste = true;
                    break;
                }

            }
            
            if (emailJaExiste) {
                emailErroLabel.setVisible(true);
            } else {
                emailErroLabel.setVisible(false);
                participanteCadastrado = new Participante(nome, email);
                participantes.anexar(participanteCadastrado);
                Scene scene = MenuScene.menuScene(stage, nome);
                stage.setScene(scene);
            }
            
        });

        Button cancelarBtn = new Button("Cancelar");
        cancelarBtn.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        cancelarBtn.setOnAction(e -> {
            Scene telaInicial = TelaInicial.criarTelaInicial(stage);
            stage.setScene(telaInicial);
        });

        // Layout principal
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER_LEFT);
        centerBox.getChildren().addAll(title, form, salvarBtn, cancelarBtn);

        root.setCenter(centerBox);
        return new Scene(root, 800, 500);
    }
}
