public class Carta implements Comparable<Carta> {
    public Jogador jogador;
    public int numero;
    public int valor;

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
        if (numero / 10 == numero % 10) { // ex: 33/10 = 3, 33%10 = 3
            this.valor += 4;
        }
    }

    public String printCard(){
        return this.numero + " - " + this.valor;
    }

    @Override
    public int compareTo(Carta c) {
        if (this.numero < c.numero) {
            return -1;
        } else {
            return 1;
        }
    }
    
}
