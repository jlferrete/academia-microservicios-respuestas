package com.formacionbdi.microservicios.app.respuestas.services;



import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Service
public class RespuestaServiceImpl implements RespuestaService {

	@Autowired
	private RespuestaRepository repository;
	
	@Autowired
	private ExamenFeignClient examenClient;
	
	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {

		return repository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long AlumnoId, Long examenId) {
		Examen examen = examenClient.obtenerExamenPorId(examenId);
		List<Pregunta> preguntas = examen.getPreguntas();
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Respuesta> respuestas = (List<Respuesta>) repository.findRespuestaByAlumnoByPreguntaIds(AlumnoId, preguntaIds);
		respuestas = respuestas.parallelStream().map(r -> {
			preguntas.forEach(p -> {
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());
		
		return respuestas;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {

		return null;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return repository.findByAlumnoId(alumnoId);
	}

}
