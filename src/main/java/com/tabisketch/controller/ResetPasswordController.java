package com.tabisketch.controller;

import com.tabisketch.bean.entity.PasswordResetToken;
import com.tabisketch.mapper.IPasswordResetTokensMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/password-reset/{token}")
public class ResetPasswordController {
    private final IPasswordResetTokensMapper passwordResetTokensMapper;

    public ResetPasswordController(IPasswordResetTokensMapper passwordResetTokensMapper) {
        this.passwordResetTokensMapper = passwordResetTokensMapper;
    }

    @GetMapping
    public String get(@PathVariable final String token) {
        final UUID tokenUUID = UUID.fromString(token);

        System.out.println(passwordResetTokensMapper.selectByToken(tokenUUID));

        return "password-reset/index";
    }
}
