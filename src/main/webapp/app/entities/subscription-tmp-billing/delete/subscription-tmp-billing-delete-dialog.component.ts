import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubscriptionTmpBilling } from '../subscription-tmp-billing.model';
import { SubscriptionTmpBillingService } from '../service/subscription-tmp-billing.service';

@Component({
  templateUrl: './subscription-tmp-billing-delete-dialog.component.html',
})
export class SubscriptionTmpBillingDeleteDialogComponent {
  subscriptionTmp?: ISubscriptionTmpBilling;

  constructor(protected subscriptionTmpService: SubscriptionTmpBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.subscriptionTmpService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
