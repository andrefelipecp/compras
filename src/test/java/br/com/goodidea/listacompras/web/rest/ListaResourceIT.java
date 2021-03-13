package br.com.goodidea.listacompras.web.rest;

import br.com.goodidea.listacompras.ComprasApp;
import br.com.goodidea.listacompras.domain.Lista;
import br.com.goodidea.listacompras.repository.ListaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ListaResource} REST controller.
 */
@SpringBootTest(classes = ComprasApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ListaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private ListaRepository listaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restListaMockMvc;

    private Lista lista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lista createEntity(EntityManager em) {
        Lista lista = new Lista()
            .nome(DEFAULT_NOME);
        return lista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lista createUpdatedEntity(EntityManager em) {
        Lista lista = new Lista()
            .nome(UPDATED_NOME);
        return lista;
    }

    @BeforeEach
    public void initTest() {
        lista = createEntity(em);
    }

    @Test
    @Transactional
    public void createLista() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();
        // Create the Lista
        restListaMockMvc.perform(post("/api/listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lista)))
            .andExpect(status().isCreated());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate + 1);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createListaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listaRepository.findAll().size();

        // Create the Lista with an existing ID
        lista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListaMockMvc.perform(post("/api/listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lista)))
            .andExpect(status().isBadRequest());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = listaRepository.findAll().size();
        // set the field null
        lista.setNome(null);

        // Create the Lista, which fails.


        restListaMockMvc.perform(post("/api/listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lista)))
            .andExpect(status().isBadRequest());

        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListas() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get all the listaList
        restListaMockMvc.perform(get("/api/listas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", lista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lista.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }
    @Test
    @Transactional
    public void getNonExistingLista() throws Exception {
        // Get the lista
        restListaMockMvc.perform(get("/api/listas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // Update the lista
        Lista updatedLista = listaRepository.findById(lista.getId()).get();
        // Disconnect from session so that the updates on updatedLista are not directly saved in db
        em.detach(updatedLista);
        updatedLista
            .nome(UPDATED_NOME);

        restListaMockMvc.perform(put("/api/listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLista)))
            .andExpect(status().isOk());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate);
        Lista testLista = listaList.get(listaList.size() - 1);
        assertThat(testLista.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingLista() throws Exception {
        int databaseSizeBeforeUpdate = listaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListaMockMvc.perform(put("/api/listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lista)))
            .andExpect(status().isBadRequest());

        // Validate the Lista in the database
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLista() throws Exception {
        // Initialize the database
        listaRepository.saveAndFlush(lista);

        int databaseSizeBeforeDelete = listaRepository.findAll().size();

        // Delete the lista
        restListaMockMvc.perform(delete("/api/listas/{id}", lista.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lista> listaList = listaRepository.findAll();
        assertThat(listaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
