import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISettingsInvoiceBilling } from '../settings-invoice-billing.model';
import { SettingsInvoiceBillingService } from '../service/settings-invoice-billing.service';

@Component({
  templateUrl: './settings-invoice-billing-delete-dialog.component.html',
})
export class SettingsInvoiceBillingDeleteDialogComponent {
  settingsInvoice?: ISettingsInvoiceBilling;

  constructor(protected settingsInvoiceService: SettingsInvoiceBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.settingsInvoiceService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
