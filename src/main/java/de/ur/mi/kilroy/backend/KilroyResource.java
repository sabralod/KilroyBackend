package de.ur.mi.kilroy.backend;

import de.ur.mi.kilroy.backend.helper.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by simon on 02/09/15.
 */
public class KilroyResource {
    private static final String API_CONTEXT = "/api/";
    private final KilroyService kilroyService;

    public KilroyResource(KilroyService service) {
        this.kilroyService = service;
        setupEndpoints();

    }

    private void setupEndpoints() {

        post(API_CONTEXT + "/posts", "application/json", (request, response)
                        -> kilroyService.createPost(request.body()), new JsonTransformer()
        );

        get(API_CONTEXT + "/posts", "application/json", (request, response)
                        -> kilroyService.getAllPosts(), new JsonTransformer()
        );

        get(API_CONTEXT + "/post/:uuid", "application/json", (request, response)
                        -> kilroyService.getPostWithUuid(request.params(":uuid")), new JsonTransformer()
        );

        get(API_CONTEXT + "/posts/:id/comments", "application/json", (request, response)
                        -> kilroyService.getAllCommentsOn(request.params(":id")), new JsonTransformer()
        );

        post(API_CONTEXT + "/posts/:id/comments", "application/json", (request, response)
                        -> kilroyService.createComment(request.body()), new JsonTransformer()
        );
    }
}
