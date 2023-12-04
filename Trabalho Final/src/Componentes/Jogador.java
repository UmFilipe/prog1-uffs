package Componentes;

import java.util.ArrayList;
import java.util.Stack;

public class Jogador implements Comparable<Jogador>{
    public String nome;
    public int pontos = 0;
    public ArrayList<Carta> mao = new ArrayList<Carta>();
    public ArrayList<Carta> cartasColetadas = new ArrayList<Carta>();

    // Método usado para comprar as 12 cartas iniciais
    public void cartasIniciais(Stack<Carta> baralho){
        int i = 0;
        while (i < 12) {
            Carta carta = baralho.pop();
            carta.jogador = this;
            this.mao.add(carta);
            i++;
        }
        
    }

    // Método usado para imprimir as cartas na mão do jogador ou as cartas coletadas no fim do jogo
    public String imprimirCartas(boolean jogoFinalizado) {
        String cartas = "";
        ArrayList<Carta> listaCartas = new ArrayList<Carta>();

        if (jogoFinalizado == false){
            listaCartas = this.mao;
        } else {
            listaCartas = this.cartasColetadas;
        }
    
        for (Carta carta : listaCartas) {
            cartas += carta.numero + " | ";
        }
    
        return cartas;
    }

    @Override
    public int compareTo(Jogador outro) {
        return Integer.compare(this.pontos, outro.pontos);
    }
}