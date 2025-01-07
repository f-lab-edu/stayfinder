package com.vacation.platform.stayfinder.user.service.serviceImpl;

import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.ResponseCode;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Result<String> saveUser(User user) {

        List<User> nickNameList = userRepository.findByNickName(user.getNickName());

        if(!nickNameList.isEmpty()) {
            return Result.fail(ResponseCode.SUCCESS, "요청하신 닉네임은 사용중입니다.", user.getNickName());
        }




        return null;
    }

    @Override
    public Result<String> modifyUser(UserDTO.saveDTO modifyDTO) {
        return null;
    }


}
