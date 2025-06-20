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
/**
 * Classe responsável pela cena de cadastro de participantes.Add commentMore actions
 * Esta classe fornece uma interface gráfica para cadastrar novos participantes,
 * com validação de e-mail único e armazenamento na lista de participantes.
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
 * * @version 1.1
 * @since 2025-05-25
 */
public class CadastroParticipanteScene {
    
    /** Variável para participante usuário */
    public static Participante participanteCadastrado;
    
    /** Lista de participantes */
    public static Lista participantes = new Lista(20);

    /**Add commentMore actions
     * Cria e configura a cena principal para o cadastro de um novo participante.
     * Este método inicializa o layout base, configura a barra superior e o conteúdo central
     * que inclui o formulário de cadastro e os botões de ação.
     *
     * @param stage O Stage principal da aplicação JavaFX onde a cena será exibida.
     * @return Uma instância de Scene configurada e pronta para ser exibida,
     * representando a interface de cadastro de participantes.
     */
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
                Scene menuScene = MenuScene.menuScene(stage, nome + "\nID: " + participanteCadastrado.getId());
                stage.setScene(menuScene);
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
