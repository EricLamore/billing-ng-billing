import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRefundBilling, getRefundBillingIdentifier } from '../refund-billing.model';

export type EntityResponseType = HttpResponse<IRefundBilling>;
export type EntityArrayResponseType = HttpResponse<IRefundBilling[]>;

@Injectable({ providedIn: 'root' })
export class RefundBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/refunds');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(refund: IRefundBilling): Observable<EntityResponseType> {
    return this.http.post<IRefundBilling>(this.resourceUrl, refund, { observe: 'response' });
  }

  update(refund: IRefundBilling): Observable<EntityResponseType> {
    return this.http.put<IRefundBilling>(`${this.resourceUrl}/${getRefundBillingIdentifier(refund) as string}`, refund, {
      observe: 'response',
    });
  }

  partialUpdate(refund: IRefundBilling): Observable<EntityResponseType> {
    return this.http.patch<IRefundBilling>(`${this.resourceUrl}/${getRefundBillingIdentifier(refund) as string}`, refund, {
      observe: 'response',
    });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IRefundBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRefundBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addRefundBillingToCollectionIfMissing(
    refundCollection: IRefundBilling[],
    ...refundsToCheck: (IRefundBilling | null | undefined)[]
  ): IRefundBilling[] {
    const refunds: IRefundBilling[] = refundsToCheck.filter(isPresent);
    if (refunds.length > 0) {
      const refundCollectionIdentifiers = refundCollection.map(refundItem => getRefundBillingIdentifier(refundItem)!);
      const refundsToAdd = refunds.filter(refundItem => {
        const refundIdentifier = getRefundBillingIdentifier(refundItem);
        if (refundIdentifier == null || refundCollectionIdentifiers.includes(refundIdentifier)) {
          return false;
        }
        refundCollectionIdentifiers.push(refundIdentifier);
        return true;
      });
      return [...refundsToAdd, ...refundCollection];
    }
    return refundCollection;
  }
}
