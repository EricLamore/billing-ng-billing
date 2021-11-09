import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { OrganizationBillingComponent } from '../list/organization-billing.component';
import { OrganizationBillingDetailComponent } from '../detail/organization-billing-detail.component';
import { OrganizationBillingUpdateComponent } from '../update/organization-billing-update.component';
import { OrganizationBillingRoutingResolveService } from './organization-billing-routing-resolve.service';

const organizationRoute: Routes = [
  {
    path: '',
    component: OrganizationBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: OrganizationBillingDetailComponent,
    resolve: {
      organization: OrganizationBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: OrganizationBillingUpdateComponent,
    resolve: {
      organization: OrganizationBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: OrganizationBillingUpdateComponent,
    resolve: {
      organization: OrganizationBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(organizationRoute)],
  exports: [RouterModule],
})
export class OrganizationBillingRoutingModule {}
