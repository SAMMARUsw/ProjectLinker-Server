package com.sammaru.projectlinker.domain.user.service;

import com.sammaru.projectlinker.domain.user.domain.User;
import com.sammaru.projectlinker.domain.user.domain.UserSkill;
import com.sammaru.projectlinker.domain.user.exception.exceptions.DuplicateEmailException;
import com.sammaru.projectlinker.domain.user.payload.request.EditProfileRequest;
import com.sammaru.projectlinker.domain.user.payload.request.SignUpRequest;
import com.sammaru.projectlinker.domain.user.repository.UserRepository;
import com.sammaru.projectlinker.domain.user.repository.UserSkillRepository;
import com.sammaru.projectlinker.global.component.token.TokenResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenResolver tokenResolver;

    public void checkEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("Email already exists");
        }
    }

    public void signUp(SignUpRequest request) {
        checkEmail(request.email());
        User user = User.signUp(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.phnum(),
                request.university(),
                request.major(),
                request.interest()
        );
        userRepository.save(user);

        List<String> skillNames = request.skills();
        for (String skillName : skillNames) {
            UserSkill userSkill = UserSkill.create(skillName);
            userSkillRepository.save(userSkill);
            user.getUserSkills().add(userSkill);
        }
    }

    public void editProfile(String token, EditProfileRequest request){
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(tokenResolver.getAccessClaims(token))
                .orElseThrow();

        targetUser.editProfile(
                request.nickname(),
                request.phnum()
        );
    }

    public void resignUser(String token) {
        Long userId = tokenResolver.getAccessClaims(token);
        User targetUser = userRepository.findByUserIdAndIsDeletedIsFalse(userId)
                .orElseThrow(() -> new DuplicateEmailException("Email Not exists"));
        targetUser.resignUser();
    }

}
