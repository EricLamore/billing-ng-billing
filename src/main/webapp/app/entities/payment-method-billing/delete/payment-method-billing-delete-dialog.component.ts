import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPaymentMethodBilling } from '../payment-method-billing.model';
import { PaymentMethodBillingService } from '../service/payment-method-billing.service';

@Component({
  templateUrl: './payment-method-billing-delete-dialog.component.html',
})
export class PaymentMethodBillingDeleteDialogComponent {
  paymentMethod?: IPaymentMethodBilling;

  constructor(protected paymentMethodService: PaymentMethodBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.paymentMethodService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
