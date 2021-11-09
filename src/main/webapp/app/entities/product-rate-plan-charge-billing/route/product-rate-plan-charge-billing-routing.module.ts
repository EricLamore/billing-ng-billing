import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductRatePlanChargeBillingComponent } from '../list/product-rate-plan-charge-billing.component';
import { ProductRatePlanChargeBillingDetailComponent } from '../detail/product-rate-plan-charge-billing-detail.component';
import { ProductRatePlanChargeBillingUpdateComponent } from '../update/product-rate-plan-charge-billing-update.component';
import { ProductRatePlanChargeBillingRoutingResolveService } from './product-rate-plan-charge-billing-routing-resolve.service';

const productRatePlanChargeRoute: Routes = [
  {
    path: '',
    component: ProductRatePlanChargeBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductRatePlanChargeBillingDetailComponent,
    resolve: {
      productRatePlanCharge: ProductRatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductRatePlanChargeBillingUpdateComponent,
    resolve: {
      productRatePlanCharge: ProductRatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductRatePlanChargeBillingUpdateComponent,
    resolve: {
      productRatePlanCharge: ProductRatePlanChargeBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRatePlanChargeRoute)],
  exports: [RouterModule],
})
export class ProductRatePlanChargeBillingRoutingModule {}
