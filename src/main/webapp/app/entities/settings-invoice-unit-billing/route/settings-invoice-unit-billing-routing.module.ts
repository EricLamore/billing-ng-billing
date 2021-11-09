import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingsInvoiceUnitBillingComponent } from '../list/settings-invoice-unit-billing.component';
import { SettingsInvoiceUnitBillingDetailComponent } from '../detail/settings-invoice-unit-billing-detail.component';
import { SettingsInvoiceUnitBillingUpdateComponent } from '../update/settings-invoice-unit-billing-update.component';
import { SettingsInvoiceUnitBillingRoutingResolveService } from './settings-invoice-unit-billing-routing-resolve.service';

const settingsInvoiceUnitRoute: Routes = [
  {
    path: '',
    component: SettingsInvoiceUnitBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SettingsInvoiceUnitBillingDetailComponent,
    resolve: {
      settingsInvoiceUnit: SettingsInvoiceUnitBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SettingsInvoiceUnitBillingUpdateComponent,
    resolve: {
      settingsInvoiceUnit: SettingsInvoiceUnitBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SettingsInvoiceUnitBillingUpdateComponent,
    resolve: {
      settingsInvoiceUnit: SettingsInvoiceUnitBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(settingsInvoiceUnitRoute)],
  exports: [RouterModule],
})
export class SettingsInvoiceUnitBillingRoutingModule {}
