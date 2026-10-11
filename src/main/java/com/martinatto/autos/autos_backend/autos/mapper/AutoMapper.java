package com.martinatto.autos.autos_backend.autos.mapper;

import com.martinatto.autos.autos_backend.autos.dto.AutoRequestDTO;
import com.martinatto.autos.autos_backend.autos.dto.AutoResponseDTO;
import com.martinatto.autos.autos_backend.autos.entity.Auto;

public class AutoMapper {

    public static Auto toEntity(AutoRequestDTO dto) {
        Auto auto = new Auto();

        auto.setMarca(dto.getMarca());
        auto.setModelo(dto.getModelo());
        auto.setAnio(dto.getAnio());
        auto.setColor(dto.getColor());
        auto.setKilometraje(dto.getKilometraje());
        auto.setPrecio(dto.getPrecio());
        auto.setTipoRodado(dto.getTipoRodado());
        auto.setEstado(dto.getEstado());
        auto.setCombustible(dto.getCombustible());
        auto.setTransmision(dto.getTransmision());
        auto.setImagenUrl(dto.getImagenUrl());
        return auto;
    }

    public static AutoResponseDTO toDTO(Auto auto) {
        AutoResponseDTO dto = new AutoResponseDTO();

        dto.setId(auto.getId());
        dto.setMarca(auto.getMarca());
        dto.setModelo(auto.getModelo());
        dto.setAnio(auto.getAnio());
        dto.setColor(auto.getColor());
        dto.setKilometraje(auto.getKilometraje());
        dto.setPrecio(auto.getPrecio());
        dto.setTipoRodado(auto.getTipoRodado());
        dto.setEstado(auto.getEstado());
        dto.setCombustible(auto.getCombustible());
        dto.setTransmision(auto.getTransmision());
        dto.setImagenUrl(auto.getImagenUrl());

        return dto;
    }
}