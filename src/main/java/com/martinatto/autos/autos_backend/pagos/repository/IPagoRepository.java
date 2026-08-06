package com.martinatto.autos.autos_backend.pagos.repository;

import com.martinatto.autos.autos_backend.pagos.entity.Pago;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IPagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByVentaId(Long ventaId);

    @Query("""
           SELECT COALESCE(SUM(v.precioFinal), 0)
           FROM Venta v
           WHERE v.estado = :estado
           """)
    BigDecimal sumarPrecioFinalPorEstado(@Param("estado")EstadoVenta estadoVenta);

    @Query("""
       SELECT COALESCE(SUM(p.monto), 0)
       FROM Pago p
       """)
    BigDecimal sumarTodosLosPagos();
}
