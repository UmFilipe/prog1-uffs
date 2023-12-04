import java.util.ArrayList;
import java.util.Stack;
public class Jogador implements Comparable<Jogador>{
    public ArrayList<Carta> mao = new ArrayList<Carta>();
    public ArrayList<Carta> cartasColetadas = new ArrayList<Carta>();
    public String nome;
    public int pontos = 0;

    public void comprarCartas(Stack<Carta> baralho, int quantidade){
        int i = 0;
        while (i < quantidade) {
            Carta carta = baralho.pop();
            carta.jogador = this;
            this.mao.add(carta);
            i++;
        }
        
    }

    public String imprimirCartas(boolean coletadas) {
        String cartas = "";
        ArrayList<Carta> listaCartas = coletadas ? cartasColetadas : mao;
    
        for (Carta carta : listaCartas) {
            cartas += carta.numero + " | ";
        }
    
        return cartas;
    }

    @Override
    public int compareTo(Jogador other) {
        return Integer.compare(this.pontos, other.pontos);
    }
}