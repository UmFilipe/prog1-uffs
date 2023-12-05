import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;


import Componentes.Carta;
import Componentes.Jogador;
public class Jogo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Criando o baralho
        Stack<Carta> baralho = new Stack<>();

        // Adicionando as 109 cartas ao baralho
        for (int i = 1; i <= 109; i++) {
            baralho.add(new Carta(i));
        }

        // Embaralhando
        Collections.shuffle(baralho);

        // Criando os jogadores usando o baralho previamente criado
        ArrayList<Jogador> jogadores = criarJogadores(baralho, scanner);

        // Inicializando o tabuleiro
        ArrayList<ArrayList<Carta>> tabuleiro = inicializarTabuleiro(baralho);

        // Chamando a função que inicia o jogo
        jogarJogo(jogadores, tabuleiro, scanner);

        // Imprimindo o resultado do jogo
        resultado(jogadores);

        scanner.close();
    }

    private static ArrayList<Jogador> criarJogadores(Stack<Carta> baralho, Scanner scanner) {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        int quantidadeJogadores;
        
        // Verificando se a quantidade de jogadores está entre 3 e 6
        do {
            System.out.println("Digite o número de jogadores (entre 3 e 6): ");
            quantidadeJogadores = scanner.nextInt();
        } while (quantidadeJogadores < 3 || quantidadeJogadores > 6);

        scanner.nextLine(); 
    
        // Criando os jogadores e distribuindo as cartas iniciais
        for (int i = 0; i < quantidadeJogadores; i++) {
            Jogador jogador = new Jogador();
            jogador.cartasIniciais(baralho);
    
            System.out.println("Digite o nome do jogador " + (i + 1) + ": ");
            jogador.nome = scanner.nextLine();
            System.out.println();
    
            jogadores.add(jogador);
        }
    
        return jogadores;
    }
    

    private static ArrayList<ArrayList<Carta>> inicializarTabuleiro(Stack<Carta> baralho) {
        ArrayList<ArrayList<Carta>> tabuleiro = new ArrayList<>();
    
        for (int i = 0; i < 5; i++) {
            ArrayList<Carta> linha = new ArrayList<>();
    
            for (int j = 0; j < 5; j++) {
                Carta carta;
                if (j == 0) {
                    carta = baralho.pop();
                } else {
                    carta = new Carta();
                }
                linha.add(carta);
            }
    
            tabuleiro.add(linha);
        }
    
        return tabuleiro;
    }

    private static void jogarJogo(ArrayList<Jogador> jogadores, ArrayList<ArrayList<Carta>> tabuleiro, Scanner scanner) {
        // Enquanto houver cartas na mão de algum jogador, o jogo continua
        while (!jogadores.get(0).mao.isEmpty()) {
            // Criando uma lista com as cartas jogadas
            ArrayList<Carta> cartasJogadas = new ArrayList<>();

            // Imprimindo o tabuleiro e a pontuação de cada jogador
            imprimirTabuleiro(tabuleiro);
            System.out.println();
            for (Jogador jogador : jogadores) {
            if (jogador != jogadores.get(jogadores.size() - 1)) {
                System.out.print(jogador.nome + ": " + jogador.pontos + " | ");
            } else {
                System.out.println(jogador.nome + ": " + jogador.pontos);
            }
        }

        System.out.println();

            // Imprimindo as cartas na mão de cada jogador e solicitando a jogada ao usuário
            for (Jogador jogador : jogadores) {
                System.out.println("Jogada de " + jogador.nome + ": ");
                System.out.println(jogador.imprimirCartas(false));
                System.out.println();
                int carta = jogada(jogador.mao, scanner);
                cartasJogadas.add(jogador.mao.remove(carta));
            }

            // Ordenando as cartas jogadas e inserindo no tabuleiro
            Collections.sort(cartasJogadas);
            inserirCartas(tabuleiro, cartasJogadas);
        }
    }

    private static void imprimirTabuleiro(ArrayList<ArrayList<Carta>> tabuleiro) {
        System.out.println("Tabuleiro: ");
        for (ArrayList<Carta> linha : tabuleiro) {
            for (Carta carta : linha) {
                System.out.print("(");
                if (carta.numero == -1) {
                    System.out.print("-");
                } else {
                    System.out.print(carta.numero);
                }
                System.out.print(")");
            }
            System.out.println();
        }
    }

    private static int jogada(ArrayList<Carta> mao, Scanner scanner) {
        int numero = 0;
        int carta = -1; 

        System.out.println("Escolha uma carta para jogar: ");

        // Verificando se a carta escolhida está na mão do jogador
        while (carta == -1) {
            try {
                numero = scanner.nextInt();
                for (int i = 0; i < mao.size(); i++) {
                    if (mao.get(i).numero == numero) {
                        carta = i;
                        break;
                    }
                }

                if (carta == -1) {
                    System.out.println("Esta carta não está na sua mão.");
                    System.out.println("Escolha uma carta para jogar: ");
                }

            // Verificando se a entrada está no formato correto (inteiro)
            } catch (InputMismatchException e) {
                scanner.next();
                System.out.println("A entrada deve ser um número inteiro.");
                System.out.println("Escolha uma carta para jogar: ");
            }
                
        }

        return carta;
    }

        private static void inserirCartas(ArrayList<ArrayList<Carta>> tabuleiro, ArrayList<Carta> cartasJogadas) {
        for (Carta cartaJogada : cartasJogadas) {
            int indiceLinha = menorDiferenca(tabuleiro, cartaJogada);

            if (indiceLinha == -1) {
                indiceLinha = maiorNumero(tabuleiro);
            }

            ArrayList<Carta> cartasRemovidas = new ArrayList<Carta>();
            int pontos = 0;

            var linha = tabuleiro.get(indiceLinha);

            for (int i = 0; i < linha.size(); i++) {

                if (linha.get(i).numero != -1){
                    cartasRemovidas.add(linha.get(i));
                    pontos += linha.get(i).valor;
                }

                if (linha.get(i).numero == -1) {
                    if(linha.get(i-1).numero > cartaJogada.numero){
                        pegarLinha(cartaJogada, cartasRemovidas, pontos, linha);
                    }else{
                        linha.remove(i);
                        linha.add(i, cartaJogada);
                    }
                    break;
                }else if(i == 4){
                    pegarLinha(cartaJogada, cartasRemovidas, pontos, linha);
                    break;
                }
            }

        }
    }

    private static void pegarLinha(Carta cartaJogada, ArrayList<Carta> cartasRemovidas, int pontos, ArrayList<Carta> linha) {
            cartaJogada.jogador.cartasColetadas.addAll(cartasRemovidas);
            cartaJogada.jogador.pontos += pontos;
            linha.clear();
            linha.add(cartaJogada);
            for (int j = 1; j < 5; j++) {
                linha.add(new Carta());
            }
        }
    

    private static int maiorNumero(ArrayList<ArrayList<Carta>> tabuleiro) {
            int maiorNumero = Integer.MIN_VALUE;
            int linha = -1;
        
            // Procurando a linha com a maior carta não vazia
            for (int i = 0; i < tabuleiro.size(); i++) {
                var l = tabuleiro.get(i);
        
                for (int j = 0; j < l.size(); j++) {
                    Carta cartaAtual = l.get(j);
                    if (cartaAtual.numero != -1 && cartaAtual.numero > maiorNumero) {
                        maiorNumero = cartaAtual.numero;
                        linha = i;
                    }
                }
            }
        
            return linha;
        }
    private static int menorDiferenca(ArrayList<ArrayList<Carta>> tabuleiro, Carta cartaJogada) {
        int menorDiferenca = Integer.MAX_VALUE;
        int linha = -1;
    
        // Procurando a linha com a menor diferença entre a carta jogada e as cartas da linha
        for (int i = 0; i < tabuleiro.size(); i++) {
            var l = tabuleiro.get(i);
    
            for (int j = l.size() - 1; j >= 0; j--) {
                Carta cartaAtual = l.get(j);

                if (cartaAtual.numero != -1) {
                    if(cartaAtual.numero < cartaJogada.numero){
                        int diferenca = cartaJogada.numero - cartaAtual.numero;
    
                        if (diferenca < menorDiferenca) {
                            menorDiferenca = diferenca;
                            linha = i;
                        }
                    }
                    break;
                }
            }
        }
    
        return linha;
    }

    private static void resultado(ArrayList<Jogador> jogadores) {

        System.out.println("Resultado:");

        // Imprimindo as cartas coletadas por cada jogador
        for (Jogador jogador : jogadores) {
            System.out.println("Cartas coletadas por: "+ jogador.nome);
            System.out.println(jogador.imprimirCartas(true));
            System.out.println();
        }

        // Ordenando os jogadores pelo número de pontos
        Collections.sort(jogadores);
        
        for (Jogador jogador : jogadores) {
            System.out.print("Pontuação de " + jogador.nome + ": " + jogador.pontos);
        
            if (jogadores.indexOf(jogador) < jogadores.size() - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();

        // Imprimindo o(s) vencedor(es)
        System.out.println("Vencedor(es): ");

        for (Jogador vencedor : jogadores) {
            if (vencedor.pontos == jogadores.get(0).pontos) {
                System.out.println(vencedor.nome);
            }
        }
    }
    
}
