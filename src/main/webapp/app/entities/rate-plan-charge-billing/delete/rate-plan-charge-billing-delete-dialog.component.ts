import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IRatePlanChargeBilling } from '../rate-plan-charge-billing.model';
import { RatePlanChargeBillingService } from '../service/rate-plan-charge-billing.service';

@Component({
  templateUrl: './rate-plan-charge-billing-delete-dialog.component.html',
})
export class RatePlanChargeBillingDeleteDialogComponent {
  ratePlanCharge?: IRatePlanChargeBilling;

  constructor(protected ratePlanChargeService: RatePlanChargeBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.ratePlanChargeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
