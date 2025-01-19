package com.vacation.platform.stayfinder.user.service.serviceImpl;

import com.vacation.platform.stayfinder.common.ErrorType;
import com.vacation.platform.stayfinder.common.StayFinderException;
import com.vacation.platform.stayfinder.user.dto.UserDTO;
import com.vacation.platform.stayfinder.user.entity.User;
import com.vacation.platform.stayfinder.user.repository.UserRepository;
import com.vacation.platform.stayfinder.user.service.UserService;
import com.vacation.platform.stayfinder.util.Result;
import jakarta.transaction.Transactional;
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
    public void saveUser(User user) {

        List<User> nickNameList = userRepository.findByNickName(user.getNickName());

        if(!nickNameList.isEmpty()) {
            throw new StayFinderException(ErrorType.DUPLICATE_NICK_NAME);
        }

        // 응답 없애기

    }

    @Override
    public Result<String> modifyUser(UserDTO.saveDTO modifyDTO) {
        return null;
    }


}
