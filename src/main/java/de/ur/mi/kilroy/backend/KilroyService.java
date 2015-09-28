package de.ur.mi.kilroy.backend;

import com.google.gson.Gson;
import de.ur.mi.kilroy.backend.interfaces.Model;
import de.ur.mi.kilroy.backend.objects.Comment;
import de.ur.mi.kilroy.backend.objects.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

// Database service.
public class KilroyService implements Model {

    private Sql2o sql2o;

    public KilroyService(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

//    Create new post.
    public Post createPost(String body) {
        String sql = "insert into posts(title, content, publishing_date, lat, lng, nfc_id) VALUES (:title, :content, :date, :lat, :lng, :nfc_id)";

//        Parsing request body with gson.
        Post post = new Gson().fromJson(body, Post.class);

//        Set today.
        post.setPublishing_date(new Date());

//        Connect and execute query to database.
        Connection conn = sql2o.open();
        try {
            int post_id = conn.createQuery(sql, true)
                    .addParameter("title", post.getTitle())
                    .addParameter("content", post.getContent())
                    .addParameter("date", post.getPublishing_date())
                    .addParameter("lat", BigDecimal.class, post.getLat())
                    .addParameter("lng", BigDecimal.class, post.getLng())
                    .addParameter("nfc_id", post.getNfc_id())
                    .executeUpdate()
                    .getKey(int.class);
//            Execute successfully, add id and return Post.
            post.setId(post_id);
            return post;
        } finally {
            conn.close();
        }
    }

//    Create a comment.
    public Comment createComment(String body) {
        String sql = "insert into comments(post_id, author, content, submission_date) VALUES (:post_id, :author, :content, :date)";

//        Parsing request body with gson.
        Comment comment = new Gson().fromJson(body, Comment.class);

//        Set today.
        comment.setSubmission_date(new Date());

//        Connect and execute query to database.
        Connection conn = sql2o.open();
        try {
            int comment_id = conn.createQuery(sql, true)
                    .addParameter("post_id", comment.getPost_id())
                    .addParameter("author", comment.getAuthor())
                    .addParameter("content", comment.getContent())
                    .addParameter("date", comment.getSubmission_date())
                    .executeUpdate()
                    .getKey(int.class);
//            Execute successfully, add id and return Comment.
            comment.setId(comment_id);
            return comment;
        } finally {
            conn.close();
        }
    }

//    Get all posts.
    public List<Post> getAllPosts() {
        String sql = "select * from posts";
        Connection conn = sql2o.open();
        try {
            List<Post> posts = conn.createQuery(sql)
                    .executeAndFetch(Post.class);
//            Set comments and return post list.
            for (Post post : posts) {
                post.setComments(getAllCommentsOn("" + post.getId()));
            }
            return posts;
        } finally {
            conn.close();
        }
    }

//    Get all comments from post wiht post_id.
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

//    Check post.
    public boolean existPost(String post_id) {
        String sql = "select * from posts where id=:post_id";
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

//    Get post with uuid.
    public Post getPostWithUuid(String uuid) {
        String sql = "select * from posts where nfc_id=:nfc_id";
        Connection conn = sql2o.open();
        try {
            List<Post> posts = conn.createQuery(sql)
                    .addParameter("nfc_id", uuid)
                    .executeAndFetch(Post.class);
//            Set comments and return post. If query failed, return null.
            for (Post post : posts) {
                post.setComments(getAllCommentsOn("" + post.getId()));
                return post;
            }
            return null;
        } finally {
            conn.close();
        }
    }

//    Get post with id.
    public Post getPost(String post_id) {
        String sql = "select * from posts where id=:post_id";
        Connection conn = sql2o.open();
        try {
            List<Post> posts = conn.createQuery(sql)
                    .addParameter("post_id", post_id)
                    .executeAndFetch(Post.class);
            return posts.size() > 0 ? posts.get(0) : null;
        } finally {
            conn.close();
        }
    }
}
