import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CustomerBillingComponent } from '../list/customer-billing.component';
import { CustomerBillingDetailComponent } from '../detail/customer-billing-detail.component';
import { CustomerBillingUpdateComponent } from '../update/customer-billing-update.component';
import { CustomerBillingRoutingResolveService } from './customer-billing-routing-resolve.service';

const customerRoute: Routes = [
  {
    path: '',
    component: CustomerBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustomerBillingDetailComponent,
    resolve: {
      customer: CustomerBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustomerBillingUpdateComponent,
    resolve: {
      customer: CustomerBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustomerBillingUpdateComponent,
    resolve: {
      customer: CustomerBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(customerRoute)],
  exports: [RouterModule],
})
export class CustomerBillingRoutingModule {}
