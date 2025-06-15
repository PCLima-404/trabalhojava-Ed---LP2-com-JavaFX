package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Gerencia a cena de visualização e gerenciamento de palestras.
 * Exibe uma lista de todas as palestras cadastradas, com opções para ver detalhes e excluir.
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
public class PalestrasScene {

    /**
     * Cria e retorna a cena de palestras.
     * Inclui a barra superior de navegação, um título, uma tabela de palestras
     * com seus detalhes (ID, nome, data, evento associado), e botões de ação
     * para visualizar mais informações ou excluir a palestra.
     *
     * @param stage O palco principal da aplicação.
     * @param nomeUsuario O nome do usuário logado.
     * @return A cena de palestras configurada.
     */
    public static Scene palestrasScene(Stage stage, String nomeUsuario) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        HBox topMenu = TopoComMenu.createTopBar(stage, nomeUsuario);
        root.setTop(topMenu);

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));

        VBox detalhesBox = new VBox(10);
        detalhesBox.setPadding(new Insets(10));
        detalhesBox.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 10;");

        Label titulo = new Label("Palestras");
        titulo.setFont(Font.font("Arial", 22));
        titulo.setTextFill(Color.web("#333"));

        GridPane tabela = new GridPane();
        tabela.setHgap(30);
        tabela.setVgap(15);
        tabela.setPadding(new Insets(10));

        // Cabeçalhos da tabela
        Label colId = new Label("ID");
        Label colNome = new Label("Nome");
        Label colData = new Label("Data");
        Label colEvento = new Label("Evento");

        colId.setStyle("-fx-font-weight: bold;");
        colNome.setStyle("-fx-font-weight: bold;");
        colData.setStyle("-fx-font-weight: bold;");
        colEvento.setStyle("-fx-font-weight: bold;");

        tabela.addRow(0, colId, colNome, colData, colEvento);

        int indexRow = 1;
        // Percorre todos os eventos e suas palestras para popular a tabela
        Object[] objetos = TelaInicial.eventos.selecionarTodos();
        for (Object objeto : objetos) {
            Evento evento = (Evento)objeto;
            for(Palestra palestra : evento.listarPalestras()){
                Label id = new Label(palestra.getId());
                Label nome = new Label(palestra.getTitulo());
                Label data = new Label(palestra.getData().toString());
                Label eventoNome = new Label(evento.getNome());

                // Botão "Ver mais" para exibir detalhes da palestra
                Button btnVer = new Button("Ver mais");
                btnVer.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white;");
                btnVer.setOnAction(e -> {
                    detalhesBox.getChildren().clear(); // Limpa detalhes anteriores
                    
                    Label tituloDetalhes = new Label("Detalhes da Palestra");
                    tituloDetalhes.setFont(Font.font("Arial", 18));
                    tituloDetalhes.setTextFill(Color.web("#3b3b98"));

                    Label descricao = new Label(palestra.getDescricao());
                    descricao.setWrapText(true);

                    Label horario = new Label("Horário: " + palestra.getHorarioInicio() + " às " + palestra.getHorarioFinal());
                    Label local = new Label("Local: " + palestra.getLocal());
                    
                    // Constrói a string de participantes
                    String participantes = "";
                    for(Participante participante : palestra.listarParticipantes()){
                        participantes += "ID: " + participante.getId() + "       Nome: " + participante.getNome() + "\n";
                    }
                    Label participantesL = new Label("Participantes:\n" + participantes);

                    detalhesBox.getChildren().addAll(tituloDetalhes, descricao, horario, local, participantesL);
                });

                // Botão "Excluir" palestra
                Button btnExcluir = new Button("Excluir");
                btnExcluir.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white;");
                btnExcluir.setOnAction(e -> {
                    CadastroParticipanteScene.participanteCadastrado.cancelarInscricao(palestra); // Remove do participante
                    palestra.cancelarInscricao(CadastroParticipanteScene.participanteCadastrado.getId()); // Remove da palestra
                    evento.removerPalestra(palestra.getId()); // Remove do evento
                    
                    // Recarrega a cena para refletir a exclusão
                    Scene palestraScene = PalestrasScene.palestrasScene(stage, nomeUsuario);
                    stage.setScene(palestraScene);
                });

                HBox botoes = new HBox(10, btnVer, btnExcluir);
                botoes.setAlignment(Pos.CENTER_LEFT);

                tabela.addRow(indexRow++, id, nome, data, eventoNome, botoes);
            }
        }

        ScrollPane scroll = new ScrollPane(tabela);
        scroll.setFitToWidth(true);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("-fx-background-color: transparent;");

        content.getChildren().addAll(titulo, scroll, detalhesBox);
        root.setCenter(content);

        return new Scene(root, 1000, 600);
    }
}