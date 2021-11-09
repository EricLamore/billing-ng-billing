import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAddressBilling, AddressBilling } from '../address-billing.model';
import { AddressBillingService } from '../service/address-billing.service';

@Injectable({ providedIn: 'root' })
export class AddressBillingRoutingResolveService implements Resolve<IAddressBilling> {
  constructor(protected service: AddressBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAddressBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((address: HttpResponse<AddressBilling>) => {
          if (address.body) {
            return of(address.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AddressBilling());
  }
}
