package de.ur.mi.kilroy.backend.helper;

import com.google.gson.Gson;
import spark.Response;
import spark.ResponseTransformer;

import java.util.HashMap;

/**
 * Created by simon on 02/09/15.
 */
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new Gson();

    public String render(Object model) throws Exception {
        if (model instanceof Response) {
            return gson.toJson(new HashMap<>());
        }
        return gson.toJson(model);
    }
}
