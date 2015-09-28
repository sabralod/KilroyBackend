package de.ur.mi.kilroy.backend;

import de.ur.mi.kilroy.backend.helper.JsonTransformer;

import static spark.Spark.get;
import static spark.Spark.post;

// Setup post / get commands.

public class KilroyResource {
    private static final String API_CONTEXT = "/api/";
    private final KilroyService kilroyService;

    public KilroyResource(KilroyService service) {
        this.kilroyService = service;
        setupEndpoints();

    }

    private void setupEndpoints() {

//        Create new post.
//        Request must contain title, content, lat, lng, nfc_id. Id is auto increment.
//        Response the created PostItem.
        post(API_CONTEXT + "/posts", "application/json", (request, response)
                        -> kilroyService.createPost(request.body()), new JsonTransformer()
        );

//        Get all PostItems.
//        Response List of PostItem.
        get(API_CONTEXT + "/posts", "application/json", (request, response)
                        -> kilroyService.getAllPosts(), new JsonTransformer()
        );

//        Get PostItem with uuid.
//        Request params must contain uuid.
//        Response Postitem.
        get(API_CONTEXT + "/post/uuid/:uuid", "application/json", (request, response)
                        -> kilroyService.getPostWithUuid(request.params(":uuid")), new JsonTransformer()
        );

//        Get PostItem with id.
//        Request params must contain id.
//        Response PostItem.
        get(API_CONTEXT + "/post/id/:id", "application/json", (request, response)
                        -> kilroyService.getPost(request.params(":id")), new JsonTransformer()
        );

//        Get all comments of post with id.
//        Request params must contain id.
//        Response List of CommentItem.
        get(API_CONTEXT + "/posts/id/:id/comments", "application/json", (request, response)
                        -> kilroyService.getAllCommentsOn(request.params(":id")), new JsonTransformer()
        );

//        Create new comment.
//        Request must contain post_id, author, content.
//        Response CommentItem.
        post(API_CONTEXT + "/posts/id/:id/comments", "application/json", (request, response)
                        -> kilroyService.createComment(request.body()), new JsonTransformer()
        );
    }
}
