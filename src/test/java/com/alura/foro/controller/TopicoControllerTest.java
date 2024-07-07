package com.alura.foro.controller;

import com.alura.foro.dominio.topico.DatosCreacionTopico;
import com.alura.foro.dominio.topico.DatosMostrarTopico;
import com.alura.foro.dominio.topico.Topico;
import com.alura.foro.dominio.topico.TopicoService;
import com.alura.foro.dominio.usuario.Rol;
import com.alura.foro.dominio.usuario.Usuario;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosCreacionTopico> creacionTopicoJacksonTester;

    @Autowired
    private JacksonTester<DatosMostrarTopico> mostrarTopicoJacksonTester;

    @MockBean //simula recorrido x db
    private TopicoService topicoService; //deberia estar en una clase service

    @Test
    @DisplayName("Devolver 400 con datos no válidos")
    @WithMockUser
    void crearTopicoEscenario400() throws Exception{

        //given
        //no params

        //when
        var response = mockMvc.perform(post("/topicos"))
                .andReturn().getResponse();

        //then
        assertThat(response.getStatus())
                .isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Devolver 201 con datos válidos")
    @WithMockUser
    void crearTopicoEscenario201() throws Exception
    {
        //given
        var usuario = new Usuario(1L, "joselo", "joselo@mail.com", "123", Rol.USUARIO, true);

        var datosCreacionTopico = new DatosCreacionTopico("Titulo de prueba",
                "Mandando un mensaje de prueba", usuario.getId());

        var topico = new Topico(datosCreacionTopico, usuario);
        var datosMostrarTopico = new DatosMostrarTopico(topico);

        //when
        when(topicoService.crearTopico(any())).thenReturn(datosMostrarTopico);

        var response = mockMvc.perform(post("/topicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(creacionTopicoJacksonTester.write(datosCreacionTopico).getJson())
        ).andReturn().getResponse();

        //then
        Assertions.assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var jsonEsperado = mostrarTopicoJacksonTester.write(datosMostrarTopico).getJson();

        //para evitar conflictos con la generación de id
        String jsonReal = response.getContentAsString();

        // Parse JSON strings
        JSONObject jObjReal = new JSONObject(jsonReal);
        JSONObject jObjEsperado = new JSONObject(jsonEsperado);

        // Remove id fields
        jObjReal.remove("id");
        jObjEsperado.remove("id");

        // Compare JSON objects
        JSONAssert.assertEquals(jObjEsperado, jObjReal, JSONCompareMode.STRICT);

        //assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}