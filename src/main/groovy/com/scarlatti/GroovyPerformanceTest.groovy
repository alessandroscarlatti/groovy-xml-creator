package com.scarlatti

import com.scarlatti.attempt1.XMLBuilder

import java.time.Duration

/**
 * Created by pc on 10/17/2017.
 */
class GroovyPerformanceTest {

    static void main(String[] args) {

        Long sum = 0L;

        for (int count = 0; count < 50; count++) {
            Long startTime = System.nanoTime();

            do10000XMLSWithGroovy()

            Long endTime = System.nanoTime();
            Long durationCount = endTime - startTime;
            Duration duration = Duration.ofNanos(durationCount);

            System.out.println("Groovy xml creation took " + duration.toString());

            sum += duration.toNanos()
        }

        sum = sum / 50
        Duration avg = Duration.ofNanos(sum);
        System.out.println("Groovy xml creation avg took " + avg.toString());

    }

    static void do10000XMLSWithGroovy() {

        XMLBuilder xmlBuilder = new XMLBuilder()

        for (int count = 0; count < 10000; count++) {
            xmlBuilder.xml {
                boolean shouldCreateElementX = false

                thing1 {
                    thing2 {
                        thing4  // TODO what happens if I define a metaclass property thing4, but then later use a metaclass method thing4?  Does that work?
                        thing4 { "qwer" }
                        thing5 {
                            for (int i = 0; i < 9; i++) {
                                if (shouldCreateElementX) {
                                    PcPullChecks {
                                        i % 3 == 0 ? "Y" : "N"
                                    }
                                } else {
                                    Y { "what do you know Y" }
                                    Z
                                    ZX { "asdf" }
                                    createElement("this") { "this is an element" }
                                }
                                shouldCreateElementX = !shouldCreateElementX
                            }

                        }
                    }
                }
            }.withPrettyPrint().toString()
        }
    }

}
