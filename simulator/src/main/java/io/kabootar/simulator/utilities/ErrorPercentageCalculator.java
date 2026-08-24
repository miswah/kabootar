package io.kabootar.simulator.utilities;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ErrorPercentageCalculator {

    private static final Random random  = new Random();

    public static boolean shouldFail(double errorPercentage){
        if (errorPercentage <= 0) {
            return false;
        }

        if (errorPercentage >= 100) {
            return true;
        }

        return random.nextDouble() * 100 < errorPercentage;
    }
}
