package com.ssafy.api.service;

import com.ssafy.api.request.UserCheckPostReq;
import com.ssafy.db.entity.Mask;
import com.ssafy.db.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserRepositorySupport userRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public User createUser(UserRegisterPostReq userRegisterInfo) {
		User user = new User();
		user.setEmail(userRegisterInfo.getEmail());
		user.setNickname(userRegisterInfo.getNickname());
		Mask mask = new Mask();
		Role role = new Role();
		mask.setId(1L);
		role.setId(1L);
		user.setMask(mask);
		user.setRole(role);

		// 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
		user.setPassword(passwordEncoder.encode(userRegisterInfo.getPassword()));
		return userRepository.save(user);
	}

	@Override
	public User getUserByEmail(String userEmail) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		User user = userRepositorySupport.findByEmail(userEmail).get();
		return user;
	}

	@Override
	public User getUserByNickname(String userNickname) {

		User user = userRepositorySupport.findByNickname(userNickname).get();
		return user;
	}

	@Override
	public User consultantRegister(Long userId) {

		User user = userRepositorySupport.findById(userId).get();
		return user;
	}


}
