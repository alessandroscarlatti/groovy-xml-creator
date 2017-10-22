package com.scarlatti.attempt4;

import java.time.Duration;

/**
 * Created by pc on 10/17/2017.
 */
class GroovyMarkupBuilderPerformanceTest {

    public static void main(String[] args) {

        Long sum = 0L;

        Do10000XMLSWithGroovyMarkupBuilder do10000XMLSWithGroovyMarkupBuilder = new Do10000XMLSWithGroovyMarkupBuilder();

        for (int count = 0; count < 50; count++) {
            Long startTime = System.nanoTime();

            do10000XMLSWithGroovyMarkupBuilder.doXMLs();

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
