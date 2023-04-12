package com.hugsy.socialmedia.model;

import com.hugsy.customorm.annotation.Column;
import com.hugsy.customorm.annotation.Id;
import com.hugsy.customorm.annotation.ManyToOne;
import com.hugsy.customorm.annotation.Table;

@Table(name = "post_tbl")
public class Post {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "caption", nullable = false, length = 500)
    private String caption;
    @Column(name = "media_url", nullable = false)
    private String mediaUrl;
    @ManyToOne(targetTable = "user_tbl", targetEntity = User.class, referenceColumn = "id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
