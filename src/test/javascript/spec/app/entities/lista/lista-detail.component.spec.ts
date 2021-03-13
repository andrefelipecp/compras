import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ComprasTestModule } from '../../../test.module';
import { ListaDetailComponent } from 'app/entities/lista/lista-detail.component';
import { Lista } from 'app/shared/model/lista.model';

describe('Component Tests', () => {
  describe('Lista Management Detail Component', () => {
    let comp: ListaDetailComponent;
    let fixture: ComponentFixture<ListaDetailComponent>;
    const route = ({ data: of({ lista: new Lista(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ComprasTestModule],
        declarations: [ListaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ListaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ListaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load lista on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lista).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
