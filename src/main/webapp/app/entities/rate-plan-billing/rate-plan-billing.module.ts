import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RatePlanBillingComponent } from './list/rate-plan-billing.component';
import { RatePlanBillingDetailComponent } from './detail/rate-plan-billing-detail.component';
import { RatePlanBillingUpdateComponent } from './update/rate-plan-billing-update.component';
import { RatePlanBillingDeleteDialogComponent } from './delete/rate-plan-billing-delete-dialog.component';
import { RatePlanBillingRoutingModule } from './route/rate-plan-billing-routing.module';

@NgModule({
  imports: [SharedModule, RatePlanBillingRoutingModule],
  declarations: [
    RatePlanBillingComponent,
    RatePlanBillingDetailComponent,
    RatePlanBillingUpdateComponent,
    RatePlanBillingDeleteDialogComponent,
  ],
  entryComponents: [RatePlanBillingDeleteDialogComponent],
})
export class RatePlanBillingModule {}
