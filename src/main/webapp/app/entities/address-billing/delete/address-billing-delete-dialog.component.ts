import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAddressBilling } from '../address-billing.model';
import { AddressBillingService } from '../service/address-billing.service';

@Component({
  templateUrl: './address-billing-delete-dialog.component.html',
})
export class AddressBillingDeleteDialogComponent {
  address?: IAddressBilling;

  constructor(protected addressService: AddressBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.addressService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
