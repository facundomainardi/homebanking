package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;


public interface ClientService {

    List<ClientDTO> getClients();

    void saveClient(Client client);

    Client findClientById(long id);

    ClientDTO getClientDTO(long id);

    Client findClientByEmail(String email);

    ClientDTO getCurrent(String email);

}