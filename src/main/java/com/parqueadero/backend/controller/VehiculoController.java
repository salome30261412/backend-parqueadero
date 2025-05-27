package com.parqueadero.backend.controller;

import com.parqueadero.backend.dto.VehiculoDTO;
import com.parqueadero.backend.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping("/ingreso")
    public ResponseEntity<VehiculoDTO> registrarIngreso(@RequestBody VehiculoDTO dto) {
        return ResponseEntity.ok(vehiculoService.registrarIngreso(dto));
    }

    @DeleteMapping("/salida/{placa}")
    public ResponseEntity<Map<String, Object>> registrarSalida(@PathVariable String placa) {
        return ResponseEntity.ok(vehiculoService.registrarSalida(placa));
    }

    @GetMapping("/parqueadero")
    public ResponseEntity<List<VehiculoDTO>> obtenerVehiculosEnParqueadero() {
        return ResponseEntity.ok(vehiculoService.obtenerVehiculosEnParqueadero());
    }

    @GetMapping("/parqueadero/{placa}")
    public ResponseEntity<VehiculoDTO> buscarVehiculo(@PathVariable String placa) {
        return ResponseEntity.ok(vehiculoService.buscarVehiculo(placa));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<VehiculoDTO>> listarTodosLosVehiculos() {
        return ResponseEntity.ok(vehiculoService.listarVehiculos());
    }

    @PostMapping("/generarFactura")
    public ResponseEntity<String> generarFactura(@RequestBody String placa) {
        return ResponseEntity.ok(vehiculoService.generarFactura(placa));
    }

    @GetMapping("/tarifas")
    public ResponseEntity<Map<String, Double>> obtenerTarifas() {
        return ResponseEntity.ok(vehiculoService.obtenerTarifas());
    }

    @GetMapping("/clientes/mensualidad")
    public ResponseEntity<String> obtenerClientesMensualidad() {
        return ResponseEntity.ok(vehiculoService.obtenerClientesMensualidad());
    }

    @PostMapping("/clientes/mensualidad")
    public ResponseEntity<Void> agregarClienteMensualidad(@RequestBody Map<String, String> clienteInfo) {
        vehiculoService.agregarClienteMensualidad(clienteInfo);
        return ResponseEntity.ok().build();
    }
}
