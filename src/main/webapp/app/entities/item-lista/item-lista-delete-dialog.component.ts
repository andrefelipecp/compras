import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IItemLista } from 'app/shared/model/item-lista.model';
import { ItemListaService } from './item-lista.service';

@Component({
  templateUrl: './item-lista-delete-dialog.component.html',
})
export class ItemListaDeleteDialogComponent {
  itemLista?: IItemLista;

  constructor(protected itemListaService: ItemListaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.itemListaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('itemListaListModification');
      this.activeModal.close();
    });
  }
}
