package com.martinatto.autos.autos_backend.clientes.repository;

import com.martinatto.autos.autos_backend.clientes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientesRepository extends JpaRepository<Cliente, Long> {
}
