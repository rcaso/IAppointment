package com.shavatech.domain.management.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shavatech.domain.management.entity.BackEndCommand;
import io.quarkus.websockets.next.TextMessageCodec;
import jakarta.inject.Singleton;

import java.lang.reflect.Type;

@Singleton
public class CommandMessageCode implements TextMessageCodec<BackEndCommand> {

    /**
     * @param type the type to handle, must not be {@code null}
     * @return {@code true} if this codec can encode/decode the provided type, {@code false} otherwise
     */
    @Override
    public boolean supports(Type type) {
        return type.equals(BackEndCommand.class);
    }

    /**
     * @param value the value to encode, must not be {@code null}
     * @return the encoded representation of the value
     */
    @Override
    public String encode(BackEndCommand value) {
        String json = null;
        ObjectMapper om = new ObjectMapper();
        // support Java 8 date time apis
        om.registerModule(new JavaTimeModule());
        try {
            // covert Java object to JSON strings
            json = om.writeValueAsString(value);
            // output: {"name":"mkyong","age":42}
            System.out.println(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * @param type  the type of the object to decode, must not be {@code null}
     * @param value the value to decode, must not be {@code null}
     * @return the decoded representation of the value
     */
    @Override
    public BackEndCommand decode(Type type, String value) {
        ObjectMapper om = new ObjectMapper();
        // support Java 8 date time apis
        om.registerModule(new JavaTimeModule());
        BackEndCommand command = null;
        try {
            // covert JSON to Java object
            command = om.readValue(value, BackEndCommand.class);
            // output: Person{name='mkyong', age=20}
            System.out.println(command);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return command;
    }
}
