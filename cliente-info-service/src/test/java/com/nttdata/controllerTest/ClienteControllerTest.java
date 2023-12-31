package com.nttdata.controllerTest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nttdata.application.BuscarPorTipoNumeroDocumentoUseCase;
import com.nttdata.domain.Cliente;

import static org.junit.Assert.*;

public class ClienteControllerTest {
	private BuscarPorTipoNumeroDocumentoUseCase buscarPorTipoNumeroDocumentoUseCase;

	@Before
	public void setUp() {
		this.buscarPorTipoNumeroDocumentoUseCase = new BuscarPorTipoNumeroDocumentoUseCase();
	}

	@Test
	public void pruebaError500() {
		ResponseEntity<?> resultado = this.buscarPorTipoNumeroDocumentoUseCase.run("C", "35364575Z");
		assertEquals(resultado.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	public void pruebaError400() {
		ResponseEntity<?> resultado = this.buscarPorTipoNumeroDocumentoUseCase.run("D", "23445322");
		assertEquals(resultado.getStatusCode(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void pruebaError404() {
		ResponseEntity<?> resultado = this.buscarPorTipoNumeroDocumentoUseCase.run("P", "23445322");
		ResponseEntity<?> resultado1 = this.buscarPorTipoNumeroDocumentoUseCase.run("C", "234457322");
		assertEquals(resultado.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(resultado1.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void pruebaError200() {
		ResponseEntity<?> resultado = this.buscarPorTipoNumeroDocumentoUseCase.run("C", "23445322");
		Cliente body = (Cliente) resultado.getBody();

		assertEquals(resultado.getStatusCode(), HttpStatus.OK);
		assertEquals(body.getPrimerNombre(), "Ronald");
		assertEquals(body.getSegundoNombre(), "Alexis");
		assertEquals(body.getPrimerApellido(), "Cuadros");
		assertEquals(body.getSegundoApellido(), "Benavides");
		assertEquals(body.getTelefono(), "316 710 0756");
		assertEquals(body.getDireccion(), "123 Calle Principal");
		assertEquals(body.getCiudadResidencia(), "Piedecuesta");
		assertEquals(body.getTipoDocumento(), "C");
		assertEquals(body.getNumeroDocumento(), 23445322);
	}

}