package ru.telephoneexchange.repository;

import org.springframework.data.repository.CrudRepository;
import ru.telephoneexchange.model.Service;
import ru.telephoneexchange.model.User;

public interface ServiceRepository extends CrudRepository<Service, Long>
{

}
