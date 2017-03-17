
import java.util.Scanner;

/*
Simple Jacobson Karels algorithm,
 It estimates API latency based on sample metrics and the retry timeout

*/


public class Jacobson {

    // list of samples used for the simulation
    private static float samples[] = {600, 600, 100, 100, 100, 100, 600, 600, 600};


    // 10ms is a good start for a typical service call
    private static float estimatedRTT = 10;
    private static float deviation = 1;

    // This parameter controls how agressively we ramp up to changes
    private static final float X=.25F;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        float timeoutVal = estimatedRTT + (X * deviation);

        for (int i = 0; i<samples.length; i++) {

            float sample = samples[i];

            for(int p = 0; p<5; p++) {   
                timeoutVal = getNewTimeoutVal(sample);
                System.out.println("sample:" + sample + " -> estimateRTT:" + (int)estimatedRTT + "ms maxTimeout:" + (int)timeoutVal + "ms      d:" + deviation);
            }
        }

        while (true) {

            System.out.print("Next API sample timeout (in ms): ");
            float sample = scan.nextFloat();

            timeoutVal = getNewTimeoutVal(sample);
            System.out.println("sample:" + sample + " -> estimateRTT:" + (int)estimatedRTT + "ms maxTimeout:" + (int)timeoutVal + "ms      d:" + deviation);
        }
    }

    public static float getNewTimeoutVal(float sampleRTT) {

        float difference = sampleRTT - estimatedRTT;
        estimatedRTT = estimatedRTT + (X*difference);
       	deviation = deviation + (X*(Math.abs(difference) - deviation));
        return estimatedRTT + (X * deviation);
    }
}
