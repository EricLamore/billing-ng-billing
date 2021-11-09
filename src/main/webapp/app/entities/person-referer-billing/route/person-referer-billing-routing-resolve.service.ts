import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPersonRefererBilling, PersonRefererBilling } from '../person-referer-billing.model';
import { PersonRefererBillingService } from '../service/person-referer-billing.service';

@Injectable({ providedIn: 'root' })
export class PersonRefererBillingRoutingResolveService implements Resolve<IPersonRefererBilling> {
  constructor(protected service: PersonRefererBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPersonRefererBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((personReferer: HttpResponse<PersonRefererBilling>) => {
          if (personReferer.body) {
            return of(personReferer.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PersonRefererBilling());
  }
}
