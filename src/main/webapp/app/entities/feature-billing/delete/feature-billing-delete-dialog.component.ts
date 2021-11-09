import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IFeatureBilling } from '../feature-billing.model';
import { FeatureBillingService } from '../service/feature-billing.service';

@Component({
  templateUrl: './feature-billing-delete-dialog.component.html',
})
export class FeatureBillingDeleteDialogComponent {
  feature?: IFeatureBilling;

  constructor(protected featureService: FeatureBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.featureService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
