package com.parqueadero.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @Column(name = "placa")
    private String placa;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "hora_ingreso")
    private String hora_ingreso;

    public Vehiculo() {}

    // Getter y setter de placa
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    // Getter y setter de tipo
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Getter y setter de horaIngreso
    public String getHora_ingreso() {
        return hora_ingreso;
    }

    public void setHora_ingreso(String horaIngreso) {
        this.hora_ingreso = horaIngreso;
    }
}



