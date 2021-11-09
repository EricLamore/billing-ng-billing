import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { InvoiceItemBillingComponent } from '../list/invoice-item-billing.component';
import { InvoiceItemBillingDetailComponent } from '../detail/invoice-item-billing-detail.component';
import { InvoiceItemBillingUpdateComponent } from '../update/invoice-item-billing-update.component';
import { InvoiceItemBillingRoutingResolveService } from './invoice-item-billing-routing-resolve.service';

const invoiceItemRoute: Routes = [
  {
    path: '',
    component: InvoiceItemBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: InvoiceItemBillingDetailComponent,
    resolve: {
      invoiceItem: InvoiceItemBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: InvoiceItemBillingUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: InvoiceItemBillingUpdateComponent,
    resolve: {
      invoiceItem: InvoiceItemBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(invoiceItemRoute)],
  exports: [RouterModule],
})
export class InvoiceItemBillingRoutingModule {}
