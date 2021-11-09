import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConsumptionBillingComponent } from '../list/consumption-billing.component';
import { ConsumptionBillingDetailComponent } from '../detail/consumption-billing-detail.component';
import { ConsumptionBillingUpdateComponent } from '../update/consumption-billing-update.component';
import { ConsumptionBillingRoutingResolveService } from './consumption-billing-routing-resolve.service';

const consumptionRoute: Routes = [
  {
    path: '',
    component: ConsumptionBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConsumptionBillingDetailComponent,
    resolve: {
      consumption: ConsumptionBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConsumptionBillingUpdateComponent,
    resolve: {
      consumption: ConsumptionBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConsumptionBillingUpdateComponent,
    resolve: {
      consumption: ConsumptionBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(consumptionRoute)],
  exports: [RouterModule],
})
export class ConsumptionBillingRoutingModule {}
