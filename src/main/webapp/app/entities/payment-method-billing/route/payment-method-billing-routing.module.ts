import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PaymentMethodBillingComponent } from '../list/payment-method-billing.component';
import { PaymentMethodBillingDetailComponent } from '../detail/payment-method-billing-detail.component';
import { PaymentMethodBillingUpdateComponent } from '../update/payment-method-billing-update.component';
import { PaymentMethodBillingRoutingResolveService } from './payment-method-billing-routing-resolve.service';

const paymentMethodRoute: Routes = [
  {
    path: '',
    component: PaymentMethodBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PaymentMethodBillingDetailComponent,
    resolve: {
      paymentMethod: PaymentMethodBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PaymentMethodBillingUpdateComponent,
    resolve: {
      paymentMethod: PaymentMethodBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PaymentMethodBillingUpdateComponent,
    resolve: {
      paymentMethod: PaymentMethodBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(paymentMethodRoute)],
  exports: [RouterModule],
})
export class PaymentMethodBillingRoutingModule {}
