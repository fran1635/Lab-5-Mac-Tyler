import java.util.Arrays;

public class matching_problem {
    public static void main(String[] args) {
        stableMatching(new int[][]{{2, 0, 1}, // programmer 0
                                   {2, 1, 0}, // programmer 1
                                   {2, 0, 1}}, // programmer 2
                       new int[][]{{1, 0, 2}, // company 0
                                   {2, 0, 1}, // company 1
                                   {0, 2, 1}}); // company 2
    }

    static public void stableMatching(int[][] programmerPrefs, int[][] companyPrefs) {
        int N = programmerPrefs[0].length;
        int[] companyMatch = new int[N]; // companyMatch[c] = programmer matched to c
        Arrays.fill(companyMatch, -1);

        boolean[] freeProg = new boolean[N];
        Arrays.fill(freeProg, true);

        int[] nextProposal = new int[N]; // which company each programmer will propose to next

        int freeCount = N;
        while (freeCount > 0) {
            int p;
            for (p = 0; p < N; p++)
                if (freeProg[p])
                    break;

            int c = programmerPrefs[p][nextProposal[p]];
            nextProposal[p]++;

            if (companyMatch[c] == -1) {
                companyMatch[c] = p;
                freeProg[p] = false;
                freeCount--;
            } else {
                int current = companyMatch[c];
                if (prefers(companyPrefs[c], p, current)) {
                    companyMatch[c] = p;
                    freeProg[p] = false;
                    freeProg[current] = true;
                }
            }
        }

        // Print results
        for (int c = 0; c < N; c++) {
            System.out.println("Company " + c + " matched with Programmer " + companyMatch[c]);
        }
    }

    static boolean prefers(int[] companyPref, int newProg, int currentProg) {
        for (int p : companyPref) {
            if (p == newProg)
                return true;
            if (p == currentProg)
                return false;
        }
        return false; // shouldn't happen if prefs are valid
    }

}