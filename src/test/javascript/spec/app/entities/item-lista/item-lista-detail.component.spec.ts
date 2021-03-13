import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComprasTestModule } from '../../../test.module';
import { ItemListaDetailComponent } from 'app/entities/item-lista/item-lista-detail.component';
import { ItemLista } from 'app/shared/model/item-lista.model';

describe('Component Tests', () => {
  describe('ItemLista Management Detail Component', () => {
    let comp: ItemListaDetailComponent;
    let fixture: ComponentFixture<ItemListaDetailComponent>;
    const route = ({ data: of({ itemLista: new ItemLista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComprasTestModule],
        declarations: [ItemListaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ItemListaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ItemListaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load itemLista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.itemLista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
