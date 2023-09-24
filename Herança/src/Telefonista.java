public class Telefonista extends Funcionario {
    private String codigoEstacao;

    // Construtor com uso de herança
    public Telefonista(String nome, double salario, String codigoEstacao) {
        super(nome, salario);
        this.codigoEstacao = codigoEstacao;
    }

    // Getter
    public String getCodigoEstacao() {
        return codigoEstacao;
    }

    // Setter
    public void setCodigoEstacao(String codigoEstacao) {
        this.codigoEstacao = codigoEstacao;
    }
}
