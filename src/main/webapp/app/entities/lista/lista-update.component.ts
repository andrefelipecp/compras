import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ILista, Lista } from 'app/shared/model/lista.model';
import { ListaService } from './lista.service';

@Component({
  selector: 'jhi-lista-update',
  templateUrl: './lista-update.component.html',
})
export class ListaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
  });

  constructor(protected listaService: ListaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lista }) => {
      this.updateForm(lista);
    });
  }

  updateForm(lista: ILista): void {
    this.editForm.patchValue({
      id: lista.id,
      nome: lista.nome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const lista = this.createFromForm();
    if (lista.id !== undefined) {
      this.subscribeToSaveResponse(this.listaService.update(lista));
    } else {
      this.subscribeToSaveResponse(this.listaService.create(lista));
    }
  }

  private createFromForm(): ILista {
    return {
      ...new Lista(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILista>>): void {
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
}
