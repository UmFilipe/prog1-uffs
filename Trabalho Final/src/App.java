import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class App {
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
                System.out.println("Mão de " + jogador.nome + ": ");
                System.out.println(jogador.maoJogador());
                System.out.println();
                int cartaJogada = getCardToPlay(jogador.mao, scanner);
                cartasJogadas.add(jogador.mao.remove(cartaJogada));
            }

            Collections.sort(cartasJogadas);
            insertCards(tabuleiro, cartasJogadas);
        }
    }

    private static void resultadoFinal(ArrayList<Jogador> jogadores) {
        Collections.sort(jogadores);

        System.out.println("Resultado final:");

        for (Jogador jogador : jogadores) {
            System.out.println(jogador.nome);
            System.out.println("Cartas coletadas: " + jogador.imprimirCartasColetadas());
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

    private static void imprimirTabuleiro(ArrayList<ArrayList<Carta>> tabuleiro) {
        for (ArrayList<Carta> row : tabuleiro) {
            for (Carta carta : row) {
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
            System.out.println(jogador.nome + ": " + jogador.pontos + " pontos");
            System.out.println();
        }
    }

    private static void insertCards(ArrayList<ArrayList<Carta>> tabuleiro, ArrayList<Carta> cartasJogadas) {
        for (Carta cartaJogada : cartasJogadas) {
            int indexOfRow = getIndexOfRowWithMinimumDifference(tabuleiro, cartaJogada);

            if (indexOfRow == -1) {
                indexOfRow = getIndexOfRowWithLargestNumber(tabuleiro);
            }

            ArrayList<Carta> cardsRemovedFromRow = new ArrayList<>();
            int pontos = 0;

            var linha = tabuleiro.get(indexOfRow);

            for (int i = 0; i < linha.size(); i++) {
                if (linha.get(i).numero != 0) {
                    cardsRemovedFromRow.add(linha.get(i));
                    pontos += linha.get(i).valor;
                }

                if (linha.get(i).numero == 0) {
                    if (linha.get(i - 1).numero > cartaJogada.numero) {
                        cartaJogada.jogador.cartasColetadas.addAll(cardsRemovedFromRow);
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
                } else if (i == 4) {
                    cartaJogada.jogador.cartasColetadas.addAll(cardsRemovedFromRow);
                    cartaJogada.jogador.pontos += pontos;
                    linha.clear();
                    linha.add(cartaJogada);
                    for (int j = 1; j < 5; j++) {
                        linha.add(new Carta());
                    }
                    break;
                }
            }
        }
    }

    private static int getIndexOfRowWithLargestNumber(ArrayList<ArrayList<Carta>> tabuleiro) {
        int largestNumber = Integer.MIN_VALUE, indexOfRow = -1;

        for (int i = 0; i < tabuleiro.size(); i++) {
            var row = tabuleiro.get(i);
            for (int j = row.size() - 1; j >= 0; j--) {
                if (row.get(j).numero != 0) {
                    if (row.get(j).numero > largestNumber) {
                        largestNumber = row.get(j).numero;
                        indexOfRow = i;
                    }
                    break;
                }
            }
        }

        return indexOfRow;
    }

    private static int getIndexOfRowWithMinimumDifference(ArrayList<ArrayList<Carta>> tabuleiro, Carta cardPlayed) {
        int finalDifference = Integer.MAX_VALUE, indexOfRow = -1;

        for (int i = 0; i < tabuleiro.size(); i++) {
            var row = tabuleiro.get(i);
            for (int j = row.size() - 1; j >= 0; j--) {
                if (row.get(j).numero != 0) {
                    if (row.get(j).numero < cardPlayed.numero) {
                        if (cardPlayed.numero - row.get(j).numero < finalDifference) {
                            finalDifference = cardPlayed.numero - row.get(j).numero;
                            indexOfRow = i;
                        }
                    }
                    break;
                }
            }
        }

        return indexOfRow;
    }

    private static int getCardToPlay(ArrayList<Carta> mao, Scanner scanner) {
        int numero = 0, index = -1;
        System.out.println("Digite uma carta para jogar: ");

        while (index == -1) {
            try {
                numero = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < mao.size(); i++) {
                    if (mao.get(i).numero == numero) {
                        index = i;
                        break;
                    }
                }

                if (index == -1) {
                    System.out.println("Carta não está na mão.");
                    System.out.println("Digite uma carta para jogar: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("A entrada deve ser um número inteiro.");
                System.out.println("Digite uma carta para jogar: ");
            }
        }

        return index;
    }
}
