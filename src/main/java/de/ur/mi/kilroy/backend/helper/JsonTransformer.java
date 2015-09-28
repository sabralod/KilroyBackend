package de.ur.mi.kilroy.backend.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import spark.Response;
import spark.ResponseTransformer;

import java.util.ArrayList;

// Generating Response with gson.
public class JsonTransformer implements ResponseTransformer {

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

//    Generate response from object.
    public String render(Object model) throws Exception {
        if (model instanceof Response) {
            return gson.toJson(new ArrayList<>());
        }
        return gson.toJson(model);
    }
}
