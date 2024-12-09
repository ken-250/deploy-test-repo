package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MailAddressAuthToken;
import com.tabisketch.bean.entity.User;
import com.tabisketch.bean.form.RegisterForm;
import com.tabisketch.service.IEncryptPasswordService;
import com.tabisketch.valueobject.Mail;
import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IRegisterService;
import com.tabisketch.service.ISendMailService;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService implements IRegisterService {
    private final IUsersMapper usersMapper;
    private final IEncryptPasswordService encryptPasswordService;
    private final IMailAddressAuthTokensMapper mailAuthenticationTokensMapper;
    private final ISendMailService sendMailService;

    public RegisterService(
            final IUsersMapper usersMapper,
            final IEncryptPasswordService encryptPasswordService,
            final IMailAddressAuthTokensMapper mailAuthenticationTokensMapper,
            final ISendMailService sendMailService
    ) {
        this.usersMapper = usersMapper;
        this.encryptPasswordService = encryptPasswordService;
        this.mailAuthenticationTokensMapper = mailAuthenticationTokensMapper;
        this.sendMailService = sendMailService;
    }

    @Override
    @Transactional
    public void execute(final RegisterForm registerForm) throws MessagingException {
        final var user = registerForm.toUser();
        final var encryptedPassword = this.encryptPasswordService.execute(user.getPassword());
        final var encryptedUser = User.generate(user.getMailAddress(), encryptedPassword);
        this.usersMapper.insert(encryptedUser);

        final var mailAuthToken = MailAddressAuthToken.generate(user.getId());
        this.mailAuthenticationTokensMapper.insert(mailAuthToken);

        final var mail = Mail.generateRegisterMail(user.getMailAddress(), mailAuthToken.getToken());
        this.sendMailService.execute(mail);
    }
}
