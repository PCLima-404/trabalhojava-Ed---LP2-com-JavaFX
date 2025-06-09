package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class EventosScene {

    public static Scene eventosScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Topo com menu de navegação
        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(20));

        Label titulo = new Label("Eventos");
        titulo.setFont(Font.font("Arial", 22));
        titulo.setTextFill(Color.web("#333"));

        GridPane tabelaEventos = new GridPane();
        tabelaEventos.setHgap(30);
        tabelaEventos.setVgap(15);
        tabelaEventos.setPadding(new Insets(10));

        // Cabeçalho da tabela
        Label colId = new Label("ID");
        Label colNome = new Label("Nome");
        Label colData = new Label("Data");


        colId.setStyle("-fx-font-weight: bold;");
        colNome.setStyle("-fx-font-weight: bold;");
        colData.setStyle("-fx-font-weight: bold;");

        VBox detalhesBox = new VBox(10);
        detalhesBox.setPadding(new Insets(10));
        detalhesBox.setStyle("-fx-background-color: #eeeeee; -fx-background-radius: 10;");

        tabelaEventos.addRow(0, colId, colNome, colData);

        int rowIndex = 1;
        Object[] objetos = TelaInicial.eventos.selecionarTodos();
        for (Object objeto : objetos ) {
            Evento evento = (Evento) objeto;
            Label id = new Label(evento.getId());
            Label nome = new Label(evento.getNome());
            Label data = new Label(evento.getDataInicio().toString()+" á "+evento.getDataFim());

            // Ícones de ação (substituir por imagens reais, se desejar)
            Button btnVer = new Button("Ver mais");
            btnVer.setStyle("-fx-background-color: #7a7adb; -fx-text-fill: white; -fx-background-radius: 10;");
            btnVer.setOnAction(e -> {
                detalhesBox.getChildren().clear(); // limpa antes de atualizar

                Label tituloDetalhes = new Label("Detalhes do Evento");
                tituloDetalhes.setFont(Font.font("Arial", 18));
                tituloDetalhes.setTextFill(Color.web("#3b3b98"));

                Label desc = new Label(evento.getDescricao());
                desc.setWrapText(true);
                Label dataCompleta = new Label("De " + evento.getDataInicio().toString() + " até " + evento.getDataFim().toString());

                detalhesBox.getChildren().addAll(tituloDetalhes, desc, dataCompleta);
            });

            HBox botoes = new HBox(10, btnVer);
            botoes.setAlignment(Pos.CENTER_LEFT);

            tabelaEventos.addRow(rowIndex++, id, nome, data, botoes);
        }

        mainContent.getChildren().addAll(titulo, tabelaEventos, detalhesBox);
        root.setCenter(mainContent);

        return new Scene(root, 950, 600);
    }
}
