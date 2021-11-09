import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InvoiceBillingComponent } from '../list/invoice-billing.component';
import { InvoiceBillingDetailComponent } from '../detail/invoice-billing-detail.component';
import { InvoiceBillingUpdateComponent } from '../update/invoice-billing-update.component';
import { InvoiceBillingRoutingResolveService } from './invoice-billing-routing-resolve.service';

const invoiceRoute: Routes = [
  {
    path: '',
    component: InvoiceBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InvoiceBillingDetailComponent,
    resolve: {
      invoice: InvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InvoiceBillingUpdateComponent,
    resolve: {
      invoice: InvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InvoiceBillingUpdateComponent,
    resolve: {
      invoice: InvoiceBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(invoiceRoute)],
  exports: [RouterModule],
})
export class InvoiceBillingRoutingModule {}
