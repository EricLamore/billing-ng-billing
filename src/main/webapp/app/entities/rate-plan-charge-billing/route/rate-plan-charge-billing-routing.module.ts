import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RatePlanChargeBillingComponent } from '../list/rate-plan-charge-billing.component';
import { RatePlanChargeBillingDetailComponent } from '../detail/rate-plan-charge-billing-detail.component';
import { RatePlanChargeBillingUpdateComponent } from '../update/rate-plan-charge-billing-update.component';
import { RatePlanChargeBillingRoutingResolveService } from './rate-plan-charge-billing-routing-resolve.service';

const ratePlanChargeRoute: Routes = [
  {
    path: '',
    component: RatePlanChargeBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RatePlanChargeBillingDetailComponent,
    resolve: {
      ratePlanCharge: RatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RatePlanChargeBillingUpdateComponent,
    resolve: {
      ratePlanCharge: RatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RatePlanChargeBillingUpdateComponent,
    resolve: {
      ratePlanCharge: RatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ratePlanChargeRoute)],
  exports: [RouterModule],
})
export class RatePlanChargeBillingRoutingModule {}
