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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("success into loadUser");
        System.out.println("result.getUsername(): " + username);

        Optional<UserAccount> userAccountList = userAccountDAO.findByAccount(username);
        if(userAccountList.isPresent()) {
            UserAccount result = userAccountList.get();

            System.out.println("result.getAccount(): " + result.getAccount());
            System.out.println("result.getPassword(): " + result.getPassword());
            if(result.getAccount().equals("10746010")) {
                System.out.println("User: ADMIN");
                return User.builder()
                        .username(result.getAccount())
                        .password(result.getPassword())
                        .disabled(false)
                        .accountExpired(false)
                        .accountLocked(false)
                        .authorities("ADMIN")
                        .build();
            } else{
                return User.builder()
                        .username(result.getAccount())
                        .password(result.getPassword())
                        .disabled(false)
                        .accountExpired(false)
                        .accountLocked(false)
                        .authorities("USER")
                        .build();
            }

        } else {
            throw new UsernameNotFoundException("請檢察帳號密碼是否錯誤");
        }
    }
}
