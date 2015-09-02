package de.ur.mi.kilroy.backend.interfaces;

import de.ur.mi.kilroy.backend.objects.Comment;
import de.ur.mi.kilroy.backend.objects.Post;

import java.util.List;

/**
 * Created by simon on 02/09/15.
 */
public interface Model {
//    int createPost(String body);
    Post createPost(String body);

//    int createComment(String body);
    Comment createComment(String body);

    List<Post> getAllPosts();

    List<Comment> getAllCommentsOn(String post_id);

    boolean existPost(String post_id);
}