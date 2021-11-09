import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IRefundBilling, RefundBilling } from '../refund-billing.model';
import { RefundBillingService } from '../service/refund-billing.service';

@Injectable({ providedIn: 'root' })
export class RefundBillingRoutingResolveService implements Resolve<IRefundBilling> {
  constructor(protected service: RefundBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IRefundBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((refund: HttpResponse<RefundBilling>) => {
          if (refund.body) {
            return of(refund.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new RefundBilling());
  }
}
