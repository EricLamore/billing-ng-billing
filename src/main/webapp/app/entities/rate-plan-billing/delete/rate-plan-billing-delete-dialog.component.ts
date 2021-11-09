import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRatePlanBilling } from '../rate-plan-billing.model';
import { RatePlanBillingService } from '../service/rate-plan-billing.service';

@Component({
  templateUrl: './rate-plan-billing-delete-dialog.component.html',
})
export class RatePlanBillingDeleteDialogComponent {
  ratePlan?: IRatePlanBilling;

  constructor(protected ratePlanService: RatePlanBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.ratePlanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
