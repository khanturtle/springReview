package com.review.springreview.signup.entity;

import com.review.springreview.signup.dto.PostRequestDto;
import com.review.springreview.signup.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column(name = "createdAt", updatable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column(name = "modifiedAt")
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "nickname")
    private User user;

    public Post(PostRequestDto postRequestDto, UserDetailsImpl userDetails) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
        this.user = userDetails.getUser();
    }
}
