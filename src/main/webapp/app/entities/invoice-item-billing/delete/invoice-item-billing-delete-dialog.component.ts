import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInvoiceItemBilling } from '../invoice-item-billing.model';
import { InvoiceItemBillingService } from '../service/invoice-item-billing.service';

@Component({
  templateUrl: './invoice-item-billing-delete-dialog.component.html',
})
export class InvoiceItemBillingDeleteDialogComponent {
  invoiceItem?: IInvoiceItemBilling;

  constructor(protected invoiceItemService: InvoiceItemBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.invoiceItemService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
