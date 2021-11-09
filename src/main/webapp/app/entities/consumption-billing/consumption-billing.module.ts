import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ConsumptionBillingComponent } from './list/consumption-billing.component';
import { ConsumptionBillingDetailComponent } from './detail/consumption-billing-detail.component';
import { ConsumptionBillingUpdateComponent } from './update/consumption-billing-update.component';
import { ConsumptionBillingDeleteDialogComponent } from './delete/consumption-billing-delete-dialog.component';
import { ConsumptionBillingRoutingModule } from './route/consumption-billing-routing.module';

@NgModule({
  imports: [SharedModule, ConsumptionBillingRoutingModule],
  declarations: [
    ConsumptionBillingComponent,
    ConsumptionBillingDetailComponent,
    ConsumptionBillingUpdateComponent,
    ConsumptionBillingDeleteDialogComponent,
  ],
  entryComponents: [ConsumptionBillingDeleteDialogComponent],
})
export class ConsumptionBillingModule {}
