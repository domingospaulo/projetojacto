package com.jacto.agendamento.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
public class CoordenadasDTO {

    @NotNull(message = "X (longitude) é obrigatório")
    private Double x; // longitude

    @NotNull(message = "Y (latitude) é obrigatório")
    private Double y; // latitude

    // Converte as coordenadas para Point usando um método
    public Point toPoint(org.locationtech.jts.geom.GeometryFactory factory) {
        return factory.createPoint(new org.locationtech.jts.geom.Coordinate(x, y));
    }
}