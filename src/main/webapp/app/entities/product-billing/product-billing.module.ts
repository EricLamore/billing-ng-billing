import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductBillingComponent } from './list/product-billing.component';
import { ProductBillingDetailComponent } from './detail/product-billing-detail.component';
import { ProductBillingUpdateComponent } from './update/product-billing-update.component';
import { ProductBillingDeleteDialogComponent } from './delete/product-billing-delete-dialog.component';
import { ProductBillingRoutingModule } from './route/product-billing-routing.module';

@NgModule({
  imports: [SharedModule, ProductBillingRoutingModule],
  declarations: [
    ProductBillingComponent,
    ProductBillingDetailComponent,
    ProductBillingUpdateComponent,
    ProductBillingDeleteDialogComponent,
  ],
  entryComponents: [ProductBillingDeleteDialogComponent],
})
export class ProductBillingModule {}
