
import java.util.Scanner;

/*
Simple Jacobson Karels algorithm,
 It estimates API latency based on sample metrics and the retry timeout

*/


public class Jacobson {

    // list of samples used for the simulation
    private static float samples[] = {600, 600, 600, 600, 600, 600, 600, 100, 100, 100, 100, 100, 100, 100, 100, 100, 600, 600, 600, 600, 600};


    // 10ms is a good start for a typical service call
    private static float estimatedRTT = 10;
    private static float deviation = 1;

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        float timeoutVal = estimatedRTT + 4 * deviation;


        for (int i = 0; i < samples.length; i++) {
            float sample = samples[i];

            timeoutVal = getNewTimeoutVal(sample);
            System.out.println("sample:" + sample + " -> estimateRTT:" + estimatedRTT + "ms maxTimeout:" + timeoutVal + "ms      d:" + deviation);

        }

        while (true) {

            System.out.print("Next API sample timeout (in ms): ");
            float sample = scan.nextFloat();


            timeoutVal = getNewTimeoutVal(sample);
            System.out.println("sample:" + sample + " -> estimateRTT:" + estimatedRTT + "ms maxTimeout:" + timeoutVal + "ms      d:" + deviation);
        }
    }

    public static float getNewTimeoutVal(float sampleRTT) {

        float difference = sampleRTT - estimatedRTT;
        estimatedRTT = estimatedRTT + (difference / 8F);

        // issue: the the formula below negatively reacts to abrupt changes.
        // for instance, 600ms for a while, the timeout raises to 500ms,
        //   then say the service responds at 100ms..
        // you would expect the timeout to shrink, right? well it doesn't ! it will go up to 700ms before coming down
        //	deviation = deviation + (Math.abs(difference) - deviation)/8;

        // this formula is mutch better
        deviation = Math.abs(deviation + (difference - deviation) / 8F);
        return estimatedRTT + 4 * deviation;
    }
}
