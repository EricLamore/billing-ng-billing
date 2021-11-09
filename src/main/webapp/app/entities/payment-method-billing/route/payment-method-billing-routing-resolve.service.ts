import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPaymentMethodBilling, PaymentMethodBilling } from '../payment-method-billing.model';
import { PaymentMethodBillingService } from '../service/payment-method-billing.service';

@Injectable({ providedIn: 'root' })
export class PaymentMethodBillingRoutingResolveService implements Resolve<IPaymentMethodBilling> {
  constructor(protected service: PaymentMethodBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPaymentMethodBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((paymentMethod: HttpResponse<PaymentMethodBilling>) => {
          if (paymentMethod.body) {
            return of(paymentMethod.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PaymentMethodBilling());
  }
}
