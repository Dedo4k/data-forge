package dev.vlxd.dataforge.scylla.model.configuration;

import dev.vlxd.dataforge.scylla.model.mapping.Annotation;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JaxbConfiguration {

    private final String contextPath = "dev.vlxd.dataforge.scylla.model.mapping";

    @Bean
    public Marshaller jaxbMarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Annotation.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return marshaller;
    }

    @Bean
    public Unmarshaller jaxbUnmarshaller() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Annotation.class);

        return context.createUnmarshaller();
    }
}
