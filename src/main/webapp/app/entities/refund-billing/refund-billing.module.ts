import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { RefundBillingComponent } from './list/refund-billing.component';
import { RefundBillingDetailComponent } from './detail/refund-billing-detail.component';
import { RefundBillingUpdateComponent } from './update/refund-billing-update.component';
import { RefundBillingDeleteDialogComponent } from './delete/refund-billing-delete-dialog.component';
import { RefundBillingRoutingModule } from './route/refund-billing-routing.module';

@NgModule({
  imports: [SharedModule, RefundBillingRoutingModule],
  declarations: [RefundBillingComponent, RefundBillingDetailComponent, RefundBillingUpdateComponent, RefundBillingDeleteDialogComponent],
  entryComponents: [RefundBillingDeleteDialogComponent],
})
export class RefundBillingModule {}
