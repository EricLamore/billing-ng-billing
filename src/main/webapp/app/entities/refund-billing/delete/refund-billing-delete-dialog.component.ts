import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRefundBilling } from '../refund-billing.model';
import { RefundBillingService } from '../service/refund-billing.service';

@Component({
  templateUrl: './refund-billing-delete-dialog.component.html',
})
export class RefundBillingDeleteDialogComponent {
  refund?: IRefundBilling;

  constructor(protected refundService: RefundBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.refundService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
