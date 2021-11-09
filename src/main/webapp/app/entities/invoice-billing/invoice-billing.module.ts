import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InvoiceBillingComponent } from './list/invoice-billing.component';
import { InvoiceBillingDetailComponent } from './detail/invoice-billing-detail.component';
import { InvoiceBillingUpdateComponent } from './update/invoice-billing-update.component';
import { InvoiceBillingDeleteDialogComponent } from './delete/invoice-billing-delete-dialog.component';
import { InvoiceBillingRoutingModule } from './route/invoice-billing-routing.module';

@NgModule({
  imports: [SharedModule, InvoiceBillingRoutingModule],
  declarations: [
    InvoiceBillingComponent,
    InvoiceBillingDetailComponent,
    InvoiceBillingUpdateComponent,
    InvoiceBillingDeleteDialogComponent,
  ],
  entryComponents: [InvoiceBillingDeleteDialogComponent],
})
export class InvoiceBillingModule {}
