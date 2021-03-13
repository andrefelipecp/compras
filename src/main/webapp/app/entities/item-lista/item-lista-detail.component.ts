import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemLista } from 'app/shared/model/item-lista.model';

@Component({
  selector: 'jhi-item-lista-detail',
  templateUrl: './item-lista-detail.component.html',
})
export class ItemListaDetailComponent implements OnInit {
  itemLista: IItemLista | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemLista }) => (this.itemLista = itemLista));
  }

  previousState(): void {
    window.history.back();
  }
}
