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
 * Cria a barra superior de navegação com logo, menu e nome do usuário.
 * Este componente é reutilizável em diversas cenas da aplicação.
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
public class TopoComMenu {

    /**
     * Cria e retorna a barra superior da aplicação com funcionalidades de menu.
     * Inclui o logo, o nome da aplicação, links de navegação (Eventos, Palestras, Sair, Menu)
     * e o nome do usuário logado.
     *
     * @param stage O palco principal da aplicação para navegação entre cenas.
     * @param nomeUsuario O nome do usuário a ser exibido.
     * @return Um HBox configurado como a barra superior.
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

        // Seção de Navegação (links de menu)
        HBox nav = new HBox(30);
        nav.setAlignment(Pos.CENTER);

        Label eventos = new Label("Eventos");
        Label palestras = new Label("Palestras");
        Label sair = new Label("Sair");
        Label menu = new Label("Menu");

        // Estilos para os itens de menu (normal e hover)
        String estiloPadrao = "-fx-cursor: hand; -fx-text-fill: #333;";
        String estiloHover = "-fx-cursor: hand; -fx-text-fill: #3B4EFF;";

        eventos.setFont(Font.font(14));
        palestras.setFont(Font.font(14));
        sair.setFont(Font.font(14));
        menu.setFont(Font.font(14));

        eventos.setStyle(estiloPadrao);
        palestras.setStyle(estiloPadrao);
        sair.setStyle(estiloPadrao);
        menu.setStyle(estiloPadrao);

        // Efeitos visuais ao passar o mouse (hover)
        eventos.setOnMouseEntered(e -> eventos.setStyle(estiloHover));
        eventos.setOnMouseExited(e -> eventos.setStyle(estiloPadrao));

        palestras.setOnMouseEntered(e -> palestras.setStyle(estiloHover));
        palestras.setOnMouseExited(e -> palestras.setStyle(estiloPadrao));

        sair.setOnMouseEntered(e -> sair.setStyle(estiloHover));
        sair.setOnMouseExited(e -> sair.setStyle(estiloPadrao));

        menu.setOnMouseEntered(e -> menu.setStyle(estiloHover));
        menu.setOnMouseExited(e -> menu.setStyle(estiloPadrao));

        // Ações de clique para cada item do menu
        eventos.setOnMouseClicked(e -> {
            stage.setScene(EventosScene.eventosScene(stage, nomeUsuario));
        });

        palestras.setOnMouseClicked(e -> {
            stage.setScene(PalestrasScene.palestrasScene(stage, nomeUsuario));
        });

        sair.setOnMouseClicked(e -> {
            stage.setScene(TelaInicial.criarTelaInicial(stage));
        });

        menu.setOnMouseClicked(e -> {
            stage.setScene(MenuScene.menuScene(stage, nomeUsuario));
        });

        nav.getChildren().addAll(eventos, palestras, sair, menu);

        // Exibição do nome do usuário
        Label usuarioLabel = new Label("Olá, " + nomeUsuario);
        usuarioLabel.setFont(Font.font(14));

        Region espacador = new Region(); // Espaçador para alinhar elementos
        HBox.setHgrow(espacador, Priority.ALWAYS); // Permite que o espaçador cresça

        // Adiciona todos os componentes à barra superior
        topBar.getChildren().addAll(logoBox, nav, espacador, usuarioLabel);
        return topBar;
    }
}