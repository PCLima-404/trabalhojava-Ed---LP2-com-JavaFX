package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginScene {

    public static Scene loginScene(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Topo com menu
        HBox topMenu = TopoSemMenu.createTopBar(stage, "Participante"); // Reaproveitando barra superior
        root.setTop(topMenu);

        // Formulário central
        VBox loginBox = new VBox(15);
        loginBox.setPadding(new Insets(40));
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        Label loginTitle = new Label("Login");
        loginTitle.setFont(Font.font("Arial", 24));
        loginTitle.setTextFill(Color.web("#3b3b98"));

        // Campo e-mail
        Label emailLabel = new Label("E-mail");
        emailLabel.setTextFill(Color.web("#7a7adb"));
        TextField emailField = new TextField();
        emailField.setPromptText("Insira seu usuário");
        emailField.setPrefWidth(300);

        // Campo ID
        Label idLabel = new Label("ID");
        idLabel.setTextFill(Color.web("#7a7adb"));
        PasswordField idField = new PasswordField();
        idField.setPromptText("********");

        Hyperlink esqueciId = new Hyperlink("Esqueceu seu ID?");
        esqueciId.setStyle("-fx-text-fill: #7a7adb;");
        esqueciId.setBorder(Border.EMPTY);
        
        Label loginErroLabel = new Label("Login incorreto!");
        loginErroLabel.setTextFill(Color.RED);
        loginErroLabel.setVisible(false); // Inicialmente invisível

        // Botão Entrar
        Button loginBtn = new Button("Entrar");
        loginBtn.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 10;");
        loginBtn.setOnAction(e -> {
        String emailUsuario = emailField.getText(); // ou uma extração de nome real
        String idUsuario = idField.getText();
        
        boolean certo = false;
        for (int i = 0; i < CadastroParticipanteScene.participantes.getTamanho(); i++) {
            if(emailUsuario.equals(((Participante) CadastroParticipanteScene.participantes.selecionar(i)).getEmail()) && idUsuario.equalsIgnoreCase(((Participante) CadastroParticipanteScene.participantes.selecionar(i)).getId())){
                CadastroParticipanteScene.participanteCadastrado = (Participante) CadastroParticipanteScene.participantes.selecionar(i);
                certo = true;
                break;
            }

        }
        if(certo){
            loginErroLabel.setVisible(false);
            Scene homeScene = MenuScene.menuScene(stage, CadastroParticipanteScene.participanteCadastrado.getNome());
            stage.setScene(homeScene);
        } else {
            loginErroLabel.setVisible(true);
        }

        });

        loginBox.getChildren().addAll(loginTitle, emailLabel, emailField, idLabel, idField, loginBtn, loginErroLabel);

        VBox contentWrapper = new VBox(loginBox);
        contentWrapper.setAlignment(Pos.CENTER);
        contentWrapper.setPadding(new Insets(30, 0, 0, 0));

        root.setCenter(contentWrapper);
        return new Scene(root, 800, 500);
    }
}

