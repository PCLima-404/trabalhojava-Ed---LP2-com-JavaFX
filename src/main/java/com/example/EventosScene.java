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
/**
 * Classe responsável por criar e gerenciar a cena de exibição de eventos.
 * Esta cena apresenta uma lista de eventos cadastrados em formato de tabela,
 * permitindo ao usuário visualizar detalhes de cada evento.
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
public class EventosScene {
    /**
     * Cria e retorna a cena de eventos da aplicação.
     * Esta cena exibe uma lista de eventos em formato de tabela,
     * com a opção de ver detalhes de cada evento ao clicar no botão "Ver mais".
     * Inclui um topo com menu de navegação.
     *
     * @param stage O Stage principal da aplicação onde esta cena será exibida.
     * @param nomeUsuario O nome do usuário logado, exibido na barra superior.
     * @return Uma instância de Scene configurada para exibir a lista de eventos.
     */
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
