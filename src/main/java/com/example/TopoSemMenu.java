package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Cria a barra superior de navegação simplificada, sem opções de menu.
 * Este componente é reutilizável em cenas onde a navegação completa não é necessária.
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
public class TopoSemMenu {

    /**
     * Cria e retorna a barra superior da aplicação sem links de menu.
     * Inclui o logo, o nome da aplicação e uma saudação ao usuário.
     *
     * @param stage O palco principal da aplicação (não usado para navegação nesta versão).
     * @param nomeUsuario O nome do usuário a ser exibido.
     * @return Um HBox configurado como a barra superior sem menu.
     */
    public static HBox createTopBar(Stage stage, String nomeUsuario) {
        HBox topBar = new HBox(50);
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: #ffffff;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Seção do Logo e Nome da Aplicação
        Image logoImg = new Image(TopoSemMenu.class.getResourceAsStream("/imagemLogo/E.png"));
        ImageView logo = new ImageView(logoImg);
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);

        Label titulo = new Label("EventFlow");
        titulo.setFont(Font.font("Arial", 20));
        titulo.setTextFill(Color.web("#3B4EFF"));

        HBox logoBox = new HBox(10, logo, titulo);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        // Exibição do nome do usuário
        Label usuarioLabel = new Label("Olá, " + nomeUsuario);
        usuarioLabel.setFont(Font.font(14));

        Region espacador = new Region(); // Espaçador para alinhar elementos
        HBox.setHgrow(espacador, Priority.ALWAYS); // Permite que o espaçador cresça

        // Adiciona todos os componentes à barra superior
        topBar.getChildren().addAll(logoBox, espacador, usuarioLabel);
        return topBar;
    }
}
