import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISettingsInvoiceBilling, getSettingsInvoiceBillingIdentifier } from '../settings-invoice-billing.model';

export type EntityResponseType = HttpResponse<ISettingsInvoiceBilling>;
export type EntityArrayResponseType = HttpResponse<ISettingsInvoiceBilling[]>;

@Injectable({ providedIn: 'root' })
export class SettingsInvoiceBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/settings-invoices');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(settingsInvoice: ISettingsInvoiceBilling): Observable<EntityResponseType> {
    return this.http.post<ISettingsInvoiceBilling>(this.resourceUrl, settingsInvoice, { observe: 'response' });
  }

  update(settingsInvoice: ISettingsInvoiceBilling): Observable<EntityResponseType> {
    return this.http.put<ISettingsInvoiceBilling>(
      `${this.resourceUrl}/${getSettingsInvoiceBillingIdentifier(settingsInvoice) as string}`,
      settingsInvoice,
      { observe: 'response' }
    );
  }

  partialUpdate(settingsInvoice: ISettingsInvoiceBilling): Observable<EntityResponseType> {
    return this.http.patch<ISettingsInvoiceBilling>(
      `${this.resourceUrl}/${getSettingsInvoiceBillingIdentifier(settingsInvoice) as string}`,
      settingsInvoice,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISettingsInvoiceBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISettingsInvoiceBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSettingsInvoiceBillingToCollectionIfMissing(
    settingsInvoiceCollection: ISettingsInvoiceBilling[],
    ...settingsInvoicesToCheck: (ISettingsInvoiceBilling | null | undefined)[]
  ): ISettingsInvoiceBilling[] {
    const settingsInvoices: ISettingsInvoiceBilling[] = settingsInvoicesToCheck.filter(isPresent);
    if (settingsInvoices.length > 0) {
      const settingsInvoiceCollectionIdentifiers = settingsInvoiceCollection.map(
        settingsInvoiceItem => getSettingsInvoiceBillingIdentifier(settingsInvoiceItem)!
      );
      const settingsInvoicesToAdd = settingsInvoices.filter(settingsInvoiceItem => {
        const settingsInvoiceIdentifier = getSettingsInvoiceBillingIdentifier(settingsInvoiceItem);
        if (settingsInvoiceIdentifier == null || settingsInvoiceCollectionIdentifiers.includes(settingsInvoiceIdentifier)) {
          return false;
        }
        settingsInvoiceCollectionIdentifiers.push(settingsInvoiceIdentifier);
        return true;
      });
      return [...settingsInvoicesToAdd, ...settingsInvoiceCollection];
    }
    return settingsInvoiceCollection;
  }
}
