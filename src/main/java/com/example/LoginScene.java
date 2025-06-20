package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
/**
 * Gerencia a cena de login da aplicação.
 * Permite que o usuário insira suas credenciais (e-mail e ID) para acessar o sistema,
 * validando-as contra a lista de participantes cadastrados.
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
 * @since 2025-05-25
 */
public class LoginScene {

    /**
     * Cria e retorna a cena de login da aplicação.
     * Configura a interface com campos para e-mail e ID, um botão de login
     * e exibe mensagens de erro em caso de credenciais inválidas.
     *
     * @param stage O palco principal da aplicação.
     * @return A cena de login configurada.
     */
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
            Scene menuScene = MenuScene.menuScene(stage, CadastroParticipanteScene.participanteCadastrado.getNome() + "\nID: " + CadastroParticipanteScene.participanteCadastrado.getId());
            stage.setScene(menuScene);
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

