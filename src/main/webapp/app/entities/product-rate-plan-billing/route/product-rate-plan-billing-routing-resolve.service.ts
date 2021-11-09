import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductRatePlanBilling, ProductRatePlanBilling } from '../product-rate-plan-billing.model';
import { ProductRatePlanBillingService } from '../service/product-rate-plan-billing.service';

@Injectable({ providedIn: 'root' })
export class ProductRatePlanBillingRoutingResolveService implements Resolve<IProductRatePlanBilling> {
  constructor(protected service: ProductRatePlanBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductRatePlanBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productRatePlan: HttpResponse<ProductRatePlanBilling>) => {
          if (productRatePlan.body) {
            return of(productRatePlan.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductRatePlanBilling());
  }
}
