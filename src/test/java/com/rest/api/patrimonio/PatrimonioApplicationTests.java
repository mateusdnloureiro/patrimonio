package com.rest.api.patrimonio;

import com.rest.api.patrimonio.model.*;
import com.rest.api.patrimonio.service.MarcaRepository;
import com.rest.api.patrimonio.service.PatrimonioRepository;
import com.rest.api.patrimonio.service.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PatrimonioApplicationTests {

	private static String URL_TEMPLATE = "http://localhost:";

	@LocalServerPort
	private int port;

	@Autowired
	private MarcaRepository marcaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private PatrimonioRepository patrimonioRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	private static HttpHeaders headers;

	@Test
	@Order(1)
	public void naoDeveListarPatrimonioSemAutenticacaoPorToken() {
		final ResponseEntity response = restTemplate.getForEntity(URL_TEMPLATE + port + "/patrimonios", Object.class);
		Assertions.assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	@Test
	@Order(2)
	public void deveSalvarUsuarioAdminELogarNoSistema() {
		UsuarioDTO admin = Optional.ofNullable(usuarioRepository.findByEmail("admin@gmail.com"))
				.map(Usuario::toDTO).orElse(new UsuarioDTO());
		admin.setNome("Admin");
		admin.setEmail("admin@gmail.com");
		admin.setSenha("admin123");

		ResponseEntity<UsuarioDTO> saveUsuario = restTemplate.postForEntity(URL_TEMPLATE + port + "/usuarios/save", admin, UsuarioDTO.class);
		UsuarioDTO usuarioAdmin = saveUsuario.getBody();

		Assertions.assertNotNull(usuarioAdmin.getId());

		ResponseEntity<UsuarioDTO> login = restTemplate.postForEntity(URL_TEMPLATE + port + "/login", admin, UsuarioDTO.class);
		UsuarioDTO usuarioLogado = login.getBody();

		this.headers = new HttpHeaders();
		headers.set("Authorization", usuarioLogado.getToken());
		Assertions.assertNotNull(usuarioLogado.getToken());
	}

	@Test
	@Order(3)
	public void deveSalvarMarcaComSucesso() {
		MarcaDTO dto = Optional.ofNullable(marcaRepository.findByNome("Marca Nova")).map(Marca::toDTO).orElse(new MarcaDTO());
		dto.setNome("Marca Nova");

		final ResponseEntity<MarcaDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/marcas/save", HttpMethod.POST, new HttpEntity(dto, headers), MarcaDTO.class);

		final MarcaDTO novaMarca = response.getBody();
		Assertions.assertEquals("Marca Nova", novaMarca.getNome());
		Assertions.assertNotNull(novaMarca.getId());
	}

	@Test
	@Order(4)
	public void deveEditarMarcaComSucesso() {
		final MarcaDTO dto = marcaRepository.findByNome("Marca Nova").toDTO();
		dto.setNome("Marca Editada");

		final ResponseEntity<MarcaDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/marcas/save", HttpMethod.POST, new HttpEntity(dto, headers), MarcaDTO.class);

		final MarcaDTO marcaAntiga = response.getBody();
		Assertions.assertEquals("Marca Editada", marcaAntiga.getNome());
		Assertions.assertEquals(dto.getId(), marcaAntiga.getId());
	}

	@Test
	@Order(5)
	public void deveExcluirMarcaComSucesso() {
		final MarcaDTO marcaNova = marcaRepository.findByNome("Marca Editada").toDTO();

		final ResponseEntity<MarcaDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/marcas/" + marcaNova.getId(), HttpMethod.DELETE, new HttpEntity(marcaNova, headers), MarcaDTO.class);

		Assertions.assertNull(marcaRepository.findById(marcaNova.getId()).orElse(null));
	}

	@Test
	@Order(6)
	public void deveSalvarUsuarioComSucesso() {
		UsuarioDTO dto = Optional.ofNullable(usuarioRepository.findByEmail("mateus@gmail.com")).map(Usuario::toDTO).orElse(new UsuarioDTO());
		dto.setNome("Mateus");
		dto.setEmail("mateus@gmail.com");
		dto.setSenha("mateus123");

		final ResponseEntity<UsuarioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/usuarios/save", HttpMethod.POST, new HttpEntity(dto, headers), UsuarioDTO.class);

		final UsuarioDTO novoUsuario = response.getBody();
		Assertions.assertNotNull(novoUsuario.getId());
		Assertions.assertEquals("Mateus", novoUsuario.getNome());
		Assertions.assertEquals("mateus@gmail.com", novoUsuario.getEmail());
		Assertions.assertEquals("mateus123", novoUsuario.getSenha());
	}

	@Test
	@Order(7)
	public void deveEditarUsuarioComSucesso() {
		final UsuarioDTO dto = usuarioRepository.findByEmail("mateus@gmail.com").toDTO();
		dto.setNome("Mateus Editado");

		final ResponseEntity<UsuarioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/usuarios/save", HttpMethod.POST, new HttpEntity(dto, headers), UsuarioDTO.class);

		final UsuarioDTO usuarioAntigo = response.getBody();
		Assertions.assertEquals("Mateus Editado", usuarioAntigo.getNome());
		Assertions.assertEquals(dto.getId(), usuarioAntigo.getId());
	}

	@Test
	@Order(8)
	public void deveExcluirUsuarioComSucesso() {
		final UsuarioDTO usuario = usuarioRepository.findByEmail("mateus@gmail.com").toDTO();

		final ResponseEntity<UsuarioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/usuarios/" + usuario.getId(), HttpMethod.DELETE, new HttpEntity(usuario, headers), UsuarioDTO.class);

		Assertions.assertNull(usuarioRepository.findById(usuario.getId()).orElse(null));
	}

	@Test
	@Order(9)
	public void deveSalvarPatrimonioComSucesso() {
		//setup - criação da marca
		MarcaDTO marcaDTO = Optional.ofNullable(marcaRepository.findByNome("Marca Patrimonio")).map(Marca::toDTO).orElse(new MarcaDTO());
		marcaDTO.setNome("Marca Patrimonio");
		final ResponseEntity<MarcaDTO> responseMarca = restTemplate.exchange(URL_TEMPLATE + port + "/marcas/save", HttpMethod.POST, new HttpEntity(marcaDTO, headers), MarcaDTO.class);
		final MarcaDTO marcaPatrimonio = responseMarca.getBody();

		PatrimonioDTO dto = Optional.ofNullable(patrimonioRepository.findByNome("Frota Caminhões")).map(Patrimonio::toDTO).orElse(new PatrimonioDTO());
		dto.setNome("Frota Caminhões");
		dto.setDescricao("Frota de caminhões da empresa");
		dto.setMarcaId(marcaPatrimonio.getId());
		//numero_tombo será gerado automaticamente

		final ResponseEntity<PatrimonioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/patrimonios/save", HttpMethod.POST, new HttpEntity(dto, headers), PatrimonioDTO.class);

		final PatrimonioDTO novoPatrimonio = response.getBody();
		Assertions.assertNotNull(novoPatrimonio.getId());
		Assertions.assertNotNull(novoPatrimonio.getNumeroTombo());
		Assertions.assertEquals("Frota Caminhões", novoPatrimonio.getNome());
		Assertions.assertEquals("Frota de caminhões da empresa", novoPatrimonio.getDescricao());
		Assertions.assertEquals(marcaPatrimonio.getId(), novoPatrimonio.getMarcaId());
	}

	@Test
	@Order(10)
	public void deveEditarPatrimonioComSucesso() {
		final PatrimonioDTO dto = patrimonioRepository.findByNome("Frota Caminhões").toDTO();
		dto.setNome("Patrimonio Editado");

		final ResponseEntity<PatrimonioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/patrimonios/save", HttpMethod.POST, new HttpEntity(dto, headers), PatrimonioDTO.class);

		final PatrimonioDTO patrimonioAntigo = response.getBody();
		Assertions.assertEquals("Patrimonio Editado", patrimonioAntigo.getNome());
		Assertions.assertEquals(dto.getId(), patrimonioAntigo.getId());
	}

	@Test
	@Order(11)
	public void deveExcluirPatrimonioComSucesso() {
		final PatrimonioDTO patrimonio = patrimonioRepository.findByNome("Patrimonio Editado").toDTO();

		final ResponseEntity<PatrimonioDTO> response = restTemplate.exchange(URL_TEMPLATE + port + "/patrimonios/" + patrimonio.getId(), HttpMethod.DELETE, new HttpEntity(patrimonio, headers), PatrimonioDTO.class);

		Assertions.assertNull(patrimonioRepository.findById(patrimonio.getId()).orElse(null));
	}

	@Test
	@Order(12)
	public void naoDeveCriarMarcaComNomeDuplicado() {
		MarcaDTO marcaDTO = new MarcaDTO();
		marcaDTO.setNome("Marca Patrimonio");

		final ResponseEntity<MarcaDTO> responseMarca = restTemplate.exchange(URL_TEMPLATE + port + "/marcas/save", HttpMethod.POST, new HttpEntity(marcaDTO, headers), MarcaDTO.class);
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseMarca.getStatusCode());
	}

	@Test
	@Order(13)
	public void naoDeveCriarUsuarioComEmailDuplicado() {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setEmail("admin@gmail.com");

		ResponseEntity<UsuarioDTO> saveUsuario = restTemplate.postForEntity(URL_TEMPLATE + port + "/usuarios/save", dto, UsuarioDTO.class);
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, saveUsuario.getStatusCode());
	}

}
