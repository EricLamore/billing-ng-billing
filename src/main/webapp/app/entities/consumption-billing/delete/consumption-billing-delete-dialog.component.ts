import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IConsumptionBilling } from '../consumption-billing.model';
import { ConsumptionBillingService } from '../service/consumption-billing.service';

@Component({
  templateUrl: './consumption-billing-delete-dialog.component.html',
})
export class ConsumptionBillingDeleteDialogComponent {
  consumption?: IConsumptionBilling;

  constructor(protected consumptionService: ConsumptionBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.consumptionService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
