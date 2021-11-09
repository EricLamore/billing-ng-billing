import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFeatureBilling, FeatureBilling } from '../feature-billing.model';
import { FeatureBillingService } from '../service/feature-billing.service';

@Injectable({ providedIn: 'root' })
export class FeatureBillingRoutingResolveService implements Resolve<IFeatureBilling> {
  constructor(protected service: FeatureBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFeatureBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((feature: HttpResponse<FeatureBilling>) => {
          if (feature.body) {
            return of(feature.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FeatureBilling());
  }
}
