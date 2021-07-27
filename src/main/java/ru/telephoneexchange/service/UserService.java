package ru.telephoneexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.UserRepository;

@Service
public class UserService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return (UserDetails) userRepository.findByUsername(username);
    }

    public Iterable<User> findAllUsers()
    {
        return userRepository.findAll();
    }

    public User findByUsername(String userName)
    {
        return userRepository.findByUsername(userName);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public void userAddService(User user, ru.telephoneexchange.model.Service service)
    {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb.getServices().size() > 0)
        {
            for (ru.telephoneexchange.model.Service tempService : user.getServices())
            {
                if (tempService.getId() != service.getId())
                {
                    userFromDb.getServices().add(service);
                    userRepository.save(userFromDb);
                }
            }
        }
        else
        {
            userFromDb.getServices().add(service);
            userRepository.save(userFromDb);
        }

        user.getServices().clear();
        user.setServices(userFromDb.getServices());
    }

}
