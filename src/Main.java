import Objects.District;
import Objects.Participant;

import java.util.Arrays;
import java.util.Random;

public class Main {
    // Variaveis finais
    private static final int MAX_DISTANCE = 9999;
    private static final int MAX_DISTRICT = 5;
    private static final int MAX_PARTICIPANT = 5;
    private static final int MAX_TEST = 2;
    private static final int MAX_VACANCY = 2;

    // Declara vet globalmente para armazenar as distâncias calculadas
    static int[][] vetDistanciaCalculada = new int[MAX_DISTRICT][MAX_DISTRICT];

    public static void main(String[] args) {
        Participant[] particip = new Participant[MAX_PARTICIPANT];
        District[] dist = new District[MAX_DISTRICT];
        for (int i = 0; i < MAX_PARTICIPANT; i++) {
            particip[i] = new Participant();
        }
        for (int i = 0; i < MAX_DISTRICT; i++) {
            dist[i] = new District();
        }
        int[][] matrizAdjacencia = {
                {0, 3, 2, 0, 0},
                {3, 0, 1, 5, 0},
                {2, 1, 0, 3, 6},
                {0, 5, 3, 0, 4},
                {0, 0, 6, 4, 0}
        };

        // Exibe a matriz de adjacência para verificar
        exibeMatrizAdjacencia(matrizAdjacencia);

        // Calcula e imprime as distâncias para cada bairro como bairro inicial
        calculaImprimeDistanciasBairros(matrizAdjacencia);

        // Imprime a tabela de distâncias final armazenada em vet
        imprimeTabelaDistanciasFinais();

        // Gera e imprime os bairros
        geraBairros(dist);

        // Gera e imprime lista de participantes
        geraParticipantes(particip);

        // Alocar participantes
        alocarParticipantes(matrizAdjacencia, particip, dist, vetDistanciaCalculada);
    }

    static void exibeMatrizAdjacencia(int[][] matrizAdjacencia) {
        System.out.println("Matriz de adjacência:");
        for (int i = 0; i < MAX_DISTRICT; i++) {
            for (int j = 0; j < MAX_DISTRICT; j++) {
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void calculaImprimeDistanciasBairros(int[][] matrizAdjacencia) {
        for (int i = 0; i < MAX_DISTRICT; i++) {
            System.out.println("Distâncias a partir do Bairro " + i + ":");
            dijkstra(matrizAdjacencia, MAX_DISTRICT, i);
        }
    }

    static void imprimeTabelaDistanciasFinais() {
        System.out.println("Tabela de distancias calculadas entre bairros:");
        for (int i = 0; i < MAX_DISTRICT; i++) {
            for (int j = 0; j < MAX_DISTRICT; j++) {
                System.out.print("[B" + j + "][B" + i + "] = " + vetDistanciaCalculada[i][j] + "\t");
            }
            System.out.println();
        }
    }

    static void geraBairros(District[] dist) {
        System.out.println("Iniciando Provas:");
        Random rand = new Random();
        for (int i = 0; i < MAX_DISTRICT; i++) {
            dist[i].id = i;
            dist[i].type_of_test = rand.nextInt(MAX_TEST);
            dist[i].capacity = 1;
            System.out.println("Bairro[" + dist[i].id + "] = Prova: " + dist[i].type_of_test + " | Vagas: " + dist[i].capacity);
        }
    }

    static void geraParticipantes(Participant[] particip) {
        System.out.println("Iniciando Candidatos");
        Random rand = new Random();
        for (int x = 0; x < MAX_PARTICIPANT; x++) {
            particip[x].id = x;
            particip[x].type_of_test = rand.nextInt(MAX_TEST);
            particip[x].district = rand.nextInt(MAX_DISTRICT);
            System.out.println("Cand [" + particip[x].id + "] | Bairro: [" + particip[x].district + "] | Prova: [" + particip[x].type_of_test + "]");
        }
    }

    public static void alocarParticipantes(int[][] matrizAdjacencia, Participant[] particip, District[] dist, int[][] vet) {
        for (int z = 0; z < MAX_PARTICIPANT; z++) {
            int aux = Integer.MAX_VALUE;
            int aux_bairro = 0;
            int aux_vet = 0;
            int k;

            for (int i = 0; i < MAX_DISTRICT; i++) {
                if (particip[z].district == i) {
                    System.out.println("Candidato: " + z);
                    System.out.println("Bairro Candidato: " + particip[z].district);
                    System.out.println("Bairro Atual: " + i);
                    System.out.println("Ha vagas? " + dist[i].capacity);

                    System.out.println("Verificando Menor Caminho: ");
                    for (int j = 0; j < MAX_DISTRICT; j++) {
                        //System.out.println("ENTROU AQUI BLOCO 01!");
                        System.out.println("[B" + i + "][B" + j + "] = " + vet[i][j]);
                        System.out.println("Ha vagas? " + dist[j].capacity);
                        System.out.println("Prova Bairro: [" + dist[j].type_of_test + "]");
                        System.out.println("Prova Cadidato: [" + particip[z].type_of_test + "]");

                        if (j == 0 && dist[j].type_of_test == particip[z].type_of_test && dist[j].capacity > 0) {
                            aux = vet[i][j];
                            aux_bairro = j;
                            /*System.out.println("ENTROU AQUI BLOCO 01!");
                            System.out.println("[B" + i + "][B" + j + "] = " + vet[i][j]);
                            //System.out.println("Ha vagas? " + dist[j].capacity);
                            System.out.println("Prova Bairro: [" + dist[j].type_of_test + "]");
                            System.out.println("Prova Cadidato: [" + particip[z].type_of_test + "]");*/
                            dijkstraImprimeMenorCaminho(matrizAdjacencia, MAX_DISTRICT, i, j);
                        }

                        if (aux > vet[i][j] && dist[j].type_of_test == particip[z].type_of_test && dist[j].capacity > 0) {
                            /*System.out.println("ENTROU AQUI BLOCO 02!");
                            //System.out.println("Menor distancia encontrada. ");
                            System.out.println("[B" + i + "][B" + j + "] = " + vet[i][j]);
                            //System.out.println("Ha vagas? " + dist[j].capacity);
                            System.out.println("Prova Bairro: [" + dist[j].type_of_test + "]");
                            System.out.println("Prova Cadidato: [" + particip[z].type_of_test + "]");*/
                            dijkstraImprimeMenorCaminho(matrizAdjacencia, MAX_DISTRICT, i, j);
                            aux = vet[i][j];
                            aux_bairro = j;
                        }
                        aux_vet = vet[i][j];
                    }

                    if (aux == Integer.MAX_VALUE) {
                        System.out.println("Nao ah vagas disponiveis para prova " + particip[z].type_of_test + " nos bairros pesquisados.");
                    } else {
                        System.out.println("Menor Distancia Geral: [" + aux + "]");
                        System.out.println("Bairro Menor Distancia: [" + aux_bairro + "]");
                    }

                    for (k = aux_bairro; k <= aux_bairro; k++){
                        if (dist[aux_bairro].capacity > 0) {
                            dist[aux_bairro].capacity = dist[aux_bairro].capacity - 1;
                        }
                    }
                }
                System.out.println();
            }
        }
    }

    static void dijkstra(int[][] matrizAdjacencia, int n, int inicial) {
        int[][] matrizCusto = new int[n][n];
        int[] distancia = new int[n];
        int[] pred = new int[n];
        int[] visitado = new int[n];
        int cont, distanciaMin, proxNo = 0;

        // Inicializa a matrizCusto
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdjacencia[i][j] == 0 && i != j) {
                    matrizCusto[i][j] = MAX_DISTANCE;
                } else {
                    matrizCusto[i][j] = matrizAdjacencia[i][j];
                }
            }
        }

        // Configuração inicial dos arrays
        for (int i = 0; i < n; i++) {
            distancia[i] = matrizCusto[inicial][i];
            pred[i] = inicial;
            visitado[i] = 0;
        }

        distancia[inicial] = 0;
        visitado[inicial] = 1;
        cont = 1;

        while (cont < n - 1) {
            distanciaMin = MAX_DISTANCE;

            // Escolhe o bairro com a distância mínima
            for (int i = 0; i < n; i++) {
                if (distancia[i] < distanciaMin && visitado[i] == 0) {
                    distanciaMin = distancia[i];
                    proxNo = i;
                }
            }

            visitado[proxNo] = 1;

            // Atualiza a distância de cada bairro
            for (int i = 0; i < n; i++) {
                if (visitado[i] == 0) {
                    if (distanciaMin + matrizCusto[proxNo][i] < distancia[i]) {
                        distancia[i] = distanciaMin + matrizCusto[proxNo][i];
                        pred[i] = proxNo;
                    }
                }
            }
            cont++;
        }

        // Armazena e imprime o caminho e a distância de cada bairro a partir do bairro inicial
        for (int i = 0; i < n; i++) {
            if (i != inicial) {
                vetDistanciaCalculada[inicial][i] = distancia[i]; // Salva a distância no vet
                System.out.println("Distância para o Bairro " + i + " = " + distancia[i]);
                System.out.print("Caminho = B" + i);

                int j = i;
                do {
                    j = pred[j];
                    System.out.print(" <- B" + j);
                } while (j != inicial);
                System.out.println();
            }
        }
    }

    public static void dijkstraImprimeMenorCaminho(int[][] matrizAdjacencia, int n, int inicial, int finall) {
        int[][] matrizCusto = new int[n][n];
        int[] distancia = new int[n];
        int[] pred = new int[n];
        boolean[] visitado = new boolean[n];
        int cont, distanciaMin, proxNo;

        // Inicializa a matrizCusto
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrizAdjacencia[i][j] == 0 && i != j) {
                    matrizCusto[i][j] = MAX_DISTANCE;
                } else {
                    matrizCusto[i][j] = matrizAdjacencia[i][j];
                }
            }
        }

        // Configuração inicial dos arrays
        Arrays.fill(distancia, MAX_DISTANCE);
        Arrays.fill(pred, inicial);
        Arrays.fill(visitado, false);

        distancia[inicial] = 0;
        visitado[inicial] = true;
        cont = 1;

        while (cont < n - 1) {
            distanciaMin = MAX_DISTANCE;

            // Escolhe o bairro com a distância mínima
            proxNo = -1;
            for (int i = 0; i < n; i++) {
                if (distancia[i] < distanciaMin && !visitado[i]) {
                    distanciaMin = distancia[i];
                    proxNo = i;
                }
            }

            if (proxNo == -1) break;

            visitado[proxNo] = true;

            // Atualiza a distância de cada bairro
            for (int i = 0; i < n; i++) {
                if (!visitado[i]) {
                    if (distanciaMin + matrizCusto[proxNo][i] < distancia[i]) {
                        distancia[i] = distanciaMin + matrizCusto[proxNo][i];
                        pred[i] = proxNo;
                    }
                }
            }
            cont++;
        }

        // Armazena e imprime o caminho e a distância entre o bairro inicial e o final
        // Note: vet array is not defined in the original code, so this line is commented out
        // vet[inicial][i] = distancia[i];
        //System.out.println("Distância para o Bairro " + inicial + " = " + distancia[finall]);
        System.out.print("Caminho percorrido = B" + finall);

        int j = finall;
        do {
            j = pred[j];
            System.out.print(" <- B" + j);
        } while (j != inicial);
        System.out.println();
    }

}

