package de.ur.mi.kilroy.backend.interfaces;

import de.ur.mi.kilroy.backend.objects.Comment;
import de.ur.mi.kilroy.backend.objects.Post;

import java.util.List;

public interface Model {
    Post createPost(String body);

    Comment createComment(String body);

    List<Post> getAllPosts();

    List<Comment> getAllCommentsOn(String post_id);

    boolean existPost(String post_id);

    Post getPostWithUuid(String uuid);
}