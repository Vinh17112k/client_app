package com.app.service.client.repository;

import com.app.service.client.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c.* from comment c where c.is_deleted = 0 and c.status = 1 and c.product_id =:productId and c.reply_comment_id is null ", nativeQuery = true)
    List<Comment> getAllOwnComment(@Param("productId") Long productId);
    @Query(value = "select c.reply_comment_id from comment c where c.is_deleted = 0 and c.status = 1 and c.product_id =:productId and c.reply_comment_id is not null ", nativeQuery = true)
    List<Long> getIdChildComment(@Param("productId") Long productId);
    @Query(value = "with recursive result  as ( " +
            "select    m.* from comment m where m.comment_id =:commentId " +
            " union all " +
            " select     cm.*  " +
            " from       comment cm" +
            " inner join result on cm.reply_comment_id = result.comment_id " +
            " ) select * from result where result.comment_id !=:commentId ", nativeQuery = true)
    List<Comment> getAllChildComment(@Param("commentId") Long commentId);
    @Query(value = "select c.product_id from comment c where c.is_deleted = 0 and c.status = 1", nativeQuery = true)
    List<Long> getProductIds();
    @Query(value = "select c.star from comment c where c.is_deleted = 0 and c.status = 1 and c.product_id =:productId and c.star is not null", nativeQuery = true)
    List<Double> starProduct(@Param("productId") Long productId);
    @Query(value = "select count(*) from ( with recursive result  as ( " +
            "select    m.* from comment m where m.comment_id =:commentId " +
            " union all " +
            " select     cm.*  " +
            " from       comment cm" +
            " inner join result on cm.reply_comment_id = result.comment_id " +
            " ) select * from result where result.comment_id !=:commentId ) a", nativeQuery = true)
    Integer totalComment(@Param("commentId") Long commentId);

}