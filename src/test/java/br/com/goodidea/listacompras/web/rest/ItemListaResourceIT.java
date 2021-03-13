package br.com.goodidea.listacompras.web.rest;

import br.com.goodidea.listacompras.ComprasApp;
import br.com.goodidea.listacompras.domain.ItemLista;
import br.com.goodidea.listacompras.repository.ItemListaRepository;

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
 * Integration tests for the {@link ItemListaResource} REST controller.
 */
@SpringBootTest(classes = ComprasApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemListaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECO = 1D;
    private static final Double UPDATED_PRECO = 2D;

    @Autowired
    private ItemListaRepository itemListaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemListaMockMvc;

    private ItemLista itemLista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemLista createEntity(EntityManager em) {
        ItemLista itemLista = new ItemLista()
            .nome(DEFAULT_NOME)
            .preco(DEFAULT_PRECO);
        return itemLista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemLista createUpdatedEntity(EntityManager em) {
        ItemLista itemLista = new ItemLista()
            .nome(UPDATED_NOME)
            .preco(UPDATED_PRECO);
        return itemLista;
    }

    @BeforeEach
    public void initTest() {
        itemLista = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemLista() throws Exception {
        int databaseSizeBeforeCreate = itemListaRepository.findAll().size();
        // Create the ItemLista
        restItemListaMockMvc.perform(post("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemLista)))
            .andExpect(status().isCreated());

        // Validate the ItemLista in the database
        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeCreate + 1);
        ItemLista testItemLista = itemListaList.get(itemListaList.size() - 1);
        assertThat(testItemLista.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testItemLista.getPreco()).isEqualTo(DEFAULT_PRECO);
    }

    @Test
    @Transactional
    public void createItemListaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemListaRepository.findAll().size();

        // Create the ItemLista with an existing ID
        itemLista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemListaMockMvc.perform(post("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemLista)))
            .andExpect(status().isBadRequest());

        // Validate the ItemLista in the database
        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemListaRepository.findAll().size();
        // set the field null
        itemLista.setNome(null);

        // Create the ItemLista, which fails.


        restItemListaMockMvc.perform(post("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemLista)))
            .andExpect(status().isBadRequest());

        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrecoIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemListaRepository.findAll().size();
        // set the field null
        itemLista.setPreco(null);

        // Create the ItemLista, which fails.


        restItemListaMockMvc.perform(post("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemLista)))
            .andExpect(status().isBadRequest());

        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemListas() throws Exception {
        // Initialize the database
        itemListaRepository.saveAndFlush(itemLista);

        // Get all the itemListaList
        restItemListaMockMvc.perform(get("/api/item-listas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemLista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].preco").value(hasItem(DEFAULT_PRECO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getItemLista() throws Exception {
        // Initialize the database
        itemListaRepository.saveAndFlush(itemLista);

        // Get the itemLista
        restItemListaMockMvc.perform(get("/api/item-listas/{id}", itemLista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemLista.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.preco").value(DEFAULT_PRECO.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingItemLista() throws Exception {
        // Get the itemLista
        restItemListaMockMvc.perform(get("/api/item-listas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemLista() throws Exception {
        // Initialize the database
        itemListaRepository.saveAndFlush(itemLista);

        int databaseSizeBeforeUpdate = itemListaRepository.findAll().size();

        // Update the itemLista
        ItemLista updatedItemLista = itemListaRepository.findById(itemLista.getId()).get();
        // Disconnect from session so that the updates on updatedItemLista are not directly saved in db
        em.detach(updatedItemLista);
        updatedItemLista
            .nome(UPDATED_NOME)
            .preco(UPDATED_PRECO);

        restItemListaMockMvc.perform(put("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemLista)))
            .andExpect(status().isOk());

        // Validate the ItemLista in the database
        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeUpdate);
        ItemLista testItemLista = itemListaList.get(itemListaList.size() - 1);
        assertThat(testItemLista.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testItemLista.getPreco()).isEqualTo(UPDATED_PRECO);
    }

    @Test
    @Transactional
    public void updateNonExistingItemLista() throws Exception {
        int databaseSizeBeforeUpdate = itemListaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemListaMockMvc.perform(put("/api/item-listas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemLista)))
            .andExpect(status().isBadRequest());

        // Validate the ItemLista in the database
        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemLista() throws Exception {
        // Initialize the database
        itemListaRepository.saveAndFlush(itemLista);

        int databaseSizeBeforeDelete = itemListaRepository.findAll().size();

        // Delete the itemLista
        restItemListaMockMvc.perform(delete("/api/item-listas/{id}", itemLista.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemLista> itemListaList = itemListaRepository.findAll();
        assertThat(itemListaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
