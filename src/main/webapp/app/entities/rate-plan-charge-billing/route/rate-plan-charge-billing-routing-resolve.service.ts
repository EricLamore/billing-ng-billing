import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRatePlanChargeBilling, RatePlanChargeBilling } from '../rate-plan-charge-billing.model';
import { RatePlanChargeBillingService } from '../service/rate-plan-charge-billing.service';

@Injectable({ providedIn: 'root' })
export class RatePlanChargeBillingRoutingResolveService implements Resolve<IRatePlanChargeBilling> {
  constructor(protected service: RatePlanChargeBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRatePlanChargeBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ratePlanCharge: HttpResponse<RatePlanChargeBilling>) => {
          if (ratePlanCharge.body) {
            return of(ratePlanCharge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RatePlanChargeBilling());
  }
}
