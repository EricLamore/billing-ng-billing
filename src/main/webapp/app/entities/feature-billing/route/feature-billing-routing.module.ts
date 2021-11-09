import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { FeatureBillingComponent } from '../list/feature-billing.component';
import { FeatureBillingDetailComponent } from '../detail/feature-billing-detail.component';
import { FeatureBillingUpdateComponent } from '../update/feature-billing-update.component';
import { FeatureBillingRoutingResolveService } from './feature-billing-routing-resolve.service';

const featureRoute: Routes = [
  {
    path: '',
    component: FeatureBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FeatureBillingDetailComponent,
    resolve: {
      feature: FeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FeatureBillingUpdateComponent,
    resolve: {
      feature: FeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FeatureBillingUpdateComponent,
    resolve: {
      feature: FeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(featureRoute)],
  exports: [RouterModule],
})
export class FeatureBillingRoutingModule {}
