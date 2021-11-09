import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IInvoiceBilling, getInvoiceBillingIdentifier } from '../invoice-billing.model';

export type EntityResponseType = HttpResponse<IInvoiceBilling>;
export type EntityArrayResponseType = HttpResponse<IInvoiceBilling[]>;

@Injectable({ providedIn: 'root' })
export class InvoiceBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/invoices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(invoice: IInvoiceBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .post<IInvoiceBilling>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(invoice: IInvoiceBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .put<IInvoiceBilling>(`${this.resourceUrl}/${getInvoiceBillingIdentifier(invoice) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(invoice: IInvoiceBilling): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(invoice);
    return this.http
      .patch<IInvoiceBilling>(`${this.resourceUrl}/${getInvoiceBillingIdentifier(invoice) as string}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IInvoiceBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IInvoiceBilling[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addInvoiceBillingToCollectionIfMissing(
    invoiceCollection: IInvoiceBilling[],
    ...invoicesToCheck: (IInvoiceBilling | null | undefined)[]
  ): IInvoiceBilling[] {
    const invoices: IInvoiceBilling[] = invoicesToCheck.filter(isPresent);
    if (invoices.length > 0) {
      const invoiceCollectionIdentifiers = invoiceCollection.map(invoiceItem => getInvoiceBillingIdentifier(invoiceItem)!);
      const invoicesToAdd = invoices.filter(invoiceItem => {
        const invoiceIdentifier = getInvoiceBillingIdentifier(invoiceItem);
        if (invoiceIdentifier == null || invoiceCollectionIdentifiers.includes(invoiceIdentifier)) {
          return false;
        }
        invoiceCollectionIdentifiers.push(invoiceIdentifier);
        return true;
      });
      return [...invoicesToAdd, ...invoiceCollection];
    }
    return invoiceCollection;
  }

  protected convertDateFromClient(invoice: IInvoiceBilling): IInvoiceBilling {
    return Object.assign({}, invoice, {
      emissionDate: invoice.emissionDate?.isValid() ? invoice.emissionDate.format(DATE_FORMAT) : undefined,
      dueDate: invoice.dueDate?.isValid() ? invoice.dueDate.format(DATE_FORMAT) : undefined,
      dateToSend: invoice.dateToSend?.isValid() ? invoice.dateToSend.format(DATE_FORMAT) : undefined,
      sendDate: invoice.sendDate?.isValid() ? invoice.sendDate.format(DATE_FORMAT) : undefined,
      dunningSendDate: invoice.dunningSendDate?.isValid() ? invoice.dunningSendDate.format(DATE_FORMAT) : undefined,
      creditNoteSendDate: invoice.creditNoteSendDate?.isValid() ? invoice.creditNoteSendDate.format(DATE_FORMAT) : undefined,
      pvalidationDate: invoice.pvalidationDate?.isValid() ? invoice.pvalidationDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.emissionDate = res.body.emissionDate ? dayjs(res.body.emissionDate) : undefined;
      res.body.dueDate = res.body.dueDate ? dayjs(res.body.dueDate) : undefined;
      res.body.dateToSend = res.body.dateToSend ? dayjs(res.body.dateToSend) : undefined;
      res.body.sendDate = res.body.sendDate ? dayjs(res.body.sendDate) : undefined;
      res.body.dunningSendDate = res.body.dunningSendDate ? dayjs(res.body.dunningSendDate) : undefined;
      res.body.creditNoteSendDate = res.body.creditNoteSendDate ? dayjs(res.body.creditNoteSendDate) : undefined;
      res.body.pvalidationDate = res.body.pvalidationDate ? dayjs(res.body.pvalidationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((invoice: IInvoiceBilling) => {
        invoice.emissionDate = invoice.emissionDate ? dayjs(invoice.emissionDate) : undefined;
        invoice.dueDate = invoice.dueDate ? dayjs(invoice.dueDate) : undefined;
        invoice.dateToSend = invoice.dateToSend ? dayjs(invoice.dateToSend) : undefined;
        invoice.sendDate = invoice.sendDate ? dayjs(invoice.sendDate) : undefined;
        invoice.dunningSendDate = invoice.dunningSendDate ? dayjs(invoice.dunningSendDate) : undefined;
        invoice.creditNoteSendDate = invoice.creditNoteSendDate ? dayjs(invoice.creditNoteSendDate) : undefined;
        invoice.pvalidationDate = invoice.pvalidationDate ? dayjs(invoice.pvalidationDate) : undefined;
      });
    }
    return res;
  }
}
