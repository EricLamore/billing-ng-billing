import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductRatePlanChargeBilling, getProductRatePlanChargeBillingIdentifier } from '../product-rate-plan-charge-billing.model';

export type EntityResponseType = HttpResponse<IProductRatePlanChargeBilling>;
export type EntityArrayResponseType = HttpResponse<IProductRatePlanChargeBilling[]>;

@Injectable({ providedIn: 'root' })
export class ProductRatePlanChargeBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-rate-plan-charges');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productRatePlanCharge: IProductRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.post<IProductRatePlanChargeBilling>(this.resourceUrl, productRatePlanCharge, { observe: 'response' });
  }

  update(productRatePlanCharge: IProductRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.put<IProductRatePlanChargeBilling>(
      `${this.resourceUrl}/${getProductRatePlanChargeBillingIdentifier(productRatePlanCharge) as string}`,
      productRatePlanCharge,
      { observe: 'response' }
    );
  }

  partialUpdate(productRatePlanCharge: IProductRatePlanChargeBilling): Observable<EntityResponseType> {
    return this.http.patch<IProductRatePlanChargeBilling>(
      `${this.resourceUrl}/${getProductRatePlanChargeBillingIdentifier(productRatePlanCharge) as string}`,
      productRatePlanCharge,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProductRatePlanChargeBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductRatePlanChargeBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductRatePlanChargeBillingToCollectionIfMissing(
    productRatePlanChargeCollection: IProductRatePlanChargeBilling[],
    ...productRatePlanChargesToCheck: (IProductRatePlanChargeBilling | null | undefined)[]
  ): IProductRatePlanChargeBilling[] {
    const productRatePlanCharges: IProductRatePlanChargeBilling[] = productRatePlanChargesToCheck.filter(isPresent);
    if (productRatePlanCharges.length > 0) {
      const productRatePlanChargeCollectionIdentifiers = productRatePlanChargeCollection.map(
        productRatePlanChargeItem => getProductRatePlanChargeBillingIdentifier(productRatePlanChargeItem)!
      );
      const productRatePlanChargesToAdd = productRatePlanCharges.filter(productRatePlanChargeItem => {
        const productRatePlanChargeIdentifier = getProductRatePlanChargeBillingIdentifier(productRatePlanChargeItem);
        if (
          productRatePlanChargeIdentifier == null ||
          productRatePlanChargeCollectionIdentifiers.includes(productRatePlanChargeIdentifier)
        ) {
          return false;
        }
        productRatePlanChargeCollectionIdentifiers.push(productRatePlanChargeIdentifier);
        return true;
      });
      return [...productRatePlanChargesToAdd, ...productRatePlanChargeCollection];
    }
    return productRatePlanChargeCollection;
  }
}
