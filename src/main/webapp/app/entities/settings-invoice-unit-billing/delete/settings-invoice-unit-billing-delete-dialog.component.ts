import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';
import { SettingsInvoiceUnitBillingService } from '../service/settings-invoice-unit-billing.service';

@Component({
  templateUrl: './settings-invoice-unit-billing-delete-dialog.component.html',
})
export class SettingsInvoiceUnitBillingDeleteDialogComponent {
  settingsInvoiceUnit?: ISettingsInvoiceUnitBilling;

  constructor(protected settingsInvoiceUnitService: SettingsInvoiceUnitBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.settingsInvoiceUnitService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
