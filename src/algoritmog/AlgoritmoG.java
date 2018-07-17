package algoritmog;

import java.util.Random;

/**
 * @author Eduardo Sales, e-mail eduardosallis30@gmail.com
 */
public class AlgoritmoG {

    public static void main(String[] args) {

        //Eixos definidos com 200 posições
        //Fiz um Random.nextInt(1000) 200 vezes e coloquei os valores ai
        int[] eixoX = {580, 600, 132, 326, 770, 396, 667, 605, 930, 993, 864, 322, 887, 747, 281, 686, 839, 656, 697, 266, 194, 319, 724, 317, 471, 578, 537, 245, 603, 165, 64, 538, 995, 822, 860, 872, 406, 173, 760, 267, 798, 243, 803, 698, 293, 860, 717, 70, 26, 159, 369, 921, 67, 554, 475, 730, 298, 560, 441, 125, 171, 789, 820, 365, 977, 885, 109, 308, 222, 2, 482, 47, 841, 925, 384, 308, 567, 330, 264, 452, 188, 190, 556, 387, 793, 314, 792, 461, 93, 959, 435, 393, 694, 768, 997, 935, 743, 557, 929, 435, 100, 642, 65, 76, 628, 434, 993, 85, 461, 713, 942, 114, 708, 168, 91, 330, 586, 441, 70, 371, 432, 112, 813, 729, 380, 676, 978, 148, 337, 318, 175, 830, 539, 495, 570, 234, 552, 294, 370, 414, 79, 456, 871, 249, 268, 70, 397, 469, 202, 114, 649, 909, 631, 857, 290, 728, 483, 745, 321, 36, 824, 724, 65, 579, 651, 299, 103, 128, 273, 28, 602, 98, 778, 860, 211, 344, 761, 189, 230, 379, 970, 353, 679, 96, 642, 996, 110, 538, 722, 549, 523, 171, 500, 813, 409, 51, 508, 157, 62, 71};
        int[] eixoY = {289, 377, 725, 174, 627, 279, 758, 917, 579, 694, 703, 319, 839, 385, 171, 718, 449, 166, 338, 762, 546, 76, 73, 89, 569, 500, 716, 545, 556, 66, 398, 729, 28, 875, 77, 213, 691, 814, 224, 57, 718, 317, 786, 250, 284, 115, 558, 682, 814, 996, 234, 371, 927, 274, 910, 952, 484, 852, 460, 969, 451, 950, 655, 77, 573, 949, 478, 639, 853, 714, 343, 395, 399, 220, 222, 63, 820, 304, 429, 645, 195, 562, 683, 421, 199, 555, 408, 845, 845, 330, 916, 490, 988, 517, 165, 310, 28, 583, 823, 370, 182, 468, 121, 90, 554, 218, 437, 45, 441, 924, 148, 146, 43, 865, 796, 304, 58, 542, 692, 575, 491, 640, 245, 871, 587, 660, 147, 534, 147, 747, 195, 840, 749, 113, 244, 974, 406, 571, 104, 450, 468, 54, 425, 31, 720, 69, 950, 898, 155, 17, 5, 765, 153, 264, 264, 545, 370, 416, 482, 761, 406, 906, 113, 131, 382, 313, 715, 631, 776, 482, 283, 352, 305, 612, 26, 848, 714, 607, 752, 381, 341, 214, 674, 696, 966, 31, 595, 483, 644, 31, 621, 536, 671, 816, 174, 822, 673, 708, 398, 109};

        int alcance_Roteador = 100; //Raio de alcance do roteador
        int geracoes = 2000;

        AlgoritmoG ag = new AlgoritmoG();

        int[] roteadorMelhor = ag.populacaoInicial(2);
        int[] X = new int[2];
        int[] Y = new int[2];
        int[] Z = new int[2];

        int alcancadosMelhor = ag.distanciaEuclidiana(roteadorMelhor, eixoX, eixoY, alcance_Roteador);
        int alcanceX = 0;
        int alcanceY = 0;
        int alcanceZ = 0;

        //System.out.println(roteadorMelhor[0] + " " + roteadorMelhor[1] + " " + alcancadosMelhor);
        /*por mais incrivel que pareça aqui é o inicio do crossover
        *Material de referência esta junto com o projeto anexado no e-mail.
         */
        for (int i = 0; i < geracoes; i++) {

            X = ag.populacaoInicial(2);
            Y = ag.populacaoInicial(2);

            alcanceX = ag.distanciaEuclidiana(X, eixoX, eixoY, alcance_Roteador);
            alcanceY = ag.distanciaEuclidiana(Y, eixoX, eixoY, alcance_Roteador);

            Z = ag.roleta(X, Y, alcanceX, alcanceY);
            alcanceZ = ag.distanciaEuclidiana(Z, eixoX, eixoY, alcance_Roteador);

            if (alcanceZ > alcancadosMelhor) {

                alcancadosMelhor = alcanceZ;
                roteadorMelhor = Z;
            }
        }

        System.out.println("Quantidades de pontos alcançados: " + alcancadosMelhor);
        System.out.println("Posição do Roteador: X: " + roteadorMelhor[0] + " Y: " + roteadorMelhor[1]);

    }

    public int[] roleta(int[] a, int[] b, int alcanceA, int alcanceB) {

        /*
        Seleciona os melhores individos gerados aleatoriamente como mostra a formula
        no trabalho relacionado.
         */
        Random ale = new Random();

        int[] selecao = new int[2];

        int[] X = a;
        int[] Y = b;

        int soma = (alcanceA + alcanceB);

        //Este IF serve para evitar casos excepicionais onde pode haver uma divisão por 0 resultante da distancia euclidiana
        if (soma != 0) {
            double A = (alcanceA * 100) / soma;

            for (int i = 0; i < 2; i++) {
                if (A >= ale.nextInt(100)) {
                    selecao[i] = X[i];
                } else {
                    selecao[i] = Y[i];
                }
            }
        }

        return selecao;

    }

    public int[] populacaoInicial(int a) {
        /*
        Gera as populações de forma aleatoria.
         */

        int[] populacao = new int[a];
        Random Ale = new Random();

        for (int i = 0; i < populacao.length; i++) {
            populacao[i] = Ale.nextInt(1000);

        }
        return populacao;
    }

    public int distanciaEuclidiana(int[] populacaoInicial, int[] x, int[] y, int a) {

        /*
        Formula matematica para calcular a distancia euclidiana entre o roteador
        e os pontos de acesso.
         */
        int result;
        double raiz;
        int alcancados = 0;
        int alcance = a;

        int[] eixoX = x;
        int[] eixoY = y;
        int[] populacao = populacaoInicial;

        double[] resultado = new double[200];

        for (int i = 0; i < eixoX.length; i++) {

            result = (int) ((int) Math.pow(eixoX[i] - populacao[0], 2) + Math.pow(eixoY[i] - populacao[1], 2));

            raiz = Math.sqrt(result);
            resultado[i] = raiz;

        }

        for (int i = 0; i < resultado.length; i++) {
            if (resultado[i] < alcance) {
                alcancados += 1;
            }

        }

        return alcancados;
    }

}
