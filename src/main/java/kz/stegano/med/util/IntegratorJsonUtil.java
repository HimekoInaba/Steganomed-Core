package kz.stegano.med.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegratorJsonUtil {
    private static final String CLASS;
    private static final Logger LOG;
    private static ObjectMapper mapper;
    private static ObjectWriter json;

    static {
        CLASS = IntegratorJsonUtil.class.getName();
        LOG = LoggerFactory.getLogger(CLASS);
        mapper = new ObjectMapper();
        json = mapper.writerWithDefaultPrettyPrinter();
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return json.writeValueAsString(object);
    }

}
