public class Secretaria extends Funcionario {
    private int numeroRamal;

    // Construtor com uso de herança
    public Secretaria(String nome, double salario, int numeroRamal) {
        super(nome, salario);
        this.numeroRamal = numeroRamal;
    }

    // Getter
    public int getNumeroRamal() {
        return numeroRamal;
    }

    // Setter
    public void setNumeroRamal(int numeroRamal) {
        this.numeroRamal = numeroRamal;
    }
}
