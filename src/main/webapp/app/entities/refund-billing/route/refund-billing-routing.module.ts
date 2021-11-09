import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RefundBillingComponent } from '../list/refund-billing.component';
import { RefundBillingDetailComponent } from '../detail/refund-billing-detail.component';
import { RefundBillingUpdateComponent } from '../update/refund-billing-update.component';
import { RefundBillingRoutingResolveService } from './refund-billing-routing-resolve.service';

const refundRoute: Routes = [
  {
    path: '',
    component: RefundBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RefundBillingDetailComponent,
    resolve: {
      refund: RefundBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RefundBillingUpdateComponent,
    resolve: {
      refund: RefundBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RefundBillingUpdateComponent,
    resolve: {
      refund: RefundBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(refundRoute)],
  exports: [RouterModule],
})
export class RefundBillingRoutingModule {}
