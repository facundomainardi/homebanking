package com.mindhub.homebanking.repositories;
import java.util.List;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByLastName(String lastName);
    Client findByEmail(String email);
}
