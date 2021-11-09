import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISettingsInvoiceBilling, SettingsInvoiceBilling } from '../settings-invoice-billing.model';
import { SettingsInvoiceBillingService } from '../service/settings-invoice-billing.service';

@Injectable({ providedIn: 'root' })
export class SettingsInvoiceBillingRoutingResolveService implements Resolve<ISettingsInvoiceBilling> {
  constructor(protected service: SettingsInvoiceBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISettingsInvoiceBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((settingsInvoice: HttpResponse<SettingsInvoiceBilling>) => {
          if (settingsInvoice.body) {
            return of(settingsInvoice.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SettingsInvoiceBilling());
  }
}
