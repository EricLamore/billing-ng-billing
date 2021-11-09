import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPaymentMethodBilling, getPaymentMethodBillingIdentifier } from '../payment-method-billing.model';

export type EntityResponseType = HttpResponse<IPaymentMethodBilling>;
export type EntityArrayResponseType = HttpResponse<IPaymentMethodBilling[]>;

@Injectable({ providedIn: 'root' })
export class PaymentMethodBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/payment-methods');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(paymentMethod: IPaymentMethodBilling): Observable<EntityResponseType> {
    return this.http.post<IPaymentMethodBilling>(this.resourceUrl, paymentMethod, { observe: 'response' });
  }

  update(paymentMethod: IPaymentMethodBilling): Observable<EntityResponseType> {
    return this.http.put<IPaymentMethodBilling>(
      `${this.resourceUrl}/${getPaymentMethodBillingIdentifier(paymentMethod) as string}`,
      paymentMethod,
      { observe: 'response' }
    );
  }

  partialUpdate(paymentMethod: IPaymentMethodBilling): Observable<EntityResponseType> {
    return this.http.patch<IPaymentMethodBilling>(
      `${this.resourceUrl}/${getPaymentMethodBillingIdentifier(paymentMethod) as string}`,
      paymentMethod,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IPaymentMethodBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPaymentMethodBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addPaymentMethodBillingToCollectionIfMissing(
    paymentMethodCollection: IPaymentMethodBilling[],
    ...paymentMethodsToCheck: (IPaymentMethodBilling | null | undefined)[]
  ): IPaymentMethodBilling[] {
    const paymentMethods: IPaymentMethodBilling[] = paymentMethodsToCheck.filter(isPresent);
    if (paymentMethods.length > 0) {
      const paymentMethodCollectionIdentifiers = paymentMethodCollection.map(
        paymentMethodItem => getPaymentMethodBillingIdentifier(paymentMethodItem)!
      );
      const paymentMethodsToAdd = paymentMethods.filter(paymentMethodItem => {
        const paymentMethodIdentifier = getPaymentMethodBillingIdentifier(paymentMethodItem);
        if (paymentMethodIdentifier == null || paymentMethodCollectionIdentifiers.includes(paymentMethodIdentifier)) {
          return false;
        }
        paymentMethodCollectionIdentifiers.push(paymentMethodIdentifier);
        return true;
      });
      return [...paymentMethodsToAdd, ...paymentMethodCollection];
    }
    return paymentMethodCollection;
  }
}
