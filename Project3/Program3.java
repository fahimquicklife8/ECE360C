//Name: Fahim Imtiaz
//EID: fmi89

public class Program3 {

    // DO NOT REMOVE OR MODIFY THESE VARIABLES (calculator and treatment_plan)
    ImpactCalculator calculator;    // the impact calculator
    int[] treatment_plan;           // array to store the treatment plan

    public Program3() {
        this.calculator = null;
    }

    /*
     * This method is used in lieu of a required constructor signature to initialize
     * your Program3. After calling a default (no-parameter) constructor, we
     * will use this method to initialize your Program3.
     *
     * DO NOT MODIFY THIS METHOD
     */
    public void initialize(ImpactCalculator ic) {
        this.calculator = ic;
        this.treatment_plan = new int[ic.getNumMedicines()];
    }

    /*
     * This method computes and returns the total impact of the treatment plan. It should
     * also fill in the treatment_plan array with the correct values.
     *
     * Each element of the treatment_plan array should contain the number of hours
     * that medicine i should be administered for. For example, if treatment_plan[2] = 5,
     * then medicine 2 should be administered for 5 hours.
     */
    public int computeImpact() {

        int totalTime = calculator.getTotalTime();
        int numMedicines = calculator.getNumMedicines();
        // Put your code here

        /*
        numMedicines = Integer.parseInt(split[0]);
        totalTime = Integer.parseInt(split[1]);
        function = new int[numMedicines][totalTime + 1];

        for(int i = 0; i < numMedicines; i++){
            function[i][0] = 0;
        }

        int i = 0;
        while(sc.hasNextLine()){
            nextLine = sc.nextLine();
            split = nextLine.split(" ");
            for(int j = 0; j < totalTime; j++){
                function[i][j + 1] = Integer.parseInt(split[j]);
            }
            i++;
        }
        */
       // float time;
       // int impact;

        int[][] dp = new int[numMedicines + 1][totalTime + 1];

        int i = 1;

        while (i <= numMedicines) {

            int j = 1;
            while(j <= totalTime) {
                dp[i][j] = dp[i - 1][j];

                for (int timeAllocated = 1; timeAllocated <= j; timeAllocated++) {
                    int impactWithCurrent = calculator.calculateImpact(i - 1, timeAllocated);

                    int impactWithoutCurrent = dp[i - 1][j - timeAllocated];

                    int impact = impactWithCurrent + impactWithoutCurrent;

                    if (impact > dp[i][j]) {
                        dp[i][j] = impact;
                    }
                }
                 j++;
            }
             i++;
        }

        int timeRemaining = totalTime;

           //int timeLeft = 0;

        for (int k = numMedicines; k > 0; k--) {

            int timeAllocated = 0;
//          while(timeLeft < timeAllocated){
//
//              if(dp
//              timeLeft++;
//          }
            while ( timeAllocated <= timeRemaining) {

                if (dp[k][timeRemaining] == dp[k - 1][timeRemaining - timeAllocated] + calculator.calculateImpact(k - 1, timeAllocated)) {
                    treatment_plan[k - 1] = timeAllocated;
                    //   treatment_plan[k - 1] = timeRemaining - timeAllocated ;
                    timeRemaining = timeRemaining-timeAllocated;
                    break;
                }
                 timeAllocated++;
            }
        }

        return dp[numMedicines][totalTime];

      //  return 0;

    }



    /*
     * This method prints the treatment plan.
     */
    public void printTreatmentPlan() {
        System.out.println("Please administer medicines 1 through n for the following amounts of time:\n");
        for(int i = 0; i < calculator.getNumMedicines(); i++){
            // retrieve the amount of hours for medicine i
            int hoursForI = treatment_plan[i];
            System.out.println("Medicine " + i + ": " + hoursForI);
        }
    }
}
