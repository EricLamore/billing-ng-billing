import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { SettingsInvoiceUnitBillingComponent } from './list/settings-invoice-unit-billing.component';
import { SettingsInvoiceUnitBillingDetailComponent } from './detail/settings-invoice-unit-billing-detail.component';
import { SettingsInvoiceUnitBillingUpdateComponent } from './update/settings-invoice-unit-billing-update.component';
import { SettingsInvoiceUnitBillingDeleteDialogComponent } from './delete/settings-invoice-unit-billing-delete-dialog.component';
import { SettingsInvoiceUnitBillingRoutingModule } from './route/settings-invoice-unit-billing-routing.module';

@NgModule({
  imports: [SharedModule, SettingsInvoiceUnitBillingRoutingModule],
  declarations: [
    SettingsInvoiceUnitBillingComponent,
    SettingsInvoiceUnitBillingDetailComponent,
    SettingsInvoiceUnitBillingUpdateComponent,
    SettingsInvoiceUnitBillingDeleteDialogComponent,
  ],
  entryComponents: [SettingsInvoiceUnitBillingDeleteDialogComponent],
})
export class SettingsInvoiceUnitBillingModule {}
