import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICustomerBilling } from '../customer-billing.model';
import { CustomerBillingService } from '../service/customer-billing.service';

@Component({
  templateUrl: './customer-billing-delete-dialog.component.html',
})
export class CustomerBillingDeleteDialogComponent {
  customer?: ICustomerBilling;

  constructor(protected customerService: CustomerBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.customerService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
