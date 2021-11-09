import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SettingsInvoiceBillingComponent } from './list/settings-invoice-billing.component';
import { SettingsInvoiceBillingDetailComponent } from './detail/settings-invoice-billing-detail.component';
import { SettingsInvoiceBillingUpdateComponent } from './update/settings-invoice-billing-update.component';
import { SettingsInvoiceBillingDeleteDialogComponent } from './delete/settings-invoice-billing-delete-dialog.component';
import { SettingsInvoiceBillingRoutingModule } from './route/settings-invoice-billing-routing.module';

@NgModule({
  imports: [SharedModule, SettingsInvoiceBillingRoutingModule],
  declarations: [
    SettingsInvoiceBillingComponent,
    SettingsInvoiceBillingDetailComponent,
    SettingsInvoiceBillingUpdateComponent,
    SettingsInvoiceBillingDeleteDialogComponent,
  ],
  entryComponents: [SettingsInvoiceBillingDeleteDialogComponent],
})
export class SettingsInvoiceBillingModule {}
