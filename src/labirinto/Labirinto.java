/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labirinto;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author PC
 */
public class Labirinto {

    /**
     * @param args the command line arguments
     */
    private String[][] maze;
    private int lines;
    private int blank;
    private int tam;
    private double[][] pop;
    private int tamPop = 101;
    private int gen = 0;
    private double[] mostFitted;
    Point sol;
    Point init;

    public void readFile(String file) {
        try (FileReader fr = new FileReader(new File(file)); BufferedReader buff = new BufferedReader(fr)) {
            int col = 0;
            int n = Integer.parseInt(buff.readLine());
            maze = new String[n][n];
            lines = n;
            while (col < n) {
                String linha = buff.readLine();
                for (int i = 0; i < n; i++) {
                    linha = linha.replace(" ", "");
                    if (linha.charAt(i) == '0') {
                        blank++;
                    }
                    if (linha.charAt(i) == 'S') {
                        sol = new Point(col, i);
                    }
                    if (linha.charAt(i) == 'E') {
                        init = new Point(col, i);
                    }
                    maze[col][i] = linha.charAt(i) + "";
                }
                col++;
            }
            tam = blank / 2;
            // tam = 10;
        } catch (IOException ex) {
            System.out.println("Não foi possível ler o arquivo");
        }
    }

    public void genss() {
        double currentMF = Double.MAX_VALUE;
        int posMostFitted =0;
        while (currentMF > 0 && gen < 1000) {
            currentMF = Double.MAX_VALUE;
            posMostFitted = 0;
            for (int i = 0; i < tamPop; i++) {
                int penalty = 0;
                boolean hit = false;
                Point currentMove = init;
                for (int j = 0; j < tam - 1; j++) {
                    if (!hit) {
                        int move = (int) pop[i][j];
                        switch (move) {
                            case (1):
                                if (currentMove.y - 1 < 0 || maze[currentMove.x][currentMove.y - 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x][currentMove.y - 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x, currentMove.y - 1);
                                } else {
                                    currentMove = new Point(currentMove.x, currentMove.y - 1);
                                }
                                break;
                            case (2):
                                if (currentMove.y + 1 >= lines || maze[currentMove.x][currentMove.y + 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x][currentMove.y + 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x, currentMove.y + 1);
                                } else {
                                    currentMove = new Point(currentMove.x, currentMove.y + 1);
                                }
                                break;
                            case (3):
                                if (currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x - 1][currentMove.y].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x - 1, currentMove.y);
                                } else {
                                    currentMove = new Point(currentMove.x - 1, currentMove.y);
                                }
                                break;
                            case (4):
                                if (currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x + 1][currentMove.y].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x + 1, currentMove.y);
                                } else {
                                    currentMove = new Point(currentMove.x + 1, currentMove.y);
                                }
                                break;
                            case (5):
                                if (currentMove.y + 1 >= lines || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y + 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x - 1][currentMove.y + 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                } else {
                                    currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                }
                                break;
                            case (6):
                                if (currentMove.y - 1 < 0 || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y - 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x - 1][currentMove.y - 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                } else {
                                    currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                }
                                break;
                            case (7):
                                if (currentMove.y - 1 < 0 || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y - 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x + 1][currentMove.y - 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                } else {
                                    currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                }
                                break;
                            case (8):
                                if (currentMove.y + 1 >= lines || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y + 1].equals("1")) {
                                    hit = true;
                                    penalty = tam - j;
                                } else if (maze[currentMove.x + 1][currentMove.y + 1].equals("S")) {
                                    hit = true;
                                    currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                                } else {
                                    currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }

                pop[i][tam - 1] = currentMove.distance(sol) + penalty;
                if (currentMove.distance(sol) + penalty < currentMF) {
                    currentMF = currentMove.distance(sol) + penalty;
                    posMostFitted = i;
                }
            }
            mostFitted = new double[tam];
            for (int i = 0; i < tam; i++) {
                mostFitted[i] = pop[posMostFitted][i];

            }
            if (gen % 10 == 0) {
                printPop();
            }
            if (currentMF == 0) {
            printFound(posMostFitted);
            System.out.println("Gene" + gen);
        }
            nextGen();
        }
      
    }

    public void nextGen() {
        gen++;
        // double oldpop[][] = Arrays.copyOf(pop, tamPop);
        double oldpop[][] = new double[tamPop][tam + 1];
        for (int i = 0; i < tamPop; i++) {
            for (int j = 0; j < tam; j++) {
                oldpop[i][j] = pop[i][j];
            }
        }
        pop = new double[tamPop][tam + 1];
        pop[0] = mostFitted;
        Random r = new Random();
        int mask = 1;
        for (int i = 1; i < tamPop; i += 2) {
            Integer[] s = new Integer[4];
            s = randomCrom().toArray(s);
            int r1 = s[0];
            int r2 = s[1];
            int r3 = s[2];
            int r4 = s[3];

            int father = oldpop[r1][tam - 1] < oldpop[r2][tam - 1] ? r1 : r2;
            int mother = oldpop[r3][tam - 1] < oldpop[r4][tam - 1] ? r3 : r4;
            for (int j = 0; j < tam - 1; j++) {
                //if(j%3 ==0) mask *=-1; 
                int sw = tam / 2;
                //if(j < sw) mask *=-1;
                if (j < sw) {
                    pop[i][j] = oldpop[father][j];
                    pop[i + 1][j] = oldpop[mother][j];
                } else {
                    pop[i][j] = oldpop[mother][j];
                    pop[i + 1][j] = oldpop[father][j];
                }
            }
        }
        int mut = 0;
        while (mut < tamPop) {
            int cromo = r.nextInt(tamPop);
            int gene = r.nextInt(tam - 1);
            int move = randomMove();
            pop[cromo][gene] = move;
            mut++;
        }

    }

    public void firstGen() {
        pop = new double[tamPop][tam + 1];
        for (int i = 0; i < tamPop; i++) {
            for (int j = 0; j < tam - 1; j++) {
                pop[i][j] = randomMove();
            }
            pop[i][tam] = 0;
        }
    }

    public Set<Integer> randomCrom() {
        Set<Integer> s = new HashSet<>(4);
        int cont = 0;
        Random r = new Random();
        while (cont < 4) {
            int i = r.nextInt(tamPop);
            if (!s.contains(i)) {
                s.add(i);
                cont++;
            }
        }
        return s;
    }

    public int randomMove() {
        Random r = new Random();
        return (r.nextInt(8) + 1);
    }

    public void printPop() {
        String msg = "";

        for (int i = 0; i < tamPop; i++) {
            msg += "Cromossomo " + i + " ";
            String caminho =  " Caminho: "+  "(" + init.x + "," + init.y + ")";
            Point currentMove = init;
            boolean hit = false;
            for (int j = 0; j < tam - 1; j++) {
                msg += (int) (pop[i][j]) + " ";
                int move = (int) pop[i][j];
                if (!hit) {
                    switch (move) {
                        case (1):
                            if (currentMove.y - 1 < 0 || maze[currentMove.x][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                                
                            } else {
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (2):
                            if (currentMove.y + 1 >= lines || maze[currentMove.x][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x][currentMove.y + 1].equals("S")) {
                                hit = true;
                               
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                                 caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (3):
                            if (currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y].equals("S")) {
                                hit = true;
                                
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (4):
                            if (currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (5):
                            if (currentMove.y + 1 >= lines || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y + 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (6):
                            if (currentMove.y - 1 < 0 || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (7):
                            if (currentMove.y - 1 < 0 || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (8):
                            if (currentMove.y + 1 >= lines || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y + 1].equals("S")) {
                                hit = true;
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                                currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            msg += "Aptidão " + pop[i][tam - 1] + caminho + "\n";

        }
        System.out.println(msg);
    }
    
    public void printFound(int i){
         String msg = "";

       
            msg += "Cromossomo " + i + " ";
            String caminho =  " Caminho: "+  "(" + init.x + "," + init.y + ")";
            Point currentMove = init;
            boolean hit = false;
            for (int j = 0; j < tam - 1; j++) {
                
                int move = (int) pop[i][j];
                if (!hit) {
                    switch (move) {
                        case (1):
                            if (currentMove.y - 1 < 0 || maze[currentMove.x][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                               // maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                                
                            } else {
                                currentMove = new Point(currentMove.x, currentMove.y - 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (2):
                            if (currentMove.y + 1 >= lines || maze[currentMove.x][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x][currentMove.y + 1].equals("S")) {
                                hit = true;
                               
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                               // maze[currentMove.x][currentMove.y] = "$";
                                 caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                
                                currentMove = new Point(currentMove.x, currentMove.y + 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (3):
                            if (currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y].equals("S")) {
                                hit = true;
                                
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                                //maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (4):
                            if (currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                                //maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (5):
                            if (currentMove.y + 1 >= lines || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y + 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                //maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y + 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (6):
                            if (currentMove.y - 1 < 0 || currentMove.x - 1 < 0 || maze[currentMove.x - 1][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x - 1][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                //maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x - 1, currentMove.y - 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (7):
                            if (currentMove.y - 1 < 0 || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y - 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y - 1].equals("S")) {
                                hit = true;
                                currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                //caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y - 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        case (8):
                            if (currentMove.y + 1 >= lines || currentMove.x + 1 >= lines || maze[currentMove.x + 1][currentMove.y + 1].equals("1")) {
                                hit = true;

                            } else if (maze[currentMove.x + 1][currentMove.y + 1].equals("S")) {
                                hit = true;                                
                                currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                                //maze[currentMove.x][currentMove.y] = "$";
                            } else {
                                currentMove = new Point(currentMove.x + 1, currentMove.y + 1);
                                maze[currentMove.x][currentMove.y] = "$";
                                caminho += "(" + currentMove.x + "," + currentMove.y + ")";
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            msg += caminho + "\n";

        
        System.out.println(msg);
    
    }

    public void printMaze() {
        String msg = "";

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < lines; j++) {
                msg += maze[i][j] + " ";
            }
            msg += "\n";
        }

        System.out.println(msg);
    }

    public static void main(String[] args) {
        Labirinto lab = new Labirinto();
        lab.readFile("labirinto4_20.txt");
        //lab.readFile(args[0]);
        //lab.printMaze();
        lab.firstGen();
        lab.genss();
        lab.printMaze();
    }

}
