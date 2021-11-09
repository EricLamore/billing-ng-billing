import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IOrganizationBilling } from '../organization-billing.model';
import { OrganizationBillingService } from '../service/organization-billing.service';

@Component({
  templateUrl: './organization-billing-delete-dialog.component.html',
})
export class OrganizationBillingDeleteDialogComponent {
  organization?: IOrganizationBilling;

  constructor(protected organizationService: OrganizationBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.organizationService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
