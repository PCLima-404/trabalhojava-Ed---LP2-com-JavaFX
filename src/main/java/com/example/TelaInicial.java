package com.example;

import java.time.format.DateTimeFormatter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    /** Lista personalizada para armazenar os eventos */
    public static Lista eventos = new Lista(5);
    public static Evento evento1 = new Evento(
    "TechNova Summit 2025",
    "Uma conferência internacional voltada para inovações em inteligência artificial, computação quântica e tecnologias emergentes. Reúne pesquisadores, startups e investidores do mundo todo.",
    "14/08/2025",
    "17/08/2025"
    );

    public static Evento evento2 = new Evento(
        "Code4Future Festival",
        "Um evento interativo com foco em jovens desenvolvedores, que inclui maratonas de programação (hackathons), oficinas de robótica, e palestras sobre desenvolvimento sustentável com tecnologia.",
        "22/09/2025",
        "24/09/2025"
    );

    public static Evento evento3 = new Evento(
        "CyberSec Week Brasil",
        "Semana temática dedicada à cibersegurança, com debates, workshops e simulações de ataques cibernéticos, voltada para profissionais da área de TI e segurança digital.",
        "03/11/2025",
        "07/11/2025"
    );

    public static Evento evento4 = new Evento(
        "ExpoRealidade+ 2025",
        "Feira nacional de tecnologias imersivas como realidade aumentada (AR), realidade virtual (VR) e realidade mista (MR), com demonstrações e experiências práticas.",
        "12/10/2025",
        "15/10/2025"
    );

    public static Evento evento5 = new Evento(
        "Women in Tech Rising",
        "Conferência voltada à inclusão e valorização das mulheres no setor de tecnologia, com painéis de lideranças femininas, programas de mentoria e feiras de oportunidades.",
        "05/12/2025",
        "06/12/2025"
    );

    /** Formato de data utilizado nas interfaces */
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Referência global á tela principal */
    private Stage primaryStage;


    /**
     * Inicializa a interface gráfica da aplicação.
     *
     * @param primaryStage Janela principal do app.
     */
    @Override
    public void start(Stage primaryStage) {

        // Adiciona eventos à lista de eventos
        eventos.limpar();
        eventos.anexar(evento1);
        eventos.anexar(evento2);
        eventos.anexar(evento3);
        eventos.anexar(evento4);
        eventos.anexar(evento5);

        // Top bar: logo, search, login
        this.primaryStage = primaryStage;
        HBox topBar = new HBox(20);
        Image logoImage = new Image(getClass().getResourceAsStream("/imagemLogo/E.png"));
        ImageView logoView = new ImageView(logoImage);
        logoView.setFitHeight(30);
        logoView.setPreserveRatio(true);

        Label appName = new Label("EventFlow");
        appName.setFont(Font.font("Arial", 24));
        appName.setTextFill(Color.web("#3b3b98"));

        HBox logoBox = new HBox(10, logoView, appName);
        logoBox.setAlignment(Pos.CENTER_LEFT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
        Scene loginScene = LoginScene.loginScene(primaryStage);
        primaryStage.setScene(loginScene);
        });

        Button registerButton = new Button("Cadastre-se");
        registerButton.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white;");
        registerButton.setOnAction(e -> {Scene cadastroScene = CadastroParticipanteScene.cadastroScene(primaryStage);
        primaryStage.setScene(cadastroScene);});
        topBar.getChildren().addAll(logoBox, spacer, registerButton, loginButton);
        
        // Título
        VBox titleBox = new VBox(10);
        Label title = new Label("Descubra e se inscreva nos");
        title.setFont(Font.font("Arial", 28));
        Label subtitle = new Label("melhores eventos da sua região");
        subtitle.setFont(Font.font("Arial", 28));
        subtitle.setTextFill(Color.web("#3b3b98"));
        titleBox.getChildren().addAll(title, subtitle);
        titleBox.setPadding(new Insets(10));

        // Grid de eventos
        GridPane eventGrid = new GridPane();
        eventGrid.setPadding(new Insets(20));
        eventGrid.setHgap(20);
        eventGrid.setVgap(20);

        String[] eventTitles = {
           evento1.getNome(),
           evento2.getNome(),
           evento3.getNome(),
           evento4.getNome(),
           evento5.getNome()
        };

        String[] eventDescriptions = {
            evento1.getDescricao(),
            evento2.getDescricao(),
            evento3.getDescricao(),
            evento4.getDescricao(),
            evento5.getDescricao()
        };

        for (int i = 0; i < eventTitles.length; i++) {
            VBox card = new VBox(10);
            card.setPadding(new Insets(10));
            card.setAlignment(Pos.CENTER);
            card.setStyle("-fx-background-color: #f0f0f0; -fx-border-radius: 8; -fx-background-radius: 8;");
            card.setPrefWidth(200);

            Image image = new Image(getClass().getResourceAsStream("/imagensApp/" + ((Evento) eventos.selecionar(i)).getId() + ".png")); 
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(180);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);

            Label eventLabel = new Label(eventTitles[i]);
            eventLabel.setWrapText(true);
            eventLabel.setFont(Font.font(14));
            eventLabel.setAlignment(Pos.CENTER);

            Button detailsButton = new Button("Saiba mais");
            detailsButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white;");
            
            // Descrição escondida inicialmente
            Label descriptionLabel = new Label(eventDescriptions[i]);
            descriptionLabel.setWrapText(true);
            descriptionLabel.setVisible(false);
            descriptionLabel.setManaged(false); // importante para o layout funcionar corretamente

            // Ao clicar no botão, mostra/oculta a descrição
            detailsButton.setOnAction(e -> {
                boolean invisivel = descriptionLabel.isVisible();
                descriptionLabel.setVisible(!invisivel);
                descriptionLabel.setManaged(!invisivel);
            });

            card.getChildren().addAll(imageView, eventLabel, detailsButton, descriptionLabel);
            eventGrid.add(card, i, 0);
        }


        VBox root = new VBox(10);
        root.getChildren().addAll(topBar, titleBox, eventGrid);

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("EventFlow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Scene criarTelaInicial(Stage stage) {
        TelaInicial tela = new TelaInicial();
        tela.primaryStage = stage;
        tela.start(stage); 
        return stage.getScene();
    }
}
