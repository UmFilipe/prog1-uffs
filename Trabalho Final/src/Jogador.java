import java.util.ArrayList;
import java.util.Stack;
public class Jogador implements Comparable<Jogador>{
    public ArrayList<Carta> mao = new ArrayList<Carta>();
    public ArrayList<Carta> cartasColetadas = new ArrayList<Carta>();
    public String nome;
    public int pontos = 0;

    public void comprarCartas(Stack<Carta> baralho, int amount){
        for (int i = 0; i < amount; i++) {
            Carta carta = baralho.pop();
            carta.jogador = this;
            this.mao.add(carta);
        }
    }

    public String maoJogador(){
        String res = "";
        for (Carta carta : mao) {
            res += carta.numero + " ";
        }
        return res;
    }

    public String imprimirCartasColetadas(){
        String res = "";
        for (Carta carta : cartasColetadas) {
            res += carta.numero + " ";
        }
        return res;
    }

    @Override
    public int compareTo(Jogador other) {
        return Integer.compare(this.pontos, other.pontos);
    }
}