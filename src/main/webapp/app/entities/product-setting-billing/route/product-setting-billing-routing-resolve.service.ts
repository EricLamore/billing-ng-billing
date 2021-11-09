import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProductSettingBilling, ProductSettingBilling } from '../product-setting-billing.model';
import { ProductSettingBillingService } from '../service/product-setting-billing.service';

@Injectable({ providedIn: 'root' })
export class ProductSettingBillingRoutingResolveService implements Resolve<IProductSettingBilling> {
  constructor(protected service: ProductSettingBillingService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductSettingBilling> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((productSetting: HttpResponse<ProductSettingBilling>) => {
          if (productSetting.body) {
            return of(productSetting.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductSettingBilling());
  }
}
