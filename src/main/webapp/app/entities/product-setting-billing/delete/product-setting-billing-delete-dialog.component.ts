import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductSettingBilling } from '../product-setting-billing.model';
import { ProductSettingBillingService } from '../service/product-setting-billing.service';

@Component({
  templateUrl: './product-setting-billing-delete-dialog.component.html',
})
export class ProductSettingBillingDeleteDialogComponent {
  productSetting?: IProductSettingBilling;

  constructor(protected productSettingService: ProductSettingBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.productSettingService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
