import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IItemLista, ItemLista } from 'app/shared/model/item-lista.model';
import { ItemListaService } from './item-lista.service';
import { ItemListaComponent } from './item-lista.component';
import { ItemListaDetailComponent } from './item-lista-detail.component';
import { ItemListaUpdateComponent } from './item-lista-update.component';

@Injectable({ providedIn: 'root' })
export class ItemListaResolve implements Resolve<IItemLista> {
  constructor(private service: ItemListaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IItemLista> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((itemLista: HttpResponse<ItemLista>) => {
          if (itemLista.body) {
            return of(itemLista.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ItemLista());
  }
}

export const itemListaRoute: Routes = [
  {
    path: '',
    component: ItemListaComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'ItemListas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ItemListaDetailComponent,
    resolve: {
      itemLista: ItemListaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemListas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ItemListaUpdateComponent,
    resolve: {
      itemLista: ItemListaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemListas',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ItemListaUpdateComponent,
    resolve: {
      itemLista: ItemListaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'ItemListas',
    },
    canActivate: [UserRouteAccessService],
  },
];
