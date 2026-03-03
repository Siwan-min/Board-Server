package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.PostDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyPosts(int accountId);

    void updatePosts(PostDTO postDTO);
    void deletePost(int userId, int postId);
}

