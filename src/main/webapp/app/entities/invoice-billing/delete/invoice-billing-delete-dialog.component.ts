import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceBilling } from '../invoice-billing.model';
import { InvoiceBillingService } from '../service/invoice-billing.service';

@Component({
  templateUrl: './invoice-billing-delete-dialog.component.html',
})
export class InvoiceBillingDeleteDialogComponent {
  invoice?: IInvoiceBilling;

  constructor(protected invoiceService: InvoiceBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.invoiceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
