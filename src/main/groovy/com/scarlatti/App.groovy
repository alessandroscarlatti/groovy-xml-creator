package com.scarlatti

import groovy.util.logging.Slf4j

/**
 * Created by pc on 9/8/2017.
 */
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@Slf4j
class App implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(App.class, args)
    }

    @Override
    void run(String... args) throws Exception {

    }

    void codeFromTheOtherDay() {

        def sw = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(sw)
        xml.langs(type:"current", count:3, mainstream:true){
            language(flavor:"static", version:"1.5", "Java")
            language(flavor:"dynamic", version:"1.6.0", "Groovy")
            language(flavor:"dynamic", version:"1.9", "JavaScript")
            language "Java"
            qwerasdf {
                language {
                    (1..4).each {
                        language("asdf")
                    }
                }
            }
        }

        println sw
    }
}