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
 * Gerencia a cena de inscrição em eventos e palestras.
 * Exibe detalhes do evento e permite a inscrição em suas palestras.
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
public class InscricaoScene {

    /**
     * Cria a cena de inscrição para um evento.
     * Inclui cabeçalho, detalhes do evento e uma lista de palestras com botões de inscrição.
     *
     * @param stage O palco principal da aplicação.
     * @param nomeUsuario O nome do usuário logado.
     * @param evento O evento a ser exibido.
     * @return A cena de inscrição configurada.
     */
    public static Scene inscricaoScene(Stage stage, String nomeUsuario, Evento evento) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);

        Label titulo = new Label("Inscrição");
        titulo.setFont(Font.font("Arial", 22));
        titulo.setTextFill(Color.web("#333"));

        HBox cardEvento = new HBox(15);
        cardEvento.setPadding(new Insets(15));
        cardEvento.setStyle("-fx-background-color: white; -fx-background-radius: 15;");
        cardEvento.setAlignment(Pos.CENTER_LEFT);

        Image imagem = new Image(InscricaoScene.class.getResourceAsStream("/imagensApp/" + evento.getId() + ".png"));
        ImageView img = new ImageView(imagem);
        img.setFitHeight(100);
        img.setFitWidth(120);

        VBox infos = new VBox(5);
        Label tituloEvento = new Label(evento.getNome());
        tituloEvento.setTextFill(Color.web("#3b3b98"));
        tituloEvento.setFont(Font.font("Arial", 16));

        Label descEvento = new Label(evento.getDescricao());
        descEvento.setWrapText(true);
        descEvento.setMaxWidth(500);

        Label dataEvento = new Label(evento.getDataInicio().toString() + " á " + evento.getDataFim());
        dataEvento.setTextFill(Color.GRAY);
        dataEvento.setFont(Font.font("Arial", 12));

        infos.getChildren().addAll(tituloEvento, descEvento, dataEvento);
        cardEvento.getChildren().addAll(img, infos);

        Label subtitulo = new Label("Confira as palestras do evento");
        subtitulo.setFont(Font.font("Arial", 16));
        subtitulo.setTextFill(Color.web("#7a7adb"));

        VBox listaPalestras = new VBox(10);
        for (Palestra palestra : evento.listarPalestras()) {
            listaPalestras.getChildren().add(
                createLinhaPalestra(
                    palestra,
                    palestra.getTitulo(),
                    palestra.getPalestrante(),
                    palestra.getHorarioInicio().toString() + " ás " + palestra.getHorarioFinal().toString()
                )
            );
        }
        ScrollPane scrollPalestras = new ScrollPane(listaPalestras);
        scrollPalestras.setFitToWidth(true);
        scrollPalestras.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPalestras.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPalestras.setPrefHeight(300);
        scrollPalestras.setStyle("-fx-background-color: transparent;");

        VBox centerBox = new VBox(15);
        centerBox.getChildren().addAll(titulo, cardEvento, subtitulo, scrollPalestras);
        root.setCenter(centerBox);
        return new Scene(root, 900, 600);
    }

    /**
     * Cria uma linha de exibição para uma palestra.
     * Inclui nome, palestrante, horário e um botão de inscrição.
     * A inscrição verifica a disponibilidade e atualiza o estado do botão.
     *
     * @param palestra O objeto Palestra.
     * @param nome O título da palestra.
     * @param palestrante O nome do palestrante.
     * @param horario O horário de início e fim.
     * @return Um HBox representando a linha da palestra.
     */
    private static HBox createLinhaPalestra(Palestra palestra, String nome, String palestrante, String horario) {
        HBox linha = new HBox(30);
        linha.setPadding(new Insets(10));
        linha.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        linha.setAlignment(Pos.CENTER_LEFT);

        Label nomeLabel = new Label(nome);
        Label palestranteLabel = new Label(palestrante);
        Label horarioLabel = new Label(horario);

        nomeLabel.setPrefWidth(200);
        palestranteLabel.setPrefWidth(200);
        horarioLabel.setPrefWidth(150);

        Button inscrever = new Button("Inscrever");
        inscrever.setStyle("-fx-background-color: #7a7adb; -fx-text-fill: white; -fx-background-radius: 10;");
        inscrever.setOnAction(e -> {
            if(palestra.verificarDisponibilidade()){
                palestra.inscreverParticipante(CadastroParticipanteScene.participanteCadastrado);
                CadastroParticipanteScene.participanteCadastrado.inscreverEmPalestra(palestra);
                System.out.println(CadastroParticipanteScene.participanteCadastrado.getNome() + " foi cadastrado em " + palestra.getTitulo());
                
                inscrever.setText("Inscrito");
                inscrever.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");
                inscrever.setDisable(true); 
            } else {
                palestra.adicionarFilaEspera(CadastroParticipanteScene.participanteCadastrado);
                inscrever.setText("Você está na lista de espera");
                inscrever.setStyle("-fx-background-color:rgb(8, 22, 104); -fx-text-fill: white; -fx-background-radius: 10;");
                inscrever.setDisable(true); 
            }
        });

        linha.getChildren().addAll(nomeLabel, palestranteLabel, horarioLabel, inscrever);
        return linha;
    }
}