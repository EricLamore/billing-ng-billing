import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ProductSettingBillingComponent } from '../list/product-setting-billing.component';
import { ProductSettingBillingDetailComponent } from '../detail/product-setting-billing-detail.component';
import { ProductSettingBillingUpdateComponent } from '../update/product-setting-billing-update.component';
import { ProductSettingBillingRoutingResolveService } from './product-setting-billing-routing-resolve.service';

const productSettingRoute: Routes = [
  {
    path: '',
    component: ProductSettingBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ProductSettingBillingDetailComponent,
    resolve: {
      productSetting: ProductSettingBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ProductSettingBillingUpdateComponent,
    resolve: {
      productSetting: ProductSettingBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ProductSettingBillingUpdateComponent,
    resolve: {
      productSetting: ProductSettingBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(productSettingRoute)],
  exports: [RouterModule],
})
export class ProductSettingBillingRoutingModule {}
