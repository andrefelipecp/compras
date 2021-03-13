import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { ComprasSharedModule } from 'app/shared/shared.module';
import { ComprasCoreModule } from 'app/core/core.module';
import { ComprasAppRoutingModule } from './app-routing.module';
import { ComprasHomeModule } from './home/home.module';
import { ComprasEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    ComprasSharedModule,
    ComprasCoreModule,
    ComprasHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    ComprasEntityModule,
    ComprasAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class ComprasAppModule {}
