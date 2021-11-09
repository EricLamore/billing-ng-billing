import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISettingsInvoiceUnitBilling, getSettingsInvoiceUnitBillingIdentifier } from '../settings-invoice-unit-billing.model';

export type EntityResponseType = HttpResponse<ISettingsInvoiceUnitBilling>;
export type EntityArrayResponseType = HttpResponse<ISettingsInvoiceUnitBilling[]>;

@Injectable({ providedIn: 'root' })
export class SettingsInvoiceUnitBillingService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/settings-invoice-units');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(settingsInvoiceUnit: ISettingsInvoiceUnitBilling): Observable<EntityResponseType> {
    return this.http.post<ISettingsInvoiceUnitBilling>(this.resourceUrl, settingsInvoiceUnit, { observe: 'response' });
  }

  update(settingsInvoiceUnit: ISettingsInvoiceUnitBilling): Observable<EntityResponseType> {
    return this.http.put<ISettingsInvoiceUnitBilling>(
      `${this.resourceUrl}/${getSettingsInvoiceUnitBillingIdentifier(settingsInvoiceUnit) as string}`,
      settingsInvoiceUnit,
      { observe: 'response' }
    );
  }

  partialUpdate(settingsInvoiceUnit: ISettingsInvoiceUnitBilling): Observable<EntityResponseType> {
    return this.http.patch<ISettingsInvoiceUnitBilling>(
      `${this.resourceUrl}/${getSettingsInvoiceUnitBillingIdentifier(settingsInvoiceUnit) as string}`,
      settingsInvoiceUnit,
      { observe: 'response' }
    );
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ISettingsInvoiceUnitBilling>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISettingsInvoiceUnitBilling[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSettingsInvoiceUnitBillingToCollectionIfMissing(
    settingsInvoiceUnitCollection: ISettingsInvoiceUnitBilling[],
    ...settingsInvoiceUnitsToCheck: (ISettingsInvoiceUnitBilling | null | undefined)[]
  ): ISettingsInvoiceUnitBilling[] {
    const settingsInvoiceUnits: ISettingsInvoiceUnitBilling[] = settingsInvoiceUnitsToCheck.filter(isPresent);
    if (settingsInvoiceUnits.length > 0) {
      const settingsInvoiceUnitCollectionIdentifiers = settingsInvoiceUnitCollection.map(
        settingsInvoiceUnitItem => getSettingsInvoiceUnitBillingIdentifier(settingsInvoiceUnitItem)!
      );
      const settingsInvoiceUnitsToAdd = settingsInvoiceUnits.filter(settingsInvoiceUnitItem => {
        const settingsInvoiceUnitIdentifier = getSettingsInvoiceUnitBillingIdentifier(settingsInvoiceUnitItem);
        if (settingsInvoiceUnitIdentifier == null || settingsInvoiceUnitCollectionIdentifiers.includes(settingsInvoiceUnitIdentifier)) {
          return false;
        }
        settingsInvoiceUnitCollectionIdentifiers.push(settingsInvoiceUnitIdentifier);
        return true;
      });
      return [...settingsInvoiceUnitsToAdd, ...settingsInvoiceUnitCollection];
    }
    return settingsInvoiceUnitCollection;
  }
}
