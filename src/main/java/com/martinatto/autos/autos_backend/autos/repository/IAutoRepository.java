package com.martinatto.autos.autos_backend.autos.repository;

import com.martinatto.autos.autos_backend.autos.entity.Auto;
import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoRodado;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAutoRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCase (String marca, String modelo);

    List<Auto> findByEstado(EstadoAuto estadoAuto);

    List<Auto> findByMarcaContainingIgnoreCase(String marca);

    List<Auto> findByCombustible(TipoCombustible combustible);

    List<Auto> findByTransmision(TipoTransmision transmision);

    List<Auto> findByTipoRodado(TipoRodado tipoRodado);

    Long countByEstado(EstadoAuto estadoAuto);
}
