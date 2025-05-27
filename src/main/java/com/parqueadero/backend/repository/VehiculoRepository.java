package com.parqueadero.backend.repository;

import com.parqueadero.backend.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
    // Si necesitas métodos personalizados, los puedes declarar aquí
}
