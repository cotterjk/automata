// automata.java runs an elementary cellular automata in the terminal
// Created by Cotter Koopman

import java.util.Scanner;
public class automata {

    public static void main(String[] args) {
        System.out.println("\nStarting automata...\n");
        int width = 65;
        Scanner reader = new Scanner(System.in);
        System.out.println("What 'rule' would you like to use (integer 0-255): ");
        int decrule = reader.nextInt();
        // Convert to binary
        int binrule = Integer.valueOf(Integer.toBinaryString(decrule));
        int binruleprint = binrule;
        // Initialize array to be overwritten by user input rule
        boolean[] rules = {false, true, true, false, false, true, false, true};
        for (int i=0; i<rules.length; i++) {
            if (binrule % 10 == 1) {
                rules[rules.length-(i+1)] = true;
            } else {
                rules[rules.length-(i+1)] = false;
            }
            binrule = binrule / 10;
        }

        System.out.println("That translates to " + binruleprint + " or :");
        printRules(rules);

        System.out.println("How many generations would you like to see?: ");
        int gens = reader.nextInt();

        boolean[] line = new boolean[width];
        for (int i=0; i<line.length; i++) {
            line[i] = false;
        }
        line[line.length/2] = true; //start with line ...00100...
        printCells(line);
        // Itearting step, generates each successive generation
        for (int i=0; i<gens; i++) {
            try {
                // Some delay for experience of procedural generations
                Thread.sleep(300);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            line = nextGen(line, rules);
            printCells(line);
        }
    }

    // Function for determining next generation from rules
    public static boolean[] nextGen(boolean[] line, boolean[] rules) {
        boolean[] nextLine = new boolean[line.length];
        for (int i=0; i<line.length; i++) {
            nextLine[i] = cellRule(i, line, rules);
        }
        return nextLine;
    }

    // Generation for determine life status of cell based on it and its neighbors' past state
    public static boolean cellRule(int pos, boolean[] line, boolean[] rules) {
        boolean l = false;
        boolean c = line[pos];
        boolean r = false;
        try {
            l = line[pos-1];
        } catch (ArrayIndexOutOfBoundsException e) {
            l = c;
        }
        try {
            r = line[pos+1];
        } catch (ArrayIndexOutOfBoundsException e) {
            r = c;
        }
        if (l) {
            if (c) {
                if (r) { return rules[0];} // 111
                else { return rules[1];} // 110
            } else {
                if (r) { return rules[2];} // 101
                else { return rules[3];} // 100
            }
        } else {
            if (c) {
                if (r) { return rules[4];} // 011
                else { return rules[5];} // 010
            } else {
                if (r) { return rules[6];} // 001
                else { return rules[7];} // 000
            }
        }
    }

    // IO functions
    public static void printRules(boolean[] rules) {
        System.out.println("⬛️ ⬛️ ⬛️   ⬛️ ⬛️ ⬜️   ⬛️ ⬜️ ⬛️   ⬛️ ⬜️ ⬜️   ⬜️ ⬛️ ⬛️   ⬜️ ⬛️ ⬜️   ⬜️ ⬜️ ⬛️   ⬜️ ⬜️ ⬜️ ");
        String rulesLine = "";
        for (int i=0; i<rules.length; i++) {
            if (rules[i]) {rulesLine += "▫️ ⬛️ ▫️   ";}
            else {rulesLine += "▫️ ⬜️ ▫️   ";}
        }
        System.out.println(rulesLine + "\n\n");
    }

    public static void printCells(boolean[] line) {
        String lineString = "";
        for (int i=0; i<line.length; i++) {
            if (line[i]) {lineString += "⬛️ ";}
            else {lineString += "⬜️ ";}
        }
        System.out.println(lineString);
    }

}
