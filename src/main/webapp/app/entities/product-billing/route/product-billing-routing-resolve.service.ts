import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductBilling, ProductBilling } from '../product-billing.model';
import { ProductBillingService } from '../service/product-billing.service';

@Injectable({ providedIn: 'root' })
export class ProductBillingRoutingResolveService implements Resolve<IProductBilling> {
  constructor(protected service: ProductBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((product: HttpResponse<ProductBilling>) => {
          if (product.body) {
            return of(product.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductBilling());
  }
}
