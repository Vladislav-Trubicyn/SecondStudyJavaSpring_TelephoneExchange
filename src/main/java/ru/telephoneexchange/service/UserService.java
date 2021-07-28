package ru.telephoneexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.telephoneexchange.model.User;
import ru.telephoneexchange.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

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

    public void userAddService(String userName, ru.telephoneexchange.model.Service service)
    {
        User userFromDb = userRepository.findByUsername(userName);

        if(userFromDb.getServices().size() > 0)
        {
            if(!userFromDb.getServices().contains(service))
            {
                userFromDb.setMoney(userFromDb.getMoney() - service.getPrice());
                userFromDb.getServices().add(service);
                userRepository.save(userFromDb);
            }
        }
        else
        {
            userFromDb.setMoney(userFromDb.getMoney() - service.getPrice());
            userFromDb.getServices().add(service);
            userRepository.save(userFromDb);
        }
    }

    public Set<ru.telephoneexchange.model.Service> userRemoveService(String userName, ru.telephoneexchange.model.Service service)
    {
        User userFromDb = userRepository.findByUsername(userName);

        Set<ru.telephoneexchange.model.Service> tempSet = new HashSet<ru.telephoneexchange.model.Service>(userFromDb.getServices());

        userFromDb.getServices().clear();

        for(ru.telephoneexchange.model.Service tempService : tempSet)
        {
            if(tempService.getId() != service.getId())
            {
                userFromDb.getServices().add(tempService);
            }
            else
            {
                userFromDb.setMoney(userFromDb.getMoney() + service.getPrice());
            }
        }

        userRepository.save(userFromDb);

        return userFromDb.getServices();
    }

}
