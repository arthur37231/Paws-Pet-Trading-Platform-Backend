package usyd.elec5619.group42.backend.utils;

import org.springframework.lang.NonNull;
import usyd.elec5619.group42.backend.exception.BaseException;

import java.util.HashMap;

public class ResponseBody extends HashMap<String, Object> {
    @NonNull
    public ResponseBody put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public ResponseBody() {
        super();
    }

    public ResponseBody(BaseException e) {
        super();
        this.put("code", e.getCode());
        this.put("message", e.getMessage());
    }
}
