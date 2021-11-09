import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInvoiceItemBilling, getInvoiceItemBillingIdentifier } from '../invoice-item-billing.model';

export type EntityResponseType = HttpResponse<IInvoiceItemBilling>;
export type EntityArrayResponseType = HttpResponse<IInvoiceItemBilling[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceItemBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/invoice-items');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(invoiceItem: IInvoiceItemBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceItem);
    return this.http
      .post<IInvoiceItemBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoiceItem: IInvoiceItemBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceItem);
    return this.http
      .put<IInvoiceItemBilling>(`${this.resourceUrl}/${getInvoiceItemBillingIdentifier(invoiceItem) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(invoiceItem: IInvoiceItemBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoiceItem);
    return this.http
      .patch<IInvoiceItemBilling>(`${this.resourceUrl}/${getInvoiceItemBillingIdentifier(invoiceItem) as string}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IInvoiceItemBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceItemBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInvoiceItemBillingToCollectionIfMissing(
    invoiceItemCollection: IInvoiceItemBilling[],
    ...invoiceItemsToCheck: (IInvoiceItemBilling | null | undefined)[]
  ): IInvoiceItemBilling[] {
    const invoiceItems: IInvoiceItemBilling[] = invoiceItemsToCheck.filter(isPresent);
    if (invoiceItems.length > 0) {
      const invoiceItemCollectionIdentifiers = invoiceItemCollection.map(
        invoiceItemItem => getInvoiceItemBillingIdentifier(invoiceItemItem)!
      );
      const invoiceItemsToAdd = invoiceItems.filter(invoiceItemItem => {
        const invoiceItemIdentifier = getInvoiceItemBillingIdentifier(invoiceItemItem);
        if (invoiceItemIdentifier == null || invoiceItemCollectionIdentifiers.includes(invoiceItemIdentifier)) {
          return false;
        }
        invoiceItemCollectionIdentifiers.push(invoiceItemIdentifier);
        return true;
      });
      return [...invoiceItemsToAdd, ...invoiceItemCollection];
    }
    return invoiceItemCollection;
  }

  protected convertDateFromClient(invoiceItem: IInvoiceItemBilling): IInvoiceItemBilling {
    return Object.assign({}, invoiceItem, {
      untilDate: invoiceItem.untilDate?.isValid() ? invoiceItem.untilDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.untilDate = res.body.untilDate ? dayjs(res.body.untilDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoiceItem: IInvoiceItemBilling) => {
        invoiceItem.untilDate = invoiceItem.untilDate ? dayjs(invoiceItem.untilDate) : undefined;
      });
    }
    return res;
  }
}
