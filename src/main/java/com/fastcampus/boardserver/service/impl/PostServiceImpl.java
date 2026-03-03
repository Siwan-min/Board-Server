package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;


    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memeberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memeberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memeberInfo != null) {
            postMapper.register(postDTO);
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 게시글 수정 메서드 확인 필요" + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        if(postDTO!=null && postDTO.getId() != 0) {
            postMapper.updatePosts(postDTO);
        } else {
            log.error("updatePosts ERROR! {}", postDTO);
            throw new RuntimeException("updatePosts ERROR! " + postDTO);
        }
    }

    @Override
    public void deletePost(int userId, int postId) {
        if (userId != 0 && postId != 0) {
            postMapper.deletePosts(postId);
        } else {
            log.error("deletePost ERROR! {}", postId);
            throw new RuntimeException("deletePost ERROR! " + postId);
        }
    }
}
