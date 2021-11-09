import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PersonRefererBillingComponent } from '../list/person-referer-billing.component';
import { PersonRefererBillingDetailComponent } from '../detail/person-referer-billing-detail.component';
import { PersonRefererBillingUpdateComponent } from '../update/person-referer-billing-update.component';
import { PersonRefererBillingRoutingResolveService } from './person-referer-billing-routing-resolve.service';

const personRefererRoute: Routes = [
  {
    path: '',
    component: PersonRefererBillingComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PersonRefererBillingDetailComponent,
    resolve: {
      personReferer: PersonRefererBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PersonRefererBillingUpdateComponent,
    resolve: {
      personReferer: PersonRefererBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PersonRefererBillingUpdateComponent,
    resolve: {
      personReferer: PersonRefererBillingRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(personRefererRoute)],
  exports: [RouterModule],
})
export class PersonRefererBillingRoutingModule {}
