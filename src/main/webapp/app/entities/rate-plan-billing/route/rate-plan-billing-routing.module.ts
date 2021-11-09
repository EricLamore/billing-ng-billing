import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { RatePlanBillingComponent } from '../list/rate-plan-billing.component';
import { RatePlanBillingDetailComponent } from '../detail/rate-plan-billing-detail.component';
import { RatePlanBillingUpdateComponent } from '../update/rate-plan-billing-update.component';
import { RatePlanBillingRoutingResolveService } from './rate-plan-billing-routing-resolve.service';

const ratePlanRoute: Routes = [
  {
    path: '',
    component: RatePlanBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: RatePlanBillingDetailComponent,
    resolve: {
      ratePlan: RatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: RatePlanBillingUpdateComponent,
    resolve: {
      ratePlan: RatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: RatePlanBillingUpdateComponent,
    resolve: {
      ratePlan: RatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ratePlanRoute)],
  exports: [RouterModule],
})
export class RatePlanBillingRoutingModule {}
