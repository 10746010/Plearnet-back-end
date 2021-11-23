package tw.edu.ntub.imd.plearnet.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.edu.ntub.imd.plearnet.databaseconfig.dao.UserAccountDAO;
import tw.edu.ntub.imd.plearnet.databaseconfig.entity.UserAccount;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserAccountDAO userAccountDAO;

    @Autowired
    public UserDetailsServiceImpl(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        System.out.println("success into loadUser");
        System.out.println("result.getUsername() " + account);

        Optional<UserAccount> userAccountList = userAccountDAO.findByAccount(account);
        UserAccount result = userAccountList.orElseThrow(() -> new UsernameNotFoundException("帳號或密碼錯誤"));
        System.out.println("result.getUsername() " + result.getAccount());
        System.out.println("result.getPassword() " + result.getPassword());

        return User.builder()
                .username(result.getAccount())
                .password(result.getPassword())
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .authorities("ADMIN")
                .build();
    }
}
