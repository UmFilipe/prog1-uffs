import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Calculadora {

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private Button btnSoma;

    @FXML
    private Button btnSubtracao;

    @FXML
    private Button btnMultiplicacao;

    @FXML
    private Button btnDivisao;

    @FXML
    private TextField resultado;

    private Soma soma = new Soma();
    private Subtracao subtracao = new Subtracao();
    private Multiplicacao multiplicacao = new Multiplicacao();
    private Divisao divisao = new Divisao();

    // Verifica se o X ou Y é um número
    private boolean verificarNumero(String valor) {
        try {
            Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Verifica se X ou Y estão vazios (trim para remover espaços em branco)
    private boolean verificarVazio(String valor) {
        return valor.trim().isEmpty();
    }

    // Obtém o valor de X ou Y passando o mesmo para o tipo double
    private double obterValor(TextField textField) {
        String valor = textField.getText();
        if (verificarNumero(valor)) {
            return Double.parseDouble(valor);
        } else {
            return 0;
        }
    }

    // Verifica se os campos estão vazios ou se não são números
    private boolean validarCampos() {
        if (verificarVazio(x.getText()) || verificarVazio(y.getText())) {
            resultado.setText("Por favor, preencha ambos os campos.");
            return false;
        }
        if (!verificarNumero(x.getText()) || !verificarNumero(y.getText())) {
            resultado.setText("Por favor, insira números válidos.");
            return false;
        }
        return true;
    }

    // Realiza a soma após a validação dos campos
    @FXML
    void somar(ActionEvent event) {
        if (validarCampos()) {
            resultado.setText(String.valueOf(soma.executar(obterValor(x), obterValor(y))));
        }
    }

    // Realiza a subtração após a validação dos campos
    @FXML
    void subtrair(ActionEvent event) {
        if (validarCampos()) {
            resultado.setText(String.valueOf(subtracao.executar(obterValor(x), obterValor(y))));
        }
    }

    // Realiza a multiplicação após a validação dos campos
    @FXML
    void multiplicar(ActionEvent event) {
        if (validarCampos()) {
            resultado.setText(String.valueOf(multiplicacao.executar(obterValor(x), obterValor(y))));
        }
    }

    // Valida os campos, verifica se Y é 0 e caso não seja, realiza a divisão
    @FXML
    void dividir(ActionEvent event) {
        if (validarCampos()) {
            if (obterValor(y) == 0) {
                resultado.setText("Não é possível dividir por 0");
                return;
            }
            resultado.setText(String.valueOf(divisao.executar(obterValor(x), obterValor(y))));
        }
    }
}
