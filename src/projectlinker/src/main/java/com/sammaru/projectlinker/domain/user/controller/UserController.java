package com.sammaru.projectlinker.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.sammaru.projectlinker.domain.user.payload.request.EditProfileRequest;
import com.sammaru.projectlinker.domain.user.payload.request.SignUpRequest;
import com.sammaru.projectlinker.domain.user.payload.request.VerifyEmailRequest;
import com.sammaru.projectlinker.domain.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/api/v1")
@RequiredArgsConstructor
@Validated
public class UserController {
//    private final MailService mailService;
    private final UserService userService;

    @GetMapping("/users/check")
    public ResponseEntity<Void> checkEmail(@RequestParam @Valid @NotNull String email) {
        userService.checkEmail(email);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/email/send")
//    public ResponseEntity<Void> sendMail(@RequestParam @Valid @NotNull String email) {
//        mailService.sendMail(email);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/email/verify")
//    public ResponseEntity<Void> verifyMail(@RequestBody @Valid VerifyEmailRequest request) {
//        mailService.verifyMail(request.email(), request.authCode());
//        return ResponseEntity.ok().build();
//    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/edit")
    public ResponseEntity<Void> editProfile(@RequestHeader("Authorization") String token
            , @RequestBody @Valid EditProfileRequest editProfileRequest) throws JsonProcessingException {

        userService.editProfile(token, editProfileRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resign")
    public ResponseEntity<Void> resignUser(
            @RequestHeader("Authorization") String token) throws JsonProcessingException {
        userService.resignUser(token);
        return ResponseEntity.ok().build();
    }

}
