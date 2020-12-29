package com.bala.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.stereotype.Service;


@Path("patientservice")
public class PatientServiceImpl implements PatientService {
	
	Map<Long, Patient> patients = new HashMap<>();
	long currentId = 123;

	public PatientServiceImpl() {
		init();
	}

	void init() {
		Patient patient = new Patient();
		patient.setId(currentId);
		patient.setName("John");
		patients.put(patient.getId(), patient);
		
		Patient patient1 = new Patient();
		patient1.setId(++currentId);
		patient1.setName("Cena");
		patients.put(patient1.getId(), patient1);
	}

	@Path("patients")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Patient> getPatients() {
		Collection<Patient> results = patients.values();
		List<Patient> response = new ArrayList<>(results);
		return response;
	}
	
	@Path("patients/{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public Patient getPatient(@PathParam("id") long id) {
		
		if(patients.get(id) == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return patients.get(id);
	}
	
	@Path("patients")
	@POST
	public Response createPatient(Patient patient) {
		patient.setId(++currentId);
		patients.put(patient.getId(), patient);
		return Response.ok(patient).build();
	}
	
	@Path("patients")
	@PUT
	public Response updatePatient(Patient patient) {

		Patient p =patients.get(patient.getId());
		patients.put(patient.getId(), p);
		return Response.ok(patient).build();
	}

}
