import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductRatePlanChargeBilling, ProductRatePlanChargeBilling } from '../product-rate-plan-charge-billing.model';
import { ProductRatePlanChargeBillingService } from '../service/product-rate-plan-charge-billing.service';

@Injectable({ providedIn: 'root' })
export class ProductRatePlanChargeBillingRoutingResolveService implements Resolve<IProductRatePlanChargeBilling> {
  constructor(protected service: ProductRatePlanChargeBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductRatePlanChargeBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productRatePlanCharge: HttpResponse<ProductRatePlanChargeBilling>) => {
          if (productRatePlanCharge.body) {
            return of(productRatePlanCharge.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductRatePlanChargeBilling());
  }
}
