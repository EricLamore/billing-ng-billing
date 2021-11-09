import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';
import { ProductRatePlanChargeBillingService } from '../service/product-rate-plan-charge-billing.service';

@Component({
  templateUrl: './product-rate-plan-charge-billing-delete-dialog.component.html',
})
export class ProductRatePlanChargeBillingDeleteDialogComponent {
  productRatePlanCharge?: IProductRatePlanChargeBilling;

  constructor(protected productRatePlanChargeService: ProductRatePlanChargeBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productRatePlanChargeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
