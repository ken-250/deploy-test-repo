package com.tabisketch.service.implement;

import com.tabisketch.bean.entity.MailAddressAuthToken;
import com.tabisketch.mapper.IMailAddressAuthTokensMapper;
import com.tabisketch.mapper.IUsersMapper;
import com.tabisketch.service.IAuthMailAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthMailAddressService implements IAuthMailAddressService {
    private final IMailAddressAuthTokensMapper mailAddressAuthTokensMapper;
    private final IUsersMapper usersMapper;

    public AuthMailAddressService(
            final IMailAddressAuthTokensMapper mailAddressAuthTokensMapper,
            final IUsersMapper usersMapper
    ) {
        this.mailAddressAuthTokensMapper = mailAddressAuthTokensMapper;
        this.usersMapper = usersMapper;
    }

    @Override
    @Transactional
    public boolean execute(final String token) {
        final var tokenUUID = UUID.fromString(token);
        final var mailAddressAuthToken = this.mailAddressAuthTokensMapper.selectByToken(tokenUUID);

        if (mailAddressAuthToken == null) return false;

        this.usersMapper.updateMailAddressVerified(mailAddressAuthToken.getUserId(), true);

        // メールアドレス編集の認証時はメールアドレスを更新
        if (isNotEmptyNewMailAddress(mailAddressAuthToken))
            this.usersMapper.updateMailAddress(mailAddressAuthToken.getUserId(), mailAddressAuthToken.getNewMailAddress());

        this.mailAddressAuthTokensMapper.deleteById(mailAddressAuthToken.getId());
        return true;
    }

    private boolean isNotEmptyNewMailAddress(final MailAddressAuthToken mailAddressAuthToken) {
        return mailAddressAuthToken.getNewMailAddress() != null &&
                !mailAddressAuthToken.getNewMailAddress().isEmpty();
    }
}
