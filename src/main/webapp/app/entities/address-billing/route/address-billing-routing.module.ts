import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AddressBillingComponent } from '../list/address-billing.component';
import { AddressBillingDetailComponent } from '../detail/address-billing-detail.component';
import { AddressBillingUpdateComponent } from '../update/address-billing-update.component';
import { AddressBillingRoutingResolveService } from './address-billing-routing-resolve.service';

const addressRoute: Routes = [
  {
    path: '',
    component: AddressBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AddressBillingDetailComponent,
    resolve: {
      address: AddressBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AddressBillingUpdateComponent,
    resolve: {
      address: AddressBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AddressBillingUpdateComponent,
    resolve: {
      address: AddressBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(addressRoute)],
  exports: [RouterModule],
})
export class AddressBillingRoutingModule {}
