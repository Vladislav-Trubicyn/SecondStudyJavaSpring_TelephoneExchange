package ru.telephoneexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.telephoneexchange.model.User;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername (String username);
}
