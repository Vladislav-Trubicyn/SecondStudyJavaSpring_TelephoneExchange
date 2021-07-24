package ru.telephoneexchange.repository;

import org.springframework.data.repository.CrudRepository;
import ru.telephoneexchange.model.Service;

public interface ServiceRepository extends CrudRepository<Service, Long>
{

}
