package ru.telephoneexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.telephoneexchange.repository.ServiceRepository;

@Service
public class ServiceService
{
    @Autowired
    private ServiceRepository serviceRepository;

    public Iterable<ru.telephoneexchange.model.Service> findAllServices()
    {
        return serviceRepository.findAll();
    }

    public void addServiceAdmin(ru.telephoneexchange.model.Service service)
    {
        serviceRepository.save(service);
    }

    public void saveEditServiceAdmin(ru.telephoneexchange.model.Service service)
    {
        serviceRepository.save(service);
    }

    public void deleteServiceAdmin(ru.telephoneexchange.model.Service service)
    {
        serviceRepository.delete(service);
    }
}
