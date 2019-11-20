package kz.stegano.med.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntegratorJsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();;
    private static ObjectWriter json = mapper.writerWithDefaultPrettyPrinter();

    public static String toJson(Object object) throws JsonProcessingException {
        return json.writeValueAsString(object);
    }
}
