import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ComprasSharedModule } from 'app/shared/shared.module';
import { ItemListaComponent } from './item-lista.component';
import { ItemListaDetailComponent } from './item-lista-detail.component';
import { ItemListaUpdateComponent } from './item-lista-update.component';
import { ItemListaDeleteDialogComponent } from './item-lista-delete-dialog.component';
import { itemListaRoute } from './item-lista.route';

@NgModule({
  imports: [ComprasSharedModule, RouterModule.forChild(itemListaRoute)],
  declarations: [ItemListaComponent, ItemListaDetailComponent, ItemListaUpdateComponent, ItemListaDeleteDialogComponent],
  entryComponents: [ItemListaDeleteDialogComponent],
})
export class ComprasItemListaModule {}
