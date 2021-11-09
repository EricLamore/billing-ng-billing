import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingsInvoiceBillingComponent } from '../list/settings-invoice-billing.component';
import { SettingsInvoiceBillingDetailComponent } from '../detail/settings-invoice-billing-detail.component';
import { SettingsInvoiceBillingUpdateComponent } from '../update/settings-invoice-billing-update.component';
import { SettingsInvoiceBillingRoutingResolveService } from './settings-invoice-billing-routing-resolve.service';

const settingsInvoiceRoute: Routes = [
  {
    path: '',
    component: SettingsInvoiceBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SettingsInvoiceBillingDetailComponent,
    resolve: {
      settingsInvoice: SettingsInvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SettingsInvoiceBillingUpdateComponent,
    resolve: {
      settingsInvoice: SettingsInvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SettingsInvoiceBillingUpdateComponent,
    resolve: {
      settingsInvoice: SettingsInvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingsInvoiceRoute)],
  exports: [RouterModule],
})
export class SettingsInvoiceBillingRoutingModule {}
