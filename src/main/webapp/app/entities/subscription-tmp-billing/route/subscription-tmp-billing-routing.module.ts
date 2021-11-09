import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SubscriptionTmpBillingComponent } from '../list/subscription-tmp-billing.component';
import { SubscriptionTmpBillingDetailComponent } from '../detail/subscription-tmp-billing-detail.component';
import { SubscriptionTmpBillingUpdateComponent } from '../update/subscription-tmp-billing-update.component';
import { SubscriptionTmpBillingRoutingResolveService } from './subscription-tmp-billing-routing-resolve.service';

const subscriptionTmpRoute: Routes = [
  {
    path: '',
    component: SubscriptionTmpBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SubscriptionTmpBillingDetailComponent,
    resolve: {
      subscriptionTmp: SubscriptionTmpBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SubscriptionTmpBillingUpdateComponent,
    resolve: {
      subscriptionTmp: SubscriptionTmpBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SubscriptionTmpBillingUpdateComponent,
    resolve: {
      subscriptionTmp: SubscriptionTmpBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(subscriptionTmpRoute)],
  exports: [RouterModule],
})
export class SubscriptionTmpBillingRoutingModule {}
