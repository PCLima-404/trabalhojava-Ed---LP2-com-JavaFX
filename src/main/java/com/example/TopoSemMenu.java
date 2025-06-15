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

public class TopoSemMenu {
    public static HBox createTopBar(Stage stage, String nomeUsuario) {
        HBox topBar = new HBox(50);
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: #ffffff;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Logo e nome
        Image logoImg = new Image(TopoSemMenu.class.getResourceAsStream("/imagemLogo/E.png"));
        ImageView logo = new ImageView(logoImg); 
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);

        Label titulo = new Label("EventFlow");
        titulo.setFont(Font.font("Arial", 20));
        titulo.setTextFill(Color.web("#3B4EFF"));

        HBox logoBox = new HBox(10, logo, titulo);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        // Usuário
        Label usuarioLabel = new Label("Olá, " + nomeUsuario);
        usuarioLabel.setFont(Font.font(14));

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        topBar.getChildren().addAll(logoBox, espacador, usuarioLabel);
        return topBar;
    }
}
