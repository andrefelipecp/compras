import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IItemLista, ItemLista } from 'app/shared/model/item-lista.model';
import { ItemListaService } from './item-lista.service';
import { ILista } from 'app/shared/model/lista.model';
import { ListaService } from 'app/entities/lista/lista.service';

@Component({
  selector: 'jhi-item-lista-update',
  templateUrl: './item-lista-update.component.html',
})
export class ItemListaUpdateComponent implements OnInit {
  isSaving = false;
  listas: ILista[] = [];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    preco: [null, [Validators.required]],
    lista: [],
  });

  constructor(
    protected itemListaService: ItemListaService,
    protected listaService: ListaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemLista }) => {
      this.updateForm(itemLista);

      this.listaService.query().subscribe((res: HttpResponse<ILista[]>) => (this.listas = res.body || []));
    });
  }

  updateForm(itemLista: IItemLista): void {
    this.editForm.patchValue({
      id: itemLista.id,
      nome: itemLista.nome,
      preco: itemLista.preco,
      lista: itemLista.lista,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemLista = this.createFromForm();
    if (itemLista.id !== undefined) {
      this.subscribeToSaveResponse(this.itemListaService.update(itemLista));
    } else {
      this.subscribeToSaveResponse(this.itemListaService.create(itemLista));
    }
  }

  private createFromForm(): IItemLista {
    return {
      ...new ItemLista(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
      preco: this.editForm.get(['preco'])!.value,
      lista: this.editForm.get(['lista'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemLista>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ILista): any {
    return item.id;
  }
}
