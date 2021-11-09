import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductRatePlanBilling } from '../product-rate-plan-billing.model';
import { ProductRatePlanBillingService } from '../service/product-rate-plan-billing.service';

@Component({
  templateUrl: './product-rate-plan-billing-delete-dialog.component.html',
})
export class ProductRatePlanBillingDeleteDialogComponent {
  productRatePlan?: IProductRatePlanBilling;

  constructor(protected productRatePlanService: ProductRatePlanBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productRatePlanService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
