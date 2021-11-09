import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductRatePlanBillingComponent } from './list/product-rate-plan-billing.component';
import { ProductRatePlanBillingDetailComponent } from './detail/product-rate-plan-billing-detail.component';
import { ProductRatePlanBillingUpdateComponent } from './update/product-rate-plan-billing-update.component';
import { ProductRatePlanBillingDeleteDialogComponent } from './delete/product-rate-plan-billing-delete-dialog.component';
import { ProductRatePlanBillingRoutingModule } from './route/product-rate-plan-billing-routing.module';

@NgModule({
  imports: [SharedModule, ProductRatePlanBillingRoutingModule],
  declarations: [
    ProductRatePlanBillingComponent,
    ProductRatePlanBillingDetailComponent,
    ProductRatePlanBillingUpdateComponent,
    ProductRatePlanBillingDeleteDialogComponent,
  ],
  entryComponents: [ProductRatePlanBillingDeleteDialogComponent],
})
export class ProductRatePlanBillingModule {}
