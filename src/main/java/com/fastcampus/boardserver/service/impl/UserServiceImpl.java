package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicatedIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptSHA256;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;

    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userProfile) {
        boolean duplIdResult = isDuplicatedId(userProfile.getUserId());
        if (duplIdResult) {
            throw new DuplicatedIdException("중복된 아이디입니다.");
        }
        userProfile.setCreateTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));
        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1) {
            log.error("insertMeppaer Error {}", userProfile);
            throw new RuntimeException(
                    "insertUser Error !!! 회원가입 메서드를 확인해주세요!\n" + "param : " + userProfile
            );

        }
    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicatedId(String id) {
        return userProfileMapper.idCheck(id) == 1;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {
        String cryptPassword = encryptSHA256(beforePassword);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(encryptSHA256(afterPassword));
            int insertCount = userProfileMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePassword Error {}", memberInfo);
            throw new RuntimeException("update Error 비밀번호가 일치하지 않습니다 !!!");
        }


    }

    @Override
    public void deleteId(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO memberInfo = userProfileMapper.findByIdAndPassword(id, cryptPassword);
        if (memberInfo != null) {
            int insertCount = userProfileMapper.deleteUserProfile(id);
        } else {
            log.error("deleteId Error {}", memberInfo);
            throw new RuntimeException("비밀번호가 일치하지 않습니다. ");
        }
    }
}
