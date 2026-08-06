package com.martinatto.autos.autos_backend.dashboard.service;

import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.repository.IAutoRepository;
import com.martinatto.autos.autos_backend.clientes.repository.IClientesRepository;
import com.martinatto.autos.autos_backend.dashboard.dto.DashboardResponseDTO;
import com.martinatto.autos.autos_backend.dashboard.dto.VentaMensualDTO;
import com.martinatto.autos.autos_backend.pagos.repository.IPagoRepository;
import com.martinatto.autos.autos_backend.solicitudes.enums.EstadoSolicitud;
import com.martinatto.autos.autos_backend.solicitudes.repository.ISolicitudAutoRepository;
import com.martinatto.autos.autos_backend.ventas.entity.Venta;
import com.martinatto.autos.autos_backend.ventas.enums.EstadoVenta;
import com.martinatto.autos.autos_backend.ventas.repository.IVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService implements IDashboardService{

    private final IAutoRepository autoRepo;
    private final IClientesRepository clienteRepo;
    private final IVentaRepository ventaRepo;
    private final IPagoRepository pagoRepo;
    private final ISolicitudAutoRepository solicitudRepo;

    @Override
    @Transactional(readOnly = true)
    public DashboardResponseDTO obtenerResumen() {

        BigDecimal totalVendido = ventaRepo.sumarPrecioFinalPorEstado(EstadoVenta.FINALIZADA);

        BigDecimal totalPagado = pagoRepo.sumarTodosLosPagos();

        return DashboardResponseDTO.builder()
                .totalAutos(autoRepo.count())
                .autosDisponibles(autoRepo.countByEstado(EstadoAuto.DISPONIBLE))
                .autosVendidos(autoRepo.countByEstado(EstadoAuto.VENDIDO))
                .totalClientes(clienteRepo.count())
                .totalVentas(ventaRepo.count())
                .ventasFinalizadas(ventaRepo.countByEstado(EstadoVenta.FINALIZADA))
                .totalVendido(
                        totalVendido != null
                                ? totalVendido
                                : BigDecimal.ZERO
                )
                .totalPagado(
                        totalPagado != null
                                ? totalPagado
                                : BigDecimal.ZERO
                )
                .solicitudesPendientes(
                        solicitudRepo.countByEstadoSolicitud(
                                EstadoSolicitud.PENDIENTE
                        )
                )
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<VentaMensualDTO> obtenerVentasMensuales() {
        List<Venta> ventasFinalizadas = ventaRepo.findByEstadoOrderByFechaAsc(EstadoVenta.FINALIZADA);

        Map<YearMonth, VentaMensualDTO> estadisticas = new LinkedHashMap<>();

        for (Venta venta : ventasFinalizadas) {
            if (venta.getFecha() == null) {
                continue;
            }

            YearMonth periodo = YearMonth.from(venta.getFecha());

            VentaMensualDTO estadistica = estadisticas.computeIfAbsent(
                            periodo,
                            key -> VentaMensualDTO.builder()
                                    .anio(key.getYear())
                                    .mes(key.getMonthValue())
                                    .cantidadVentas(0L)
                                    .totalVendido(BigDecimal.ZERO)
                                    .build()
            );

            estadistica.setCantidadVentas(
                    estadistica.getCantidadVentas() + 1
            );

            estadistica.setTotalVendido(
                    estadistica.getTotalVendido()
                            .add(venta.getPrecioFinal())
            );
        }

        return estadisticas.values()
                .stream()
                .toList();
    }
}
