import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubscriptionFeatureBillingComponent } from '../list/subscription-feature-billing.component';
import { SubscriptionFeatureBillingDetailComponent } from '../detail/subscription-feature-billing-detail.component';
import { SubscriptionFeatureBillingUpdateComponent } from '../update/subscription-feature-billing-update.component';
import { SubscriptionFeatureBillingRoutingResolveService } from './subscription-feature-billing-routing-resolve.service';

const subscriptionFeatureRoute: Routes = [
  {
    path: '',
    component: SubscriptionFeatureBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubscriptionFeatureBillingDetailComponent,
    resolve: {
      subscriptionFeature: SubscriptionFeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubscriptionFeatureBillingUpdateComponent,
    resolve: {
      subscriptionFeature: SubscriptionFeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubscriptionFeatureBillingUpdateComponent,
    resolve: {
      subscriptionFeature: SubscriptionFeatureBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subscriptionFeatureRoute)],
  exports: [RouterModule],
})
export class SubscriptionFeatureBillingRoutingModule {}
