package com.scarlatti.attempt1;

import java.time.Duration;

/**
 * Created by pc on 10/17/2017.
 */
class GroovyPerformanceTest {

    public static void main(String[] args) {

        Long sum = 0L;

        Do10000XMLSWithGroovy do10000XMLSWithGroovy = new Do10000XMLSWithGroovy();

        for (int count = 0; count < 50; count++) {
            Long startTime = System.nanoTime();

            do10000XMLSWithGroovy.do10000XMLSWithGroovy();

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
