package com.example;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MinhasInscricoesScene {

    public static Scene minhasInscricoesScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Topo com menu
        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario); // Reaproveitando barra superior
        root.setTop(topMenu);

        VBox conteudo = new VBox(20);
        conteudo.setPadding(new Insets(20));

        // Título da página
        Label titulo = new Label("Minhas inscrições");
        titulo.setFont(Font.font("Arial", 24));
        titulo.setTextFill(Color.web("#333"));
        conteudo.getChildren().add(titulo);

        // Inscritos
        Label labelInscrito = new Label("Inscrito");
        labelInscrito.setStyle("-fx-text-fill: #3B4EFF; -fx-font-size: 14px;");
        conteudo.getChildren().add(labelInscrito);
        for(Palestra palestra : CadastroParticipanteScene.participanteCadastrado.listarPalestrasInscritas()){
            conteudo.getChildren().add(criarLinhaInscricao(palestra, stage, nomeUsuario));
        }

        ScrollPane scrollPane = new ScrollPane(conteudo);
        scrollPane.setFitToWidth(true); // Faz o conteúdo expandir horizontalmente junto com o scroll
        scrollPane.setStyle("-fx-background-color: transparent;"); // Remove cor de fundo padrão do ScrollPane
        root.setCenter(scrollPane);

        return new Scene(root, 900, 600);
    }

    private static HBox criarLinhaInscricao(Palestra palestra, Stage stage, String nomeUsuario) {
        HBox linha = new HBox(30);
        linha.setPadding(new Insets(10));
        linha.setStyle("-fx-background-color: #fff; -fx-background-radius: 10px;");
        linha.setPrefHeight(50);

        Label tituloLabel = new Label(palestra.getTitulo());
        Label palestranteLabel = new Label(palestra.getPalestrante());
        Label horarioLabel = new Label(palestra.getHorarioInicio() + " ás " + palestra.getHorarioFinal());
        Label localLabel = new Label(palestra.getLocal());
        Label dataLabel = new Label(palestra.getData().toString());

        Region espacador = new Region();
        HBox.setHgrow(espacador, Priority.ALWAYS);

        Button cancelarBtn = new Button("Cancelar");
        cancelarBtn.setStyle("-fx-background-color: #C92C4B; -fx-text-fill: white; -fx-background-radius: 20px;");
        cancelarBtn.setOnAction(e -> {
            palestra.cancelarInscricao(CadastroParticipanteScene.participanteCadastrado.getId());
            CadastroParticipanteScene.participanteCadastrado.cancelarInscricao(palestra);
            System.out.println("Cancelado: " + tituloLabel.getText());
            Scene minhasIncricoes = MinhasInscricoesScene.minhasInscricoesScene(stage, nomeUsuario);
            stage.setScene(minhasIncricoes);
        });

        linha.getChildren().addAll(tituloLabel, palestranteLabel, horarioLabel, localLabel, dataLabel, espacador, cancelarBtn);
        return linha;
    }
}

