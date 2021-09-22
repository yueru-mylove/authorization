package com.miracle.authorization.api;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * Created at 2021/9/22 16:00
 **/
@AllArgsConstructor
public class UserTokenAuthenticationSupplier implements AuthenticationSupplier {

    private AuthenticationManager defaultAuthenticationManager;
    private UserTokenManager userTokenManager;

    private Map<String, ThirdPartyAuthenticationManager> thirdPartyAuthenticationManagers = new HashMap<>();

    @Autowired(required = false)
    public void setThirdPartyAuthenticationManagers(List<ThirdPartyAuthenticationManager> thirdPartyAuthenticationManagerList) {
        for (ThirdPartyAuthenticationManager manager : thirdPartyAuthenticationManagerList) {
            this.thirdPartyAuthenticationManagers.put(manager.getTokenType(), manager);
        }
    }



    @Override
    public Optional<Authentication> get(String userId) {
        if (userId == null) {
            return Optional.empty();
        }

        return get(this.defaultAuthenticationManager, userId);
    }

    private Optional<Authentication> get(AuthenticationManager authenticationManager, String userId) {
        if (null == userId) {
            return Optional.empty();
        }

        if (null == authenticationManager) {
            authenticationManager = this.defaultAuthenticationManager;
        }

        return authenticationManager.getByUserId(userId);
    }


    protected Optional<Authentication> get(ThirdPartyAuthenticationManager authenticationManager, String userId) {
        if (null == userId) {
            return Optional.empty();
        }

        if (null == authenticationManager) {
            return this.defaultAuthenticationManager.getByUserId(userId);
        }

        return authenticationManager.getByUserId(userId);
    }



    @Override
    public Optional<Authentication> get() {
        return ContextUtils.currentContext()
                           .get(ContextKey.of(ParsedToken.class))
                           .map(t -> userTokenManager.getByToken(t.getToken()))
                           .map(tokenMono -> tokenMono
                                   .map(token -> get(thirdPartyAuthenticationManagers.get(token.getType()), token.getUserId()))
                                   .flatMap(Mono::justOrEmpty))
                           .flatMap(Mono::blockOptional);
    }
}
