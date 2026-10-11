package com.martinatto.autos.autos_backend.autos.service;


import com.martinatto.autos.autos_backend.autos.dto.AutoResponseDTO;
import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.autos.enums.TipoRodado;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

public interface IAutoService {
    // crear auto
    AutoResponseDTO crearAuto(
            String marca,
            String modelo,
            Integer anio,
            String color,
            Integer kilometraje,
            BigDecimal precio,
            TipoRodado tipoRodado,
            EstadoAuto estado,
            TipoTransmision transmision,
            TipoCombustible combustible,
            MultipartFile imagen
    );

    // Obtener todos los autos
    List<AutoResponseDTO> obtenerTodos();

    // Obtener un auto
    AutoResponseDTO obtenerAutoPorId(Long id);

    // Buscar auto
    List<AutoResponseDTO> buscarAutos(String texto);

    // Actualizar auto
    AutoResponseDTO actualizarAuto(
            Long id,
            String marca,
            String modelo,
            Integer anio,
            String color,
            Integer kilometraje,
            BigDecimal precio,
            TipoRodado tipoRodado,
            EstadoAuto estado,
            TipoTransmision transmision,
            TipoCombustible combustible,
            MultipartFile imagen
    );

    // Eliminar auto
    void eliminarAuto(Long id);

    // Obtener auto por estado
    List<AutoResponseDTO> obtenerPorEstado(EstadoAuto estadoAuto);

    // Obtener auto por marca
    List<AutoResponseDTO> obtenerPorMarca(String marca);

    // Obtener auto por combustible
    List<AutoResponseDTO> obtenerPorCombustible(TipoCombustible combustible);

    // Obtener auto por transmision
    List<AutoResponseDTO> obtenerPorTransmision(TipoTransmision transmision);
    // Obtener vehiculo por tipo de rodado
    List<AutoResponseDTO> obtenerPorTipoRodado(TipoRodado tipoRodado);

}
