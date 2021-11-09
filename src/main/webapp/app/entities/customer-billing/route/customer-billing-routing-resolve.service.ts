import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustomerBilling, CustomerBilling } from '../customer-billing.model';
import { CustomerBillingService } from '../service/customer-billing.service';

@Injectable({ providedIn: 'root' })
export class CustomerBillingRoutingResolveService implements Resolve<ICustomerBilling> {
  constructor(protected service: CustomerBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICustomerBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((customer: HttpResponse<CustomerBilling>) => {
          if (customer.body) {
            return of(customer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CustomerBilling());
  }
}
