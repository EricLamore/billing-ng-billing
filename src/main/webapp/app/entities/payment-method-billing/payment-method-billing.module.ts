import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PaymentMethodBillingComponent } from './list/payment-method-billing.component';
import { PaymentMethodBillingDetailComponent } from './detail/payment-method-billing-detail.component';
import { PaymentMethodBillingUpdateComponent } from './update/payment-method-billing-update.component';
import { PaymentMethodBillingDeleteDialogComponent } from './delete/payment-method-billing-delete-dialog.component';
import { PaymentMethodBillingRoutingModule } from './route/payment-method-billing-routing.module';

@NgModule({
  imports: [SharedModule, PaymentMethodBillingRoutingModule],
  declarations: [
    PaymentMethodBillingComponent,
    PaymentMethodBillingDetailComponent,
    PaymentMethodBillingUpdateComponent,
    PaymentMethodBillingDeleteDialogComponent,
  ],
  entryComponents: [PaymentMethodBillingDeleteDialogComponent],
})
export class PaymentMethodBillingModule {}
