import java.util.ArrayList;
import java.util.Stack;

public class Jogador implements Comparable<Jogador> {
    private ArrayList<Carta> mao = new ArrayList<>();
    private ArrayList<Carta> cartasColetadas = new ArrayList<>();
    private String nome;
    private int totalPontos = 0;

    public void comprarCartas(Stack<Carta> baralho, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Carta carta = baralho.pop();
            carta.setJogador(this);
            mao.add(carta);
        }
    }

    public String exibirMao() {
        StringBuilder res = new StringBuilder();
        for (Carta carta : mao) {
            res.append(carta.getValor()).append(" ");
        }
        return res.toString();
    }

    public String exibirCartasColetadas() {
        StringBuilder res = new StringBuilder();
        for (Carta carta : cartasColetadas) {
            res.append(carta.getValor()).append(" ");
        }
        return res.toString();
    }

    @Override
    public int compareTo(Jogador outro) {
        return Integer.compare(this.totalPontos, outro.totalPontos);
    }
}
