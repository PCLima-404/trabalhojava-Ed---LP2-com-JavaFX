package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Gerencia a cena do menu principal da aplicação.
 * Exibe atalhos para outras funcionalidades e uma lista de eventos disponíveis para inscrição.
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
public class MenuScene {

    /**
     * Cria e retorna a cena do menu principal.
     * Inclui a barra superior de navegação, atalhos para "Minhas Inscrições" e "Criar Palestras",
     * e uma lista rolável de eventos com opção de inscrição.
     *
     * @param stage O palco principal da aplicação.
     * @param nomeUsuario O nome do usuário logado.
     * @return A cena do menu configurada.
     */
    public static Scene menuScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);

        VBox centro = new VBox(20);
        centro.setPadding(new Insets(20));
        centro.setAlignment(Pos.TOP_CENTER);

        HBox atalhos = new HBox(20);
        atalhos.setAlignment(Pos.CENTER);

        // Cria os cartões de atalho para "Minhas Inscrições" e "Criar Palestras"
        atalhos.getChildren().addAll(
            createCard("Minhas\nInscrições", () -> {
                Scene inscricoes = MinhasInscricoesScene.minhasInscricoesScene(stage, nomeUsuario);
                stage.setScene(inscricoes);
            }),
            createCard("Criar\nPalestras", () -> {
                Scene criarPalestras = CriarPalestraScene.criarPalestraScene(stage, nomeUsuario);
                stage.setScene(criarPalestras);
            })
        );

        Label tituloEventos = new Label("Eventos e palestras");
        tituloEventos.setFont(Font.font("Arial", 18));
        tituloEventos.setTextFill(Color.web("#7a7adb"));

        // Cria e popula a lista de eventos
        VBox eventosBox = new VBox(15);
        for (int i = 0; i < TelaInicial.eventos.getTamanho(); i++) {
            Evento evento = (Evento) TelaInicial.eventos.selecionar(i);
            HBox eventoCard = createEventoCard(evento, "/imagensApp/" + ((Evento) TelaInicial.eventos.selecionar(i)).getId() + ".png", stage, nomeUsuario);
            eventosBox.getChildren().add(eventoCard);
        }

        ScrollPane scrollEventos = new ScrollPane(eventosBox);
        scrollEventos.setFitToWidth(true);
        scrollEventos.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollEventos.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollEventos.setStyle("-fx-background-color: transparent;");
        scrollEventos.setPrefHeight(300);

        centro.getChildren().addAll(atalhos, tituloEventos, scrollEventos);
        root.setCenter(centro);

        return new Scene(root, 900, 600);
    }

    /**
     * Cria um cartão de atalho clicável.
     * O cartão exibe um texto e executa uma ação ao ser clicado.
     *
     * @param texto O texto a ser exibido no cartão.
     * @param onClick A ação a ser executada ao clicar no cartão.
     * @return Um VBox configurado como um cartão de atalho.
     */
    private static VBox createCard(String texto, Runnable onClick) {
        VBox card = new VBox();
        card.setPrefSize(150, 100);
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 4);");
        card.setCursor(javafx.scene.Cursor.HAND);

        Label label = new Label(texto);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.web("#333"));
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);

        card.getChildren().add(label);
        card.setOnMouseClicked(e -> onClick.run());

        return card;
    }

    /**
     * Cria um cartão de evento para exibição na lista.
     * Exibe a imagem, título, data, descrição do evento e um botão de inscrição.
     * Ao clicar no botão, navega para a cena de inscrição do evento.
     *
     * @param evento O objeto Evento a ser exibido.
     * @param imagem O caminho da imagem do evento.
     * @param stage O palco principal da aplicação.
     * @param nomeUsuario O nome do usuário logado.
     * @return Um HBox representando o cartão do evento.
     */
    private static HBox createEventoCard(Evento evento, String imagem, Stage stage, String nomeUsuario) {
        HBox eventoBox = new HBox(15);
        eventoBox.setPadding(new Insets(10));
        eventoBox.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        eventoBox.setAlignment(Pos.CENTER_LEFT);

        Image img = new Image(MenuScene.class.getResourceAsStream(imagem));
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(80);
        imgView.setFitWidth(100);

        VBox infos = new VBox(5);
        Label tituloLabel = new Label(evento.getNome());
        tituloLabel.setTextFill(Color.web("#3b3b98"));
        tituloLabel.setFont(Font.font("Arial", 14));

        Label dataLabel = new Label(evento.getId()+ "\n" + evento.getDataInicio() + " à " + evento.getDataFim());
        dataLabel.setTextFill(Color.web("#7a7adb"));
        dataLabel.setFont(Font.font("Arial", 12));

        Label descLabel = new Label(evento.getDescricao());
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(400);

        infos.getChildren().addAll(tituloLabel, dataLabel, descLabel);

        Button inscrever = new Button("Inscrever");
        inscrever.setStyle("-fx-background-color: #7a7adb; -fx-text-fill: white; -fx-background-radius: 10;");
        inscrever.setOnAction(e -> {
            Scene inscricaoScene = InscricaoScene.inscricaoScene(stage, nomeUsuario, evento);
            stage.setScene(inscricaoScene);
        });

        eventoBox.getChildren().addAll(imgView, infos, inscrever);
        return eventoBox;
    }
}