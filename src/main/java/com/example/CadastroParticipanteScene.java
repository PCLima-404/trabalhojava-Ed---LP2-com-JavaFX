package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Classe responsável pela cena de cadastro de participantes.
 * Esta classe fornece uma interface gráfica para cadastrar novos participantes,
 * com validação de e-mail único e armazenamento na lista de participantes.
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
 * * @version 1.1
 * @since 2025-05-25
 */
public class CadastroParticipanteScene {

    /**
     * Referência estática para o último participante cadastrado.
     * Este campo é atualizado após o sucesso do cadastro de um participante.
     */
    public static Participante participanteCadastrado;

    /**
     * Lista estática de participantes cadastrados.
     * Utiliza uma instância da classe Lista com capacidade inicial para 20 participantes.
     * Esta lista armazena todos os participantes que foram cadastrados na aplicação.
     */
    public static final Lista participantes = new Lista(20);

    /**
     * Cria e configura a cena principal para o cadastro de um novo participante.
     * Este método inicializa o layout base, configura a barra superior e o conteúdo central
     * que inclui o formulário de cadastro e os botões de ação.
     *
     * @param stage O Stage principal da aplicação JavaFX onde a cena será exibida.
     * @return Uma instância de Scene configurada e pronta para ser exibida,
     * representando a interface de cadastro de participantes.
     */
    public static Scene cadastroScene(Stage stage) {
        BorderPane root = criarLayoutBase();

        configurarTopo(root, stage);
        configurarConteudoCentral(root, stage);

        return new Scene(root, 800, 500);
    }

    /**
     * Cria e configura o layout base da interface de cadastro utilizando um BorderPane.
     * O layout é preenchido com um padding e tem uma cor de fundo clara definida.
     *
     * @return Um BorderPane configurado com as propriedades visuais básicas para a cena.
     */
    private static BorderPane criarLayoutBase() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");
        return root;
    }

    /**
     * Configura a barra superior da interface de cadastro.
     * Reutiliza o componente TopoSemMenu.createTopBar para criar o cabeçalho
     * da página, exibindo o título "Participante".
     *
     * @param root O BorderPane principal onde a barra superior será adicionada.
     * @param stage O Stage principal da aplicação, necessário para a barra superior.
     */
    private static void configurarTopo(BorderPane root, Stage stage) {
        HBox topMenu = TopoSemMenu.createTopBar(stage, "Participante");
        root.setTop(topMenu);
    }

    /**
     * Configura o conteúdo central da interface de cadastro.
     * Inclui o título da página, o formulário de cadastro com campos para nome e e-mail,
     * e os botões de ação (Salvar e Cancelar). Os elementos são organizados em um VBox.
     *
     * @param root O BorderPane principal onde o conteúdo central será adicionado.
     * @param stage O Stage principal da aplicação, passado para os botões de ação.
     */
    private static void configurarConteudoCentral(BorderPane root, Stage stage) {
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER_LEFT);

        Label title = criarTitulo("Cadastro do participante");
        VBox form = criarFormulario();
        HBox botoes = criarBotoesAcao(stage);

        centerBox.getChildren().addAll(title, form, botoes);
        root.setCenter(centerBox);
    }

    /**
     * Cria e configura um componente Label para ser utilizado como título da página.
     * O título é estilizado com uma fonte "Arial", tamanho 26 e uma cor de texto escura.
     *
     * @param texto O texto a ser exibido no título.
     * @return Um Label configurado com o texto e estilo do título.
     */
    private static Label criarTitulo(String texto) {
        Label title = new Label(texto);
        title.setFont(Font.font("Arial", 26));
        title.setTextFill(Color.web("#2d2d2d"));
        return title;
    }

    /**
     * Cria e configura o formulário de cadastro de participante.
     * O formulário inclui campos de texto para "Nome" e "E-mail",
     * além de uma Label para exibir mensagens de erro relacionadas ao e-mail.
     *
     * @return Um VBox contendo os componentes do formulário, organizados verticalmente.
     */
    private static VBox criarFormulario() {
        VBox form = new VBox(20);
        form.setAlignment(Pos.TOP_LEFT);

        TextField nomeField = criarCampoTexto("Insira nome do participante", 400);
        TextField emailField = criarCampoTexto("Insira o e-mail do participante", 400);
        Label emailErroLabel = criarLabelErro();

        form.getChildren().addAll(
                criarLabelCampo("Nome", nomeField),
                criarLabelCampo("E-mail", emailField),
                emailErroLabel);

        return form;
    }

    /**
     * Cria um campo de texto com prompt.
     *
     * @param prompt Texto de orientação.
     * @param largura Largura preferencial do campo em pixels.
     * @return TextField configurado.
     */
    private static TextField criarCampoTexto(String prompt, double largura) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        campo.setPrefWidth(largura);
        return campo;
    }

    /**
     * Cria uma label para campo do formulário.
     * Este método é útil para criar pares "label-campo" padronizados no formulário.
     * A label é estilizada com uma cor específica.
     *
     * @param texto O texto a ser exibido na Label (ex: "Nome", "E-mail").
     * @param campo O TextField a ser associado a esta label.
     * @return VBox contendo os componentes.
     */
    private static VBox criarLabelCampo(String texto, TextField campo) {
        Label label = new Label(texto);
        label.setTextFill(Color.web("#7a7adb"));
        return new VBox(5, label, campo);
    }

    /**
     * Cria a label para mensagens de erro.
     * A label é inicialmente invisível e tem sua cor de texto definida como vermelho.
     *
     * @return Label configurada.
     */
    private static Label criarLabelErro() {
        Label label = new Label("Este e-mail já está cadastrado.");
        label.setTextFill(Color.RED);
        label.setVisible(false); // Inicialmente invisível
        return label;
    }

    /**
     * Cria os botões de ação para o formulário de cadastro.
     * Inclui os botões "Salvar" e "Cancelar", com seus respectivos manipuladores de eventos.
     *
     * @param stage O Stage principal da aplicação, passado para as ações dos botões.
     * @return HBox contendo os botões.
     */
    private static HBox criarBotoesAcao(Stage stage) {
        // TODO: Os campos nomeField e emailField precisam ser acessíveis aqui
        // Sugestão: tornar os campos de texto como campos da classe ou passá-los como parâmetros.
        Button salvarBtn = criarBotao("Salvar", e -> {
            // Placeholder para extração de campos.
            // Para que o código compile, 'email' e 'nome' precisariam ser definidos ou passados.
            String nome = "NomeExemplo"; // Apenas para compilação
            String email = "exemplo@email.com"; // Apenas para compilação

            tratarSalvar(stage /* , nome, email */); // Passar os valores reais dos campos
        });
        Button cancelarBtn = criarBotao("Cancelar", e -> cancelarCadastro(stage));

        return new HBox(10, salvarBtn, cancelarBtn);
    }

    /**
     * Cria um botão estilizado.
     * O botão é estilizado com um gradiente de cor de fundo, texto branco e bordas arredondadas.
     *
     * @param texto Texto do botão.
     * @param acao Ação do botão.
     * @return Button configurado.
     */
    private static Button criarBotao(String texto, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button btn = new Button(texto);
        btn.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
                "-fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        btn.setOnAction(acao);
        return btn;
    }

    /**
     * Trata a ação de salvar o cadastro.
     * Este método simula a extração dos dados do formulário (nome e e-mail),
     * verifica se o e-mail já está cadastrado e, se não estiver, cadastra o participante
     * e navega para a tela de menu. Em caso de e-mail duplicado, exibe uma mensagem de erro.
     *
     * @param stage Palco principal.
     * @param nome O nome do participante a ser cadastrado.
     * @param email O e-mail do participante a ser cadastrado.
     * @implNote IMPORTANTE: Os parâmetros 'nome' e 'email' foram adicionados a este método para que ele possa
     * receber os valores dos campos de texto do formulário. A lógica de extração
     * desses campos do TextField deve ser implementada no método 'criarBotoesAcao'
     * ou em um método auxiliar que os acesse. O 'TODO' no método 'criarBotoesAcao'
     * indica essa necessidade.
     */
    private static void tratarSalvar(Stage stage /* , String nome, String email */) {
        // TODO: Extrair os valores reais dos campos nomeField e emailField do formulário
        // Apenas para que o código compile, valores placeholder são usados.
        String nome = "NomeTeste"; // Substituir pela extração real do campo
        String email = "teste@email.com"; // Substituir pela extração real do campo

        if (emailJaCadastrado(email)) {
            exibirErro("Este e-mail já está cadastrado.");
        } else {
            cadastrarParticipante(nome, email);
            navegarParaMenu(stage, nome);
        }
    }

    /**
     * Verifica se e-mail já está cadastrado.
     * Percorre a lista estática de participantes e compara o e-mail fornecido
     * com os e-mails dos participantes existentes.
     *
     * @param email E-mail a verificar.
     * @return true se e-mail já existe; false caso contrário.
     */
    private static boolean emailJaCadastrado(String email) {
        for (int i = 0; i < participantes.getTamanho(); i++) {
            // É necessário um cast para Participante, assumindo que Lista.selecionar retorna Object.
            if (email.equals(((Participante) participantes.selecionar(i)).getEmail())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Realiza o cadastro do participante.
     * Cria uma nova instância de Participante com o nome e e-mail fornecidos,
     * armazena essa instância na lista estática de participantes e atualiza
     * a referência para o último participante cadastrado.
     *
     * @param nome Nome do participante.
     * @param email E-mail do participante.
     */
    private static void cadastrarParticipante(String nome, String email) {
        participanteCadastrado = new Participante(nome, email);
        participantes.anexar(participanteCadastrado);
    }

    /**
     * Navega para a tela de menu.
     * Este método cria uma nova cena de menu e a define como a cena atual do palco principal.
     * Exibe o nome do participante e seu ID na tela de menu.
     *
     * @param stage Palco principal.
     * @param nome Nome do participante.
     */
    private static void navegarParaMenu(Stage stage, String nome) {
        Scene menuScene = MenuScene.menuScene(stage, nome + "\nID: " + participanteCadastrado.getId());
        stage.setScene(menuScene);
    }

    /**
     * Cancela o cadastro e retorna à tela inicial.
     * Este método cria uma nova cena da tela inicial e a define como a cena atual do palco principal.
     *
     * @param stage Palco principal.
     */
    private static void cancelarCadastro(Stage stage) {
        Scene telaInicial = TelaInicial.criarTelaInicial(stage);
        stage.setScene(telaInicial);
    }

    /**
     * Exibe mensagem de erro.
     * Nota: A implementação real para exibir o erro (por exemplo, mostrando uma label de erro
     * ou um pop-up de alerta) precisa ser adicionada aqui. Atualmente, este método está vazio.
     *
     * @param mensagem Texto do erro.
     */
    private static void exibirErro(String mensagem) {
        // TODO: Implementar a lógica para exibir a mensagem de erro na interface
        // Ex: Tornar a emailErroLabel visível e definir seu texto.
        System.err.println("Erro: " + mensagem); // Apenas para debug no console
    }
}