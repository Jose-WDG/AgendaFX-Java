package br.com.treinaweb.main;

import br.com.treinaweb.agenda.entidades.Contato;
import br.com.treinaweb.agenda.repositorios.impl.ContatoRepositorio;
import br.com.treinaweb.agenda.repositorios.interfaces.AgendaRepositorio;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

    @FXML
    private TableView<Contato> tabelaContatos;
    @FXML
    private Button botaoInserir;
    @FXML
    private Button botaoAlterar;
    @FXML
    private Button botaoExcluir;
    @FXML
    private TextField txfNome;
    @FXML
    private TextField txfIdade;
    @FXML
    private TextField txfTell;
    @FXML
    private Button botaoSalvar;
    @FXML
    private Button botaoCancelar;

    private Boolean ehInserir;
    private Contato contatoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.tabelaContatos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        habilitarEdicaoAgenda(false);
        this.tabelaContatos.getSelectionModel().selectedItemProperty().addListener((observador, contatoAntigo, contatoNovo) -> {
            if (contatoNovo != null) {
                txfNome.setText(contatoNovo.getNome());
                txfIdade.setText(String.valueOf(contatoNovo.getIdade()));
                txfTell.setText(contatoNovo.getTell());
                this.contatoSelecionado = contatoNovo;
            }
        });
        CarregarTabelaContatos();
    }

    public void botaoInserir_Action() {
        this.ehInserir = true;
        this.txfNome.setText("");
        this.txfIdade.setText("");
        this.txfTell.setText("");
        habilitarEdicaoAgenda(true);
    }

    public void botaoAlterar_Action() {
        habilitarEdicaoAgenda(true);
        this.ehInserir = false;
        this.txfNome.setText(this.contatoSelecionado.getNome());
        this.txfIdade.setText(Integer.toString(this.contatoSelecionado.getIdade()));
        this.txfTell.setText(this.contatoSelecionado.getTell());
    }

    public void botaoExcluir_Action() {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmação");
        confirmacao.setHeaderText("Confirmação da exclusão do contato");
        confirmacao.setContentText("Tem certeza de que deseja excluir este contato?");
        Optional<ButtonType> resultadoConfirmacao = confirmacao.showAndWait();
        if (resultadoConfirmacao.isPresent() && resultadoConfirmacao.get() == ButtonType.OK) {
            AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
            repositorioContato.excluir(this.contatoSelecionado);
            CarregarTabelaContatos();
            this.tabelaContatos.getSelectionModel().selectFirst();
        }

    }

    public void botaoCancelar_Action() {
        habilitarEdicaoAgenda(false);
        this.tabelaContatos.getSelectionModel().selectFirst();
    }

    public void botaoSalvar_Action() {
        AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
        Contato contato = new Contato();
        contato.setNome(txfNome.getText());
        contato.setIdade(Integer.parseInt(txfIdade.getText()));
        contato.setTell(txfTell.getText());
        if (this.ehInserir) {
            repositorioContato.inserir(contato);
        } else {
            repositorioContato.atualizar(contato);
        }
        habilitarEdicaoAgenda(false);
        CarregarTabelaContatos();
        this.tabelaContatos.getSelectionModel().selectFirst();

    }

    public void CarregarTabelaContatos() {
        AgendaRepositorio<Contato> repositorioContato = new ContatoRepositorio();
        List<Contato> contatos = repositorioContato.selecionar();
        if (contatos.isEmpty()) {
            Contato contato = new Contato();
            contato.setNome("TreinaWeb");
            contato.setIdade(21);
            contato.setTell("951333960");
            contatos.add(contato);
        }
        ObservableList<Contato> contatoObservabkeList = FXCollections.observableArrayList(contatos);
        this.tabelaContatos.getItems().setAll(contatoObservabkeList);
    }

    public void habilitarEdicaoAgenda(Boolean edicaoEstaHabilitada) {
        this.txfNome.setDisable(!edicaoEstaHabilitada);
        this.txfIdade.setDisable(!edicaoEstaHabilitada);
        this.txfTell.setDisable(!edicaoEstaHabilitada);
        this.botaoCancelar.setDisable(!edicaoEstaHabilitada);
        this.botaoSalvar.setDisable(!edicaoEstaHabilitada);
        this.botaoInserir.setDisable(edicaoEstaHabilitada);
        this.botaoAlterar.setDisable(edicaoEstaHabilitada);
        this.botaoExcluir.setDisable(edicaoEstaHabilitada);
        this.tabelaContatos.setDisable(edicaoEstaHabilitada);
    }
}
