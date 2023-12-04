import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

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

        // Criando os jogadores usando a quantidade de jogadores e o baralho previamente criados
        ArrayList<Jogador> jogadores = criarJogadores(baralho, scanner);

        // Inicializando o tabuleiro
        ArrayList<ArrayList<Carta>> tabuleiro = inicializarTabuleiro(baralho);

        // Chamando a função que inicia o jogo
        jogarJogo(jogadores, tabuleiro, scanner);

        // Imprimindo o resultado final após o jogo
        resultadoFinal(jogadores);

        scanner.close();
    }

    private static ArrayList<Jogador> criarJogadores(Stack<Carta> baralho, Scanner scanner) {
        ArrayList<Jogador> jogadores = new ArrayList<>();
        int quantidadeJogadores;

        do {
            System.out.println("Digite o número de jogadores (entre 3 e 6): ");
            quantidadeJogadores = scanner.nextInt();
        } while (quantidadeJogadores < 3 || quantidadeJogadores > 6);

        scanner.nextLine();

        for (int i = 1; i <= quantidadeJogadores; i++) {
            jogadores.add(new Jogador());
            jogadores.get(i - 1).comprarCartas(baralho, 12);
            System.out.println("Digite o nome do jogador " + i + ": ");
            jogadores.get(i - 1).nome = scanner.nextLine();
            System.out.println();
        }
        return jogadores;
    }

    private static ArrayList<ArrayList<Carta>> inicializarTabuleiro(Stack<Carta> baralho) {
        ArrayList<ArrayList<Carta>> tabuleiro = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tabuleiro.add(new ArrayList<>());
            for (int j = 0; j < 5; j++) {
                tabuleiro.get(i).add(j == 0 ? baralho.pop() : new Carta());
            }
        }
        return tabuleiro;
    }

    private static void jogarJogo(ArrayList<Jogador> jogadores, ArrayList<ArrayList<Carta>> tabuleiro, Scanner scanner) {
        while (!jogadores.get(0).mao.isEmpty()) {
            ArrayList<Carta> cartasJogadas = new ArrayList<>();

            imprimirTabuleiro(tabuleiro);
            System.out.println();
            quantidadePontos(jogadores);

            for (Jogador jogador : jogadores) {
                System.out.println("Jogada de " + jogador.nome + ": ");
                System.out.println(jogador.imprimirCartas(false));
                System.out.println();
                int carta = jogada(jogador.mao, scanner);
                cartasJogadas.add(jogador.mao.remove(carta));
            }

            Collections.sort(cartasJogadas);
            inserirCartas(tabuleiro, cartasJogadas);
        }
    }

    private static void imprimirTabuleiro(ArrayList<ArrayList<Carta>> tabuleiro) {
        System.out.println("Tabuleiro: ");
        for (ArrayList<Carta> linha : tabuleiro) {
            for (Carta carta : linha) {
                System.out.print("(");
                if (carta.numero == 0) {
                    System.out.print("-");
                } else {
                    System.out.print(carta.numero);
                }
                System.out.print(")");
            }
            System.out.println();
        }
    }

    private static void quantidadePontos(ArrayList<Jogador> jogadores) {
        for (Jogador jogador : jogadores) {
            if (jogador != jogadores.get(jogadores.size() - 1)) {
                System.out.print(jogador.nome + ": " + jogador.pontos + " | ");
            } else {
                System.out.println(jogador.nome + ": " + jogador.pontos);

            }
        }

        System.out.println();

    }

    private static int jogada(ArrayList<Carta> mao, Scanner scanner) {
        int numero = 0, carta = -1;
        System.out.println("Escolha uma carta para jogar: ");

        while (carta == -1) {
            try {
                numero = Integer.parseInt(scanner.nextLine());
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
            } catch (NumberFormatException e) {
                System.out.println("Você deve inserir um número inteiro.");
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
    
            ArrayList<Carta> cartasRemovidasDaLinha = new ArrayList<>();
            int pontos = 0;
    
            var linha = tabuleiro.get(indiceLinha);
    
            for (int i = 0; i < linha.size(); i++) {
                Carta cartaAtual = linha.get(i);
                if (cartaAtual.numero != 0) {
                    cartasRemovidasDaLinha.add(cartaAtual);
                    pontos += cartaAtual.valor;
                }
    
                if (cartaAtual.numero == 0 || i == 4) {
                    if ((i == 4 || linha.get(i - 1).numero > cartaJogada.numero)) {
                        cartaJogada.jogador.cartasColetadas.addAll(cartasRemovidasDaLinha);
                        cartaJogada.jogador.pontos += pontos;
                        linha.clear();
                        linha.add(cartaJogada);
                        for (int j = 1; j < 5; j++) {
                            linha.add(new Carta());
                        }
                    } else {
                        linha.remove(i);
                        linha.add(i, cartaJogada);
                    }
                    break;
                }
            }
        }
    }
    

    private static int maiorNumero(ArrayList<ArrayList<Carta>> tabuleiro) {
        int maiorNumero = Integer.MIN_VALUE, indiceLinha = -1;
    
        for (int i = 0; i < tabuleiro.size(); i++) {
            var linha = tabuleiro.get(i);
            for (int j = linha.size() - 1; j >= 0; j--) {
                Carta cartaAtual = linha.get(j);
                if (cartaAtual.numero != 0 && cartaAtual.numero > maiorNumero) {
                    maiorNumero = cartaAtual.numero;
                    indiceLinha = i;
                    break;
                }
            }
        }
    
        return indiceLinha;
    }
    

    private static int menorDiferenca(ArrayList<ArrayList<Carta>> tabuleiro, Carta cartaJogada) {
        int menorDiferenca = Integer.MAX_VALUE, indiceLinha = -1;
    
        for (int i = 0; i < tabuleiro.size(); i++) {
            var linha = tabuleiro.get(i);
            for (int j = linha.size() - 1; j >= 0; j--) {
                Carta cartaAtual = linha.get(j);
                if (cartaAtual.numero != 0 && cartaAtual.numero < cartaJogada.numero) {
                    int diferenca = cartaJogada.numero - cartaAtual.numero;
                    if (diferenca < menorDiferenca) {
                        menorDiferenca = diferenca;
                        indiceLinha = i;
                    }
                    break;
                }
            }
        }
    
        return indiceLinha;
    }

    private static void resultadoFinal(ArrayList<Jogador> jogadores) {
        Collections.sort(jogadores);

        System.out.println("Resultado final:");

        for (Jogador jogador : jogadores) {
            System.out.println(jogador.nome);
            System.out.println("Cartas coletadas: " + jogador.imprimirCartas(true));
            System.out.println("Pontuação: " + jogador.pontos);
            System.out.println();
        }

        System.out.println("Vencedor(es)");

        for (Jogador vencedor : jogadores) {
            if (vencedor.pontos == jogadores.get(0).pontos) {
                System.out.println(vencedor.nome);
            }
        }
    }
    
}
