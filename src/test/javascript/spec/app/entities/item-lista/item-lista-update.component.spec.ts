import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ComprasTestModule } from '../../../test.module';
import { ItemListaUpdateComponent } from 'app/entities/item-lista/item-lista-update.component';
import { ItemListaService } from 'app/entities/item-lista/item-lista.service';
import { ItemLista } from 'app/shared/model/item-lista.model';

describe('Component Tests', () => {
  describe('ItemLista Management Update Component', () => {
    let comp: ItemListaUpdateComponent;
    let fixture: ComponentFixture<ItemListaUpdateComponent>;
    let service: ItemListaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComprasTestModule],
        declarations: [ItemListaUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ItemListaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ItemListaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ItemListaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemLista(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ItemLista();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
