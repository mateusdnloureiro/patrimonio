package com.rest.api.patrimonio.controller;

import com.rest.api.patrimonio.model.Usuario;
import com.rest.api.patrimonio.model.UsuarioDTO;
import com.rest.api.patrimonio.service.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

@RestController
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping(path = "/login")
    @ResponseBody
    public UsuarioDTO login(@RequestBody UsuarioDTO userDTO) {
        if (isEmpty(userDTO.getEmail()) || isEmpty(userDTO.getSenha())) {
            throw new IllegalArgumentException("Necessário informar o email e senha para login");
        }

        final Usuario usuarioLogado = usuarioRepository.findByEmail(userDTO.getEmail());
        /*Ideal seria utilizar alguma criptografia na senha (MD5 por exemplo)*/
        if (usuarioLogado == null || !ObjectUtils.nullSafeEquals(usuarioLogado.getSenha(), userDTO.getSenha())) {
            return null;
        }

        /*Gerado token JWT*/
        String token = getJWTToken(userDTO.getEmail());
        UsuarioDTO dto = usuarioLogado.toDTO();
        dto.setToken(token);
        return dto;
    }

    private String getJWTToken(String email) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("patrimonioJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}
