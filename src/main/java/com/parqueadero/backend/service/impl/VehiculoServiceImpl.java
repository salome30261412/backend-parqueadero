package com.parqueadero.backend.service.impl;

import com.parqueadero.backend.dto.VehiculoDTO;
import com.parqueadero.backend.entity.Vehiculo;
import com.parqueadero.backend.repository.VehiculoRepository;
import com.parqueadero.backend.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    private static final int COSTO_POR_MINUTO = 100;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Simulación de datos persistentes para mensualidades y tarifas
    private final Map<String, Double> tarifas = new HashMap<>();
    private final List<String> clientesMensualidad = new ArrayList<>();

    public VehiculoServiceImpl() {
        // Inicialización de tarifas por tipo de vehículo
        tarifas.put("carro", 2000.0);
        tarifas.put("moto", 1000.0);

        // Clientes de mensualidad simulados
        clientesMensualidad.add("Juan Pérez - ABC123");
        clientesMensualidad.add("Ana Torres - XYZ789");
    }

    @Override
    public VehiculoDTO registrarIngreso(VehiculoDTO dto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(dto.getPlaca());
        vehiculo.setTipo(dto.getTipo());
        vehiculo.setHora_ingreso(LocalDateTime.now().format(FORMATTER));

        vehiculoRepository.save(vehiculo);

        return toDTO(vehiculo);
    }

    @Override
    public Map<String, Object> registrarSalida(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findById(placa).orElse(null);

        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }

        LocalDateTime horaSalida = LocalDateTime.now();
        LocalDateTime horaIngreso = LocalDateTime.parse(vehiculo.getHora_ingreso().toString(), FORMATTER);
        Duration duracion = Duration.between(horaIngreso, horaSalida);
        long minutos = duracion.toMinutes();
        long costo = minutos * COSTO_POR_MINUTO;

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("placa", vehiculo.getPlaca());
        resultado.put("tipo", vehiculo.getTipo());
        resultado.put("horaIngreso", vehiculo.getHora_ingreso());
        resultado.put("horaSalida", horaSalida.format(FORMATTER));
        resultado.put("minutos", minutos);
        resultado.put("costo", costo);

        vehiculoRepository.deleteById(placa);

        return resultado;
    }

    @Override
    public List<VehiculoDTO> obtenerVehiculosEnParqueadero() {
        return vehiculoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculoDTO> listarVehiculos() {
        return obtenerVehiculosEnParqueadero(); // mismo resultado por ahora
    }

    @Override
    public VehiculoDTO buscarVehiculo(String placa) {
        return vehiculoRepository.findById(placa)
                .map(this::toDTO)
                .orElse(null);
    }

    @Override
    public String generarFactura(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findById(placa).orElse(null);
        if (vehiculo == null) {
            return "Vehículo no encontrado";
        }

        LocalDateTime horaSalida = LocalDateTime.now();
        LocalDateTime horaIngreso = LocalDateTime.parse(vehiculo.getHora_ingreso(), FORMATTER);
        Duration duracion = Duration.between(horaIngreso, horaSalida);
        long minutos = duracion.toMinutes();
        long costo = minutos * COSTO_POR_MINUTO;

        return String.format("Factura\nPlaca: %s\nTipo: %s\nTiempo: %d minutos\nCosto: $%d",
                placa, vehiculo.getTipo(), minutos, costo);
    }

    @Override
    public Map<String, Double> obtenerTarifas() {
        return tarifas;
    }

    @Override
    public String obtenerClientesMensualidad() {
        return String.join(", ", clientesMensualidad);
    }

    @Override
    public void agregarClienteMensualidad(Map<String, String> clienteInfo) {
        String nombre = clienteInfo.get("nombre");
        String placa = clienteInfo.get("placa");

        if (nombre != null && placa != null) {
            clientesMensualidad.add(nombre + " - " + placa);
        } else {
            throw new RuntimeException("Datos de cliente incompletos");
        }
    }

    private VehiculoDTO toDTO(Vehiculo vehiculo) {
        VehiculoDTO dto = new VehiculoDTO();
        dto.setPlaca(vehiculo.getPlaca());
        dto.setTipo(vehiculo.getTipo());
        dto.setHoraIngreso(vehiculo.getHora_ingreso().toString());
        return dto;
    }
}
