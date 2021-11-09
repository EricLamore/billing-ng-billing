import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { InvoiceItemBillingComponent } from './list/invoice-item-billing.component';
import { InvoiceItemBillingDetailComponent } from './detail/invoice-item-billing-detail.component';
import { InvoiceItemBillingUpdateComponent } from './update/invoice-item-billing-update.component';
import { InvoiceItemBillingDeleteDialogComponent } from './delete/invoice-item-billing-delete-dialog.component';
import { InvoiceItemBillingRoutingModule } from './route/invoice-item-billing-routing.module';

@NgModule({
  imports: [SharedModule, InvoiceItemBillingRoutingModule],
  declarations: [
    InvoiceItemBillingComponent,
    InvoiceItemBillingDetailComponent,
    InvoiceItemBillingUpdateComponent,
    InvoiceItemBillingDeleteDialogComponent,
  ],
  entryComponents: [InvoiceItemBillingDeleteDialogComponent],
})
export class InvoiceItemBillingModule {}
