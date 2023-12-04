package Componentes;

public class Carta implements Comparable<Carta> {
    public int numero;
    public int valor;
    public Jogador jogador;

    public Carta() {
        this.numero = -1;
        this.valor = 0;
    }

    public Carta(int numero) {
        this.numero = numero;
        this.valor = calcularValor();

        // Se a carta possuir dois dígitos iguais, adicionamos 4 pontos.
        if (temDigitosIguais() || numero == 101) {
            this.valor += 4;
        }
    }

    private int calcularValor() {
        int valor = 1;

        // Se o número da carta terminar em 5, adicionamos 1 ponto.
        if (numero % 10 == 5) {
            valor += 1;
        }

        // Se o número da carta for divisível por 10, adicionamos 2 pontos.
        if (numero % 10 == 0) {
            valor += 2;
        }

        return valor;
    }

    private boolean temDigitosIguais() {
        int primeiroDigito = numero / 10; // 33 / 10 = 3
        int segundoDigito = numero % 10; // 33 % 10 = 3
        return primeiroDigito == segundoDigito;
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
