import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInvoiceBilling, InvoiceBilling } from '../invoice-billing.model';
import { InvoiceBillingService } from '../service/invoice-billing.service';

@Injectable({ providedIn: 'root' })
export class InvoiceBillingRoutingResolveService implements Resolve<IInvoiceBilling> {
  constructor(protected service: InvoiceBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((invoice: HttpResponse<InvoiceBilling>) => {
          if (invoice.body) {
            return of(invoice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceBilling());
  }
}
