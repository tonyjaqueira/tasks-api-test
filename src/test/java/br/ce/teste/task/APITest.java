package br.ce.teste.task;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	//é executado ates que a classe seja instanciada
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend"; //passando a baseURI
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when() //envio
			.get("/todo")
		.then() //resposta
			.statusCode(200)
		;
	}
	
	@Test
	public void deveRetornarTarefaComSucesso() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2021-05-30\"	}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201) //201 diz q foi criado com seucesso
		;
	}
	
	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{\"task\": \"Teste via API\",\"dueDate\": \"2021-05-01\"	}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400) //201 diz q foi criado com seucesso
			.body("message", CoreMatchers.is("Due date must not be in past")) //verifica se a mensagem de erro retornada esta de acordo com a passada em CoreMathres
		;
	}

	
	
}
