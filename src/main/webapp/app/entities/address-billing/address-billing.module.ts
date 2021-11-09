import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AddressBillingComponent } from './list/address-billing.component';
import { AddressBillingDetailComponent } from './detail/address-billing-detail.component';
import { AddressBillingUpdateComponent } from './update/address-billing-update.component';
import { AddressBillingDeleteDialogComponent } from './delete/address-billing-delete-dialog.component';
import { AddressBillingRoutingModule } from './route/address-billing-routing.module';

@NgModule({
  imports: [SharedModule, AddressBillingRoutingModule],
  declarations: [
    AddressBillingComponent,
    AddressBillingDetailComponent,
    AddressBillingUpdateComponent,
    AddressBillingDeleteDialogComponent,
  ],
  entryComponents: [AddressBillingDeleteDialogComponent],
})
export class AddressBillingModule {}
