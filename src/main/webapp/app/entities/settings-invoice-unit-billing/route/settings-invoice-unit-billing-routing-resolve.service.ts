import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISettingsInvoiceUnitBilling, SettingsInvoiceUnitBilling } from '../settings-invoice-unit-billing.model';
import { SettingsInvoiceUnitBillingService } from '../service/settings-invoice-unit-billing.service';

@Injectable({ providedIn: 'root' })
export class SettingsInvoiceUnitBillingRoutingResolveService implements Resolve<ISettingsInvoiceUnitBilling> {
  constructor(protected service: SettingsInvoiceUnitBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISettingsInvoiceUnitBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((settingsInvoiceUnit: HttpResponse<SettingsInvoiceUnitBilling>) => {
          if (settingsInvoiceUnit.body) {
            return of(settingsInvoiceUnit.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SettingsInvoiceUnitBilling());
  }
}
