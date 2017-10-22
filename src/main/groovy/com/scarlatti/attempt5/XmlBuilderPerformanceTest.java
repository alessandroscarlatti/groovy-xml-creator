package com.scarlatti.attempt5;

import java.time.Duration;

/**
 * Created by pc on 10/21/2017.
 */
public class XmlBuilderPerformanceTest {
    public static void main(String[] args) throws Exception {

        Long sum = 0L;

        Demo demo = new Demo();

        for (int count = 0; count < 50; count++) {
            Long startTime = System.nanoTime();

            demo.do10000Xmls();

            Long endTime = System.nanoTime();
            Long durationCount = endTime - startTime;
            Duration duration = Duration.ofNanos(durationCount);

            System.out.println("Groovy xml creation took " + duration.toString());

            sum += duration.toNanos();
        }

        sum = sum / 50;
        Duration avg = Duration.ofNanos(sum);
        System.out.println("Groovy xml creation avg took " + avg.toString());

    }
}
