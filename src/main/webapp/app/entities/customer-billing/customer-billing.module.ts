import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { CustomerBillingComponent } from './list/customer-billing.component';
import { CustomerBillingDetailComponent } from './detail/customer-billing-detail.component';
import { CustomerBillingUpdateComponent } from './update/customer-billing-update.component';
import { CustomerBillingDeleteDialogComponent } from './delete/customer-billing-delete-dialog.component';
import { CustomerBillingRoutingModule } from './route/customer-billing-routing.module';

@NgModule({
  imports: [SharedModule, CustomerBillingRoutingModule],
  declarations: [
    CustomerBillingComponent,
    CustomerBillingDetailComponent,
    CustomerBillingUpdateComponent,
    CustomerBillingDeleteDialogComponent,
  ],
  entryComponents: [CustomerBillingDeleteDialogComponent],
})
export class CustomerBillingModule {}
