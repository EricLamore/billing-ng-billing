import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductBillingComponent } from '../list/product-billing.component';
import { ProductBillingDetailComponent } from '../detail/product-billing-detail.component';
import { ProductBillingUpdateComponent } from '../update/product-billing-update.component';
import { ProductBillingRoutingResolveService } from './product-billing-routing-resolve.service';

const productRoute: Routes = [
  {
    path: '',
    component: ProductBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductBillingDetailComponent,
    resolve: {
      product: ProductBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductBillingUpdateComponent,
    resolve: {
      product: ProductBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductBillingUpdateComponent,
    resolve: {
      product: ProductBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productRoute)],
  exports: [RouterModule],
})
export class ProductBillingRoutingModule {}
