package br.com.goodidea.listacompras.web.rest;

import br.com.goodidea.listacompras.domain.ItemLista;
import br.com.goodidea.listacompras.repository.ItemListaRepository;
import br.com.goodidea.listacompras.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.goodidea.listacompras.domain.ItemLista}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ItemListaResource {

    private final Logger log = LoggerFactory.getLogger(ItemListaResource.class);

    private static final String ENTITY_NAME = "itemLista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ItemListaRepository itemListaRepository;

    public ItemListaResource(ItemListaRepository itemListaRepository) {
        this.itemListaRepository = itemListaRepository;
    }

    /**
     * {@code POST  /item-listas} : Create a new itemLista.
     *
     * @param itemLista the itemLista to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new itemLista, or with status {@code 400 (Bad Request)} if the itemLista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/item-listas")
    public ResponseEntity<ItemLista> createItemLista(@Valid @RequestBody ItemLista itemLista) throws URISyntaxException {
        log.debug("REST request to save ItemLista : {}", itemLista);
        if (itemLista.getId() != null) {
            throw new BadRequestAlertException("A new itemLista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemLista result = itemListaRepository.save(itemLista);
        return ResponseEntity.created(new URI("/api/item-listas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /item-listas} : Updates an existing itemLista.
     *
     * @param itemLista the itemLista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated itemLista,
     * or with status {@code 400 (Bad Request)} if the itemLista is not valid,
     * or with status {@code 500 (Internal Server Error)} if the itemLista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/item-listas")
    public ResponseEntity<ItemLista> updateItemLista(@Valid @RequestBody ItemLista itemLista) throws URISyntaxException {
        log.debug("REST request to update ItemLista : {}", itemLista);
        if (itemLista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemLista result = itemListaRepository.save(itemLista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, itemLista.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /item-listas} : get all the itemListas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of itemListas in body.
     */
    @GetMapping("/item-listas")
    public ResponseEntity<List<ItemLista>> getAllItemListas(Pageable pageable) {
        log.debug("REST request to get a page of ItemListas");
        Page<ItemLista> page = itemListaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /item-listas/:id} : get the "id" itemLista.
     *
     * @param id the id of the itemLista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the itemLista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/item-listas/{id}")
    public ResponseEntity<ItemLista> getItemLista(@PathVariable Long id) {
        log.debug("REST request to get ItemLista : {}", id);
        Optional<ItemLista> itemLista = itemListaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemLista);
    }

    /**
     * {@code DELETE  /item-listas/:id} : delete the "id" itemLista.
     *
     * @param id the id of the itemLista to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/item-listas/{id}")
    public ResponseEntity<Void> deleteItemLista(@PathVariable Long id) {
        log.debug("REST request to delete ItemLista : {}", id);
        itemListaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
