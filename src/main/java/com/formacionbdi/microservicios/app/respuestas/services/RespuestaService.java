package com.formacionbdi.microservicios.app.respuestas.services;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaService {
	
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long AlumnoId, Long examenId);

	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
	
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
