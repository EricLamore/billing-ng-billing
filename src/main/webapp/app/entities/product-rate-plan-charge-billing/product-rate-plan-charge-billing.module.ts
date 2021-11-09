import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ProductRatePlanChargeBillingComponent } from './list/product-rate-plan-charge-billing.component';
import { ProductRatePlanChargeBillingDetailComponent } from './detail/product-rate-plan-charge-billing-detail.component';
import { ProductRatePlanChargeBillingUpdateComponent } from './update/product-rate-plan-charge-billing-update.component';
import { ProductRatePlanChargeBillingDeleteDialogComponent } from './delete/product-rate-plan-charge-billing-delete-dialog.component';
import { ProductRatePlanChargeBillingRoutingModule } from './route/product-rate-plan-charge-billing-routing.module';

@NgModule({
  imports: [SharedModule, ProductRatePlanChargeBillingRoutingModule],
  declarations: [
    ProductRatePlanChargeBillingComponent,
    ProductRatePlanChargeBillingDetailComponent,
    ProductRatePlanChargeBillingUpdateComponent,
    ProductRatePlanChargeBillingDeleteDialogComponent,
  ],
  entryComponents: [ProductRatePlanChargeBillingDeleteDialogComponent],
})
export class ProductRatePlanChargeBillingModule {}
