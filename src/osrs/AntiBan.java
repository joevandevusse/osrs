package osrs;

import java.util.Random;

public class AntiBan {

    public int randomizeWait(int low, int high) {
        Random r = new Random();
        int randomMed = r.nextInt(high - low) + low;
        return randomMed;
    }
}
