package com.fuinco.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "book_review")
public class BookReview {
    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private int userId;
    @Indexed(direction = IndexDirection.ASCENDING)
    private int bookId;
    private int rating;
    private String reviewText;
    private Date reviewDate;

}
