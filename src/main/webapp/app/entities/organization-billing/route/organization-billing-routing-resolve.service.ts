import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IOrganizationBilling, OrganizationBilling } from '../organization-billing.model';
import { OrganizationBillingService } from '../service/organization-billing.service';

@Injectable({ providedIn: 'root' })
export class OrganizationBillingRoutingResolveService implements Resolve<IOrganizationBilling> {
  constructor(protected service: OrganizationBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOrganizationBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((organization: HttpResponse<OrganizationBilling>) => {
          if (organization.body) {
            return of(organization.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OrganizationBilling());
  }
}
