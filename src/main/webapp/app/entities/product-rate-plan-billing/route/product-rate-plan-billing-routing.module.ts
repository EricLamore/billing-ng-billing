import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductRatePlanBillingComponent } from '../list/product-rate-plan-billing.component';
import { ProductRatePlanBillingDetailComponent } from '../detail/product-rate-plan-billing-detail.component';
import { ProductRatePlanBillingUpdateComponent } from '../update/product-rate-plan-billing-update.component';
import { ProductRatePlanBillingRoutingResolveService } from './product-rate-plan-billing-routing-resolve.service';

const productRatePlanRoute: Routes = [
  {
    path: '',
    component: ProductRatePlanBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductRatePlanBillingDetailComponent,
    resolve: {
      productRatePlan: ProductRatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductRatePlanBillingUpdateComponent,
    resolve: {
      productRatePlan: ProductRatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductRatePlanBillingUpdateComponent,
    resolve: {
      productRatePlan: ProductRatePlanBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRatePlanRoute)],
  exports: [RouterModule],
})
export class ProductRatePlanBillingRoutingModule {}
