import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IPersonRefererBilling } from '../person-referer-billing.model';
import { PersonRefererBillingService } from '../service/person-referer-billing.service';

@Component({
  templateUrl: './person-referer-billing-delete-dialog.component.html',
})
export class PersonRefererBillingDeleteDialogComponent {
  personReferer?: IPersonRefererBilling;

  constructor(protected personRefererService: PersonRefererBillingService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.personRefererService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
