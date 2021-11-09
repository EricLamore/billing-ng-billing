import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubscriptionTmpBilling, SubscriptionTmpBilling } from '../subscription-tmp-billing.model';
import { SubscriptionTmpBillingService } from '../service/subscription-tmp-billing.service';

@Injectable({ providedIn: 'root' })
export class SubscriptionTmpBillingRoutingResolveService implements Resolve<ISubscriptionTmpBilling> {
  constructor(protected service: SubscriptionTmpBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubscriptionTmpBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subscriptionTmp: HttpResponse<SubscriptionTmpBilling>) => {
          if (subscriptionTmp.body) {
            return of(subscriptionTmp.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubscriptionTmpBilling());
  }
}
