import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IProductRatePlanBilling, getProductRatePlanBillingIdentifier } from '../product-rate-plan-billing.model';

export type EntityResponseType = HttpResponse<IProductRatePlanBilling>;
export type EntityArrayResponseType = HttpResponse<IProductRatePlanBilling[]>;

@Injectable({ providedIn: 'root' })
export class ProductRatePlanBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/product-rate-plans');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(productRatePlan: IProductRatePlanBilling): Observable<EntityResponseType> {
    return this.http.post<IProductRatePlanBilling>(this.resourceUrl, productRatePlan, { observe: 'response' });
  }

  update(productRatePlan: IProductRatePlanBilling): Observable<EntityResponseType> {
    return this.http.put<IProductRatePlanBilling>(
      `${this.resourceUrl}/${getProductRatePlanBillingIdentifier(productRatePlan) as string}`,
      productRatePlan,
      { observe: 'response' }
    );
  }

  partialUpdate(productRatePlan: IProductRatePlanBilling): Observable<EntityResponseType> {
    return this.http.patch<IProductRatePlanBilling>(
      `${this.resourceUrl}/${getProductRatePlanBillingIdentifier(productRatePlan) as string}`,
      productRatePlan,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IProductRatePlanBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductRatePlanBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addProductRatePlanBillingToCollectionIfMissing(
    productRatePlanCollection: IProductRatePlanBilling[],
    ...productRatePlansToCheck: (IProductRatePlanBilling | null | undefined)[]
  ): IProductRatePlanBilling[] {
    const productRatePlans: IProductRatePlanBilling[] = productRatePlansToCheck.filter(isPresent);
    if (productRatePlans.length > 0) {
      const productRatePlanCollectionIdentifiers = productRatePlanCollection.map(
        productRatePlanItem => getProductRatePlanBillingIdentifier(productRatePlanItem)!
      );
      const productRatePlansToAdd = productRatePlans.filter(productRatePlanItem => {
        const productRatePlanIdentifier = getProductRatePlanBillingIdentifier(productRatePlanItem);
        if (productRatePlanIdentifier == null || productRatePlanCollectionIdentifiers.includes(productRatePlanIdentifier)) {
          return false;
        }
        productRatePlanCollectionIdentifiers.push(productRatePlanIdentifier);
        return true;
      });
      return [...productRatePlansToAdd, ...productRatePlanCollection];
    }
    return productRatePlanCollection;
  }
}
