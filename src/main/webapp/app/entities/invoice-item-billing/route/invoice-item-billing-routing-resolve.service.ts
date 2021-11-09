import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IInvoiceItemBilling, InvoiceItemBilling } from '../invoice-item-billing.model';
import { InvoiceItemBillingService } from '../service/invoice-item-billing.service';

@Injectable({ providedIn: 'root' })
export class InvoiceItemBillingRoutingResolveService implements Resolve<IInvoiceItemBilling> {
  constructor(protected service: InvoiceItemBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IInvoiceItemBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((invoiceItem: HttpResponse<InvoiceItemBilling>) => {
          if (invoiceItem.body) {
            return of(invoiceItem.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new InvoiceItemBilling());
  }
}
