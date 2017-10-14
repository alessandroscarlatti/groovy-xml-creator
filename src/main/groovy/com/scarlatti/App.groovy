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

    XMLCreatorGroovy xmlCreator = new XMLCreatorGroovy()

    static void main(String[] args) {
        SpringApplication.run(App.class, args)
    }

    @Override
    void run(String... args) throws Exception {
        runGroovy()
    }

    void runGroovy() {

    }

    String simpleXmlString(Closure<Void> c) {
        c.setDelegate(xmlCreator)
//        c.setResolveStrategy(Closure.DELEGATE_FIRST)
        c()
        return xmlCreator.build()
    }

    void addXMLTagNames(XMLCreatorGroovy xmlCreatorGroovy, List<String> names) {
        for (String name : names) {

            xmlCreatorGroovy.class.metaClass."${name}" <<
                    { Closure<?> nestedElementClosure -> xmlCreatorGroovy.element(name, nestedElementClosure)
                    } << { -> xmlCreatorGroovy.element(name) }
        }
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

//        String createXML = simpleXmlString {
//            Root {
//                Envelope {
//                    (1..4).each {
//                        Document {"asdf"}
//                        Document2 {"asdf"}
//                    }
//                }
//            }
//        }
//
//        println createXML
    }
}