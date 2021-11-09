import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RatePlanChargeBillingComponent } from './list/rate-plan-charge-billing.component';
import { RatePlanChargeBillingDetailComponent } from './detail/rate-plan-charge-billing-detail.component';
import { RatePlanChargeBillingUpdateComponent } from './update/rate-plan-charge-billing-update.component';
import { RatePlanChargeBillingDeleteDialogComponent } from './delete/rate-plan-charge-billing-delete-dialog.component';
import { RatePlanChargeBillingRoutingModule } from './route/rate-plan-charge-billing-routing.module';

@NgModule({
  imports: [SharedModule, RatePlanChargeBillingRoutingModule],
  declarations: [
    RatePlanChargeBillingComponent,
    RatePlanChargeBillingDetailComponent,
    RatePlanChargeBillingUpdateComponent,
    RatePlanChargeBillingDeleteDialogComponent,
  ],
  entryComponents: [RatePlanChargeBillingDeleteDialogComponent],
})
export class RatePlanChargeBillingModule {}
