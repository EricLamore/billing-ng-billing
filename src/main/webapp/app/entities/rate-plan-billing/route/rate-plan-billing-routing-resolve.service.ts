import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRatePlanBilling, RatePlanBilling } from '../rate-plan-billing.model';
import { RatePlanBillingService } from '../service/rate-plan-billing.service';

@Injectable({ providedIn: 'root' })
export class RatePlanBillingRoutingResolveService implements Resolve<IRatePlanBilling> {
  constructor(protected service: RatePlanBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRatePlanBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ratePlan: HttpResponse<RatePlanBilling>) => {
          if (ratePlan.body) {
            return of(ratePlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RatePlanBilling());
  }
}
