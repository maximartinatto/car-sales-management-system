package com.martinatto.autos.autos_backend.autos.service;

import com.martinatto.autos.autos_backend.autos.dto.AutoRequestDTO;
import com.martinatto.autos.autos_backend.autos.dto.AutoResponseDTO;
import com.martinatto.autos.autos_backend.autos.entity.Auto;
import com.martinatto.autos.autos_backend.autos.enums.EstadoAuto;
import com.martinatto.autos.autos_backend.autos.enums.TipoCombustible;
import com.martinatto.autos.autos_backend.autos.enums.TipoTransmision;
import com.martinatto.autos.autos_backend.autos.mapper.AutoMapper;
import com.martinatto.autos.autos_backend.autos.repository.IAutoRepository;
import com.martinatto.autos.autos_backend.shared.service.FileStorageService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutoService implements IAutoService {

    private final IAutoRepository autoRepo;
    private final FileStorageService fileStorageService;

    @Override
    public AutoResponseDTO crearAuto(
            String marca,
            String modelo,
            Integer anio,
            String color,
            Integer kilometraje,
            BigDecimal precio,
            EstadoAuto estado,
            TipoTransmision transmision,
            TipoCombustible combustible,
            MultipartFile imagen
    ) {

        // Por ahora solamente comprobamos que la imagen llegue
        String imagenUrl = fileStorageService.guardarImagen(imagen);

        Auto auto = new Auto();

        auto.setMarca(marca);
        auto.setModelo(modelo);
        auto.setAnio(anio);
        auto.setColor(color);
        auto.setKilometraje(kilometraje);
        auto.setPrecio(precio);
        auto.setEstado(estado);
        auto.setTransmision(transmision);
        auto.setCombustible(combustible);
        auto.setImagenUrl(imagenUrl);

        Auto autoGuardado = autoRepo.save(auto);

        return AutoMapper.toDTO(autoGuardado);
    }

    @Override
    public List<AutoResponseDTO> obtenerTodos() {
        return autoRepo.findAll()
                .stream()
                .map(AutoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AutoResponseDTO obtenerAutoPorId(Long id) {
        Auto auto = autoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        return AutoMapper.toDTO(auto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoResponseDTO> buscarAutos(String texto) {
        return autoRepo
                .findByMarcaContainingIgnoreCaseOrModeloContainingIgnoreCase(
                        texto,
                        texto
                )
                .stream()
                .map(AutoMapper::toDTO)
                .toList();
    }

    @Override
    public AutoResponseDTO actualizarAuto(Long id, AutoRequestDTO request) {
        Auto auto = autoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado"));

        auto.setMarca(request.getMarca());
        auto.setModelo(request.getModelo());
        auto.setAnio(request.getAnio());
        auto.setPrecio(request.getPrecio());
        auto.setEstado(request.getEstado());
        auto.setCombustible(request.getCombustible());
        auto.setTransmision(request.getTransmision());
        auto.setImagenUrl(request.getImagenUrl());

        Auto actualizado = autoRepo.save(auto);

        return AutoMapper.toDTO(actualizado);
    }

    @Override
    public void eliminarAuto(Long id) {
        if (!autoRepo.existsById(id)) {
            throw new RuntimeException("Auto no encontrado");
        }

        autoRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoResponseDTO> obtenerPorEstado(EstadoAuto estadoAuto) {
        return autoRepo.findByEstado(estadoAuto)
                .stream()
                .map(AutoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoResponseDTO> obtenerPorMarca(String marca) {
        return autoRepo.findByMarcaContainingIgnoreCase(marca)
                .stream()
                .map(AutoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoResponseDTO> obtenerPorCombustible(TipoCombustible combustible) {
        return autoRepo.findByCombustible(combustible)
                .stream()
                .map(AutoMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AutoResponseDTO> obtenerPorTransmision(TipoTransmision transmision) {
        return autoRepo.findByTransmision(transmision)
                .stream()
                .map(AutoMapper::toDTO)
                .toList();
    }
}
