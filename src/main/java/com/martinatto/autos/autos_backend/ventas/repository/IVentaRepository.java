package com.martinatto.autos.autos_backend.ventas.repository;

import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);

    boolean existsByClienteId(Long clienteId);

    List<Venta> findByEstado(EstadoVenta estadoVenta);

    List<Venta> findByFechaBetween(
            LocalDateTime fechaDesde,
            LocalDateTime fechaHasta
    );

    Long countByEstado(EstadoVenta estadoVenta);

    List<Venta> findByEstadoOrderByFechaAsc(EstadoVenta estadoVenta);

    @Query("""
       SELECT COALESCE(SUM(v.precioFinal), 0)
       FROM Venta v
       WHERE v.estado = :estado
       """)
    BigDecimal sumarPrecioFinalPorEstado(@Param("estado") EstadoVenta estadoVenta);
}
