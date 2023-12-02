public class Carta {
    private Jogador jogador;
    private int numero;
    private int valor;

    public Carta() {
        this.numero = 0;
        this.valor = 0;
    }

    public Carta(int numero) {
        this.numero = numero;
        this.valor = 1;

        // Se o número da carta terminar em 5, adicionamos 1 ponto.
        if (numero % 10 == 5) {
            this.valor += 1;
        }

        // Se o número da carta for divisível por 10, adicionamos 2 pontos.
        if (numero % 10 == 0) {
            this.valor += 2;
        }

        // Se a carta possuir dois dígitos iguais, adicionamos 4 pontos.
        if (numero / 10 == numero % 10) {
            this.valor += 4;
        }
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getValor() {
        return this.valor;
    }
}
