import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISubscriptionFeatureBilling } from '../subscription-feature-billing.model';
import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';

@Component({
  templateUrl: './subscription-feature-billing-delete-dialog.component.html',
})
export class SubscriptionFeatureBillingDeleteDialogComponent {
  subscriptionFeature?: ISubscriptionFeatureBilling;

  constructor(protected subscriptionFeatureService: SubscriptionFeatureBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.subscriptionFeatureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
