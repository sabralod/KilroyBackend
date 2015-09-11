package de.ur.mi.kilroy.backend;

import com.google.gson.Gson;
import de.ur.mi.kilroy.backend.objects.Post;
import de.ur.mi.kilroy.backend.interfaces.Model;
import de.ur.mi.kilroy.backend.objects.Comment;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by simon on 02/09/15.
 */
public class KilroyService implements Model {

    private Sql2o sql2o;

    public KilroyService(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Post createPost(String body) {
        String sql = "insert into posts(title, content, publishing_date) VALUES (:title, :content, :date)";

        Post post = new Gson().fromJson(body, Post.class);
        post.setPublishing_date(new Date());

        Connection conn = sql2o.open();
        try {
            int post_id = conn.createQuery(sql, true)
                    .addParameter("title", post.getTitle())
                    .addParameter("content", post.getContent())
                    .addParameter("date", post.getPublishing_date())
                    .executeUpdate()
                    .getKey(int.class);
            post.setId(post_id);
            return post;
        } finally {
            conn.close();
        }
    }

    public Comment createComment(String body) {
        String sql = "insert into comments(post_id, author, content, submission_date) VALUES (:post_id, :author, :content, :date)";

        Comment comment = new Gson().fromJson(body, Comment.class);
        comment.setSubmission_date(new Date());


        Connection conn = sql2o.open();
        try {
            int comment_id = conn.createQuery(sql, true)
                    .addParameter("post_id", comment.getPost_id())
                    .addParameter("author", comment.getAuthor())
                    .addParameter("content", comment.getContent())
                    .addParameter("date", comment.getSubmission_date())
                    .executeUpdate()
                    .getKey(int.class);
            comment.setId(comment_id);
            return comment;
        } finally {
            conn.close();
        }
    }

    public List<Post> getAllPosts() {
        String sql = "select * from posts";
        Connection conn = sql2o.open();
        try {
            List<Post> posts = conn.createQuery(sql)
                    .executeAndFetch(Post.class);
            for (Post post : posts) {
                post.setComments(getAllCommentsOn("" + post.getId()));
            }
            return posts;
        } finally {
            conn.close();
        }
    }

    public List<Comment> getAllCommentsOn(String post_id) {
        String sql = "select * from comments where post_id=:post_id";
        Connection conn = sql2o.open();
        try {
            return conn.createQuery(sql)
                    .addParameter("post_id", post_id)
                    .executeAndFetch(Comment.class);
        } finally {
            conn.close();
        }
    }

    public boolean existPost(String post_id) {
        String sql = "select * from posts where post_id=:post_id";
        Connection conn = sql2o.open();
        try {
            List<Post> posts = conn.createQuery(sql)
                    .addParameter("post_id", post_id)
                    .executeAndFetch(Post.class);
            return posts.size() > 0;
        } finally {
            conn.close();
        }
    }
}
