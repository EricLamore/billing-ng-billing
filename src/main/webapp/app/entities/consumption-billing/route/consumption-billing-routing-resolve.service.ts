import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConsumptionBilling, ConsumptionBilling } from '../consumption-billing.model';
import { ConsumptionBillingService } from '../service/consumption-billing.service';

@Injectable({ providedIn: 'root' })
export class ConsumptionBillingRoutingResolveService implements Resolve<IConsumptionBilling> {
  constructor(protected service: ConsumptionBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConsumptionBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((consumption: HttpResponse<ConsumptionBilling>) => {
          if (consumption.body) {
            return of(consumption.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ConsumptionBilling());
  }
}
