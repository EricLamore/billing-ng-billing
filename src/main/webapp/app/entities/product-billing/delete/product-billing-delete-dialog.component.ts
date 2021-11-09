import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductBilling } from '../product-billing.model';
import { ProductBillingService } from '../service/product-billing.service';

@Component({
  templateUrl: './product-billing-delete-dialog.component.html',
})
export class ProductBillingDeleteDialogComponent {
  product?: IProductBilling;

  constructor(protected productService: ProductBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
