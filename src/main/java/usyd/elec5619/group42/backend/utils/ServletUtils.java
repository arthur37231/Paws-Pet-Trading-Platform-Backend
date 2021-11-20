package usyd.elec5619.group42.backend.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import usyd.elec5619.group42.backend.exception.MissingParameterException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletUtils {
    public static void jsonToAttribute(HttpServletRequest request) throws MissingParameterException {
        try {
            String requestData = request.getReader().lines().collect(Collectors.joining());
            Map<String, Object> map = new ObjectMapper()
                    .readValue(requestData, new TypeReference<>() {});

            for(String param : map.keySet()) {
                request.setAttribute(param, map.get(param));
            }
        } catch (MismatchedInputException e) {
            throw new MissingParameterException();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void checkContainsAttributes(HttpServletRequest request, String... params) throws MissingParameterException {
        for(String param : params) {
            if(request.getAttribute(param) == null) {
                throw new MissingParameterException(param);
            }
        }
    }
}
