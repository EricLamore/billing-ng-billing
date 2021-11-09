import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubscriptionFeatureBilling, SubscriptionFeatureBilling } from '../subscription-feature-billing.model';
import { SubscriptionFeatureBillingService } from '../service/subscription-feature-billing.service';

@Injectable({ providedIn: 'root' })
export class SubscriptionFeatureBillingRoutingResolveService implements Resolve<ISubscriptionFeatureBilling> {
  constructor(protected service: SubscriptionFeatureBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISubscriptionFeatureBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((subscriptionFeature: HttpResponse<SubscriptionFeatureBilling>) => {
          if (subscriptionFeature.body) {
            return of(subscriptionFeature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SubscriptionFeatureBilling());
  }
}
