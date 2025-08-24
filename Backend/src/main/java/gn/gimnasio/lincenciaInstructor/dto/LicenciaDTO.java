package gn.gimnasio.lincenciaInstructor.dto;

import java.time.Instant;

public record LicenciaDTO(
  Integer id,
  Integer instructorId,
  String filename,
  String filepath,
  Instant uploadedAt
) {}
